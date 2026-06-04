# 包内容说明

## apk

- `PokerTrainer-easygto-fusion-cn.apk`：推荐安装包。
- `PokerTrainer-cn-complete.apk`：同内容兼容文件名。

## source

- `trainer_native/AndroidManifest.xml`：Android 清单文件。
- `trainer_native/res/values/strings.xml`：应用名资源。
- `trainer_native/res/values/styles.xml`：基础样式。
- `trainer_native/src/com/codex/pokertrainer/MainActivity.java`：完整训练器逻辑、UI、牌桌绘制、范围矩阵和训练数据。

## scripts

- `rebuild-from-workspace.ps1`：从当前工作区工具链重新构建 APK。

## docs

- `TEST_REPORT.md`：功能测试记录。
- `PACKAGE_CONTENTS.md`：本说明文件。

## 注意

本交付包没有包含 Android SDK、JDK、build-tools 这类大型工具链。它们仍位于当前工作区的 `android-sdk/` 和 `tools/` 目录。安装 APK 不需要这些工具；只有重新打包时才需要。

