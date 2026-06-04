# 中文 GTO 德扑训练器

原创 Android + Web 德扑训练器。当前版本是“GTO 学习 + 专项训练 + 范围查看 + 人机实战 + 实时复盘 + 数据统计”的融合版。

不包含 EasyGTO 或其他网站的代码、素材、接口或未授权资源。

## 网页版本

静态网页入口：

`web/index.html`

运行方式：

- 直接双击打开 `web/index.html`
- 或用任意静态服务器托管 `web/` 目录

网页功能：

- 训练大厅
- 学习策略
- 练习模式
- 范围查看矩阵
- 人机实战
- 推荐行动 / GTO 频率 / EV 对比
- 建议开关
- 实时复盘
- 数据统计
- 排行榜

## Android 安装包

可直接安装：

`release/apk/PokerTrainer-easygto-fusion-cn.apk`

完整交付压缩包：

`release/PokerTrainer-easygto-fusion-complete-package.zip`

## Android 源码

核心源码：

`trainer_native/src/com/codex/pokertrainer/MainActivity.java`

资源：

`trainer_native/res/values/`

Manifest：

`trainer_native/AndroidManifest.xml`

## 测试

测试报告：

`docs/TEST_REPORT.md`

当前测试结论：

- Android：主页、学习页、模式页、范围页、人机实战、训练桌、建议开关、反馈、复盘、下一手、统计页、排行榜全部通过。
- Web：JS 语法检查、静态结构检查、HTTP 静态资源加载检查通过。
- Android crash log 为空。

## 构建说明

`scripts/rebuild-from-workspace.ps1` 可在原 Codex 工作区中复用现有 Android SDK、JDK 和 debug keystore 重新打包 Android APK。

网页版本不需要构建步骤，`web/` 是纯静态文件。

