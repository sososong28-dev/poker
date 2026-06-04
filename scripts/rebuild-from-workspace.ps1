param(
    [string]$OutputName = "PokerTrainer-easygto-fusion-rebuilt.apk"
)

$ErrorActionPreference = "Stop"

$packageRoot = Split-Path -Parent $PSScriptRoot
$workspaceRoot = Split-Path -Parent $packageRoot
$native = Join-Path $packageRoot "source\trainer_native"
$out = Join-Path $packageRoot "build"
$apkOut = Join-Path $packageRoot "apk"

$buildTools = Join-Path $workspaceRoot "tools\build-tools\android-16"
$javaHome = Join-Path $workspaceRoot "tools\jdk17\jdk-17.0.19+10"
$androidJar = Join-Path $workspaceRoot "android-sdk\platforms\android-36\android.jar"
$keystore = Join-Path $workspaceRoot "build\pokertrainer-debug.keystore"

foreach ($required in @(
    (Join-Path $buildTools "aapt2.exe"),
    (Join-Path $buildTools "d8.bat"),
    (Join-Path $buildTools "zipalign.exe"),
    (Join-Path $buildTools "apksigner.bat"),
    (Join-Path $javaHome "bin\javac.exe"),
    (Join-Path $javaHome "bin\jar.exe"),
    $androidJar,
    $keystore,
    (Join-Path $native "AndroidManifest.xml"),
    (Join-Path $native "src\com\codex\pokertrainer\MainActivity.java")
)) {
    if (-not (Test-Path $required)) {
        throw "Missing required file: $required"
    }
}

$env:JAVA_HOME = $javaHome
$env:Path = (Join-Path $javaHome "bin") + ";" + $env:Path

$aapt2 = Join-Path $buildTools "aapt2.exe"
$d8 = Join-Path $buildTools "d8.bat"
$zipalign = Join-Path $buildTools "zipalign.exe"
$apksigner = Join-Path $buildTools "apksigner.bat"
$javac = Join-Path $javaHome "bin\javac.exe"
$jar = Join-Path $javaHome "bin\jar.exe"

$unsigned = Join-Path $out "unsigned.apk"
$withDex = Join-Path $out "withdex.apk"
$aligned = Join-Path $out "aligned.apk"
$signed = Join-Path $apkOut $OutputName

Remove-Item -LiteralPath $out -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path (Join-Path $out "compiled"), (Join-Path $out "classes"), (Join-Path $out "dex"), $apkOut | Out-Null

& $aapt2 compile --dir (Join-Path $native "res") -o (Join-Path $out "compiled\resources.zip")
& $aapt2 link -I $androidJar --manifest (Join-Path $native "AndroidManifest.xml") -o $unsigned (Join-Path $out "compiled\resources.zip")
& $javac -encoding UTF-8 -source 8 -target 8 -bootclasspath $androidJar -d (Join-Path $out "classes") (Join-Path $native "src\com\codex\pokertrainer\MainActivity.java")
& $jar cf (Join-Path $out "classes.jar") -C (Join-Path $out "classes") .
& $d8 --lib $androidJar --min-api 23 --output (Join-Path $out "dex") (Join-Path $out "classes.jar")

Copy-Item -LiteralPath $unsigned -Destination $withDex -Force
Push-Location (Join-Path $out "dex")
& $jar uf $withDex classes.dex
Pop-Location

& $zipalign -p -f 4 $withDex $aligned
& $apksigner sign --ks $keystore --ks-key-alias androiddebugkey --ks-pass pass:android --key-pass pass:android --out $signed $aligned
& $apksigner verify --verbose $signed

Write-Output "Built: $signed"
