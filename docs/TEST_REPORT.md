# 测试报告

测试日期：2026-06-04

## Android APK 测试

测试方式：Android 模拟器，使用 adb 安装、启动、UI 树检查、点击操作和 crash log 检查。

APK 文件：

`release/apk/PokerTrainer-easygto-fusion-cn.apk`

通过项目：

- 首页入口完整：开始训练、学习策略、练习模式、范围查看、实时复盘、人机实战、数据统计、排行榜。
- 学习策略页可打开，展示翻前范围、翻后决策、下注尺度、实时复盘、人机实战模块。
- 练习模式页可打开，展示翻前、翻牌、转牌、河牌、漏洞、综合模式。
- 范围查看页可打开，位置选择和范围矩阵正常显示。
- 人机实战入口可打开，并能进入训练桌。
- 训练桌显示推荐行动、GTO 频率、EV 对比、机器人倾向。
- 建议开关可关闭，关闭后推荐内容隐藏。
- 建议开关可再次开启。
- 点击“加注 2400”后进入动作反馈。
- 反馈显示“正确决策”和“打开复盘”。
- 复盘页显示历史手牌、你的行动、最佳线路、错误类型。
- 从复盘返回牌局正常。
- 点击“下一手”后切换到第二个训练场景。
- 数据统计页显示主要漏洞、街道表现、错误类型。
- 排行榜可打开。
- crash log 为空。

## Web 版本测试

测试方式：本地静态 HTTP 服务 + Node 校验。

网页入口：

`web/index.html`

通过项目：

- `web/app.js` 语法检查通过。
- HTML 关键结构存在：`viewRoot`、`trainingTemplate`、`pokerCanvas`。
- JS 关键模块存在：`renderTraining`、`renderRange`、`renderReview`、`renderAnalytics`、`drawTable`。
- CSS 关键布局存在：`.training-layout`。
- HTTP 静态加载通过：`/`、`/styles.css`、`/app.js` 均返回 200。

限制：

- 当前环境 Playwright 入口可见但缺少 `playwright-core` 依赖，未执行完整无头浏览器点击流。
- 已完成静态结构、JS 语法和 HTTP 加载校验。

结论：Android 版本完整流程通过；网页版本静态可运行性检查通过。

