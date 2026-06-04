# 包内容说明

## Android

- `release/apk/PokerTrainer-easygto-fusion-cn.apk`：推荐安装包。
- `trainer_native/AndroidManifest.xml`：Android 清单文件。
- `trainer_native/res/values/strings.xml`：应用名资源。
- `trainer_native/res/values/styles.xml`：基础样式。
- `trainer_native/src/com/codex/pokertrainer/MainActivity.java`：完整 Android 训练器逻辑、UI、牌桌绘制、范围矩阵和训练数据。

## Web

- `web/index.html`：静态网页入口。
- `web/styles.css`：网页样式。
- `web/app.js`：网页训练器逻辑、牌桌 Canvas 绘制、范围矩阵、复盘和统计。

## Release

- `release/PokerTrainer-easygto-fusion-complete-package.zip`：完整交付压缩包。
- `checksums.sha256`：仓库文件校验信息。

## Scripts

- `scripts/rebuild-from-workspace.ps1`：从当前工作区工具链重新构建 Android APK。

## 注意

本仓库没有包含 Android SDK、JDK、build-tools 这类大型工具链。安装 APK 或运行网页不需要这些工具；只有重新打包 Android APK 时才需要。

