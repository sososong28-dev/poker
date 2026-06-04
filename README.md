# 中文 GTO 德扑训练器

原创 Android 原生 Java 德扑训练器。当前版本是“GTO 学习 + 专项训练 + 范围查看 + 人机实战 + 实时复盘 + 数据统计”的融合版。

不包含 EasyGTO 或其他网站的代码、素材、接口或未授权资源。

## 安装包

可直接安装：

`release/apk/PokerTrainer-easygto-fusion-cn.apk`

完整交付压缩包：

`release/PokerTrainer-easygto-fusion-complete-package.zip`

## 功能

- 中文大厅：开始训练、学习策略、练习模式、范围查看、实时复盘、人机实战、数据统计、排行榜。
- 学习策略：翻前范围、翻后决策、下注尺度、实时复盘、人机实战模块。
- 练习模式：翻前、翻牌、转牌、河牌、漏洞专项、综合随机。
- 范围查看：位置选择和 13x13 手牌范围矩阵。
- 训练牌桌：公共牌、Hero 手牌、底池、有效筹码、对手行动、行动按钮。
- 建议系统：推荐行动、胜率、GTO 频率、EV 对比、教练解释、训练目标、专项练习。
- 人机实战：模拟机器人倾向，连续训练并记录复盘。
- 实时复盘：记录你的行动、最佳线路、EV、错误类型和解释。
- 数据统计：已练手牌、正确率、EV 汇总、主要漏洞、街道表现和错误类型。

## 源码

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

- 主页、学习页、模式页、范围页、人机实战、训练桌、建议开关、反馈、复盘、下一手、统计页、排行榜全部通过。
- crash log 为空。

## 构建说明

`scripts/rebuild-from-workspace.ps1` 可在原 Codex 工作区中复用现有 Android SDK、JDK 和 debug keystore 重新打包。

如果在新机器构建，需要准备：

- JDK 17
- Android SDK platform 36
- Android build-tools
- debug keystore

