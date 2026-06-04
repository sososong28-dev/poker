const hands = [
  {
    title: "BTN 对 BB | 转牌 Probe",
    mode: "现金局 / 6 人桌 / 100bb",
    street: "转牌",
    heroPosition: "BTN",
    heroCards: ["Ac", "Qc"],
    board: ["Ts", "8d", "3c", "2c"],
    pot: 1800,
    stack: 10000,
    villainAction: "BB 下注 1000",
    goal: "训练面对过宽转牌 Probe 时，利用阻断牌和权益做反击。",
    actions: [
      { label: "弃牌", amount: "0" },
      { label: "跟注", amount: "1000" },
      { label: "加注", amount: "2400" },
      { label: "全下", amount: "9100" }
    ],
    recommendedIndex: 2,
    recommendation: "加注 2400",
    winRate: "31%",
    mix: "弃牌 8% / 跟注 37% / 加注 55% / 全下 0%",
    ev: "0.00 / -0.35 / +0.90 / -1.10",
    evLoss: "-1.4 EV",
    errorType: "过度跟注",
    advice: "对手 Probe 频率偏高。顶对加坚果同花阻断牌，适合用加注获取薄价值并保护权益。",
    drill: "当价值和保护同时提升手牌时，优先选择加注。",
    botLine: "机器人转牌 Probe 过宽，但面对加注弃牌偏多。",
    rangeNote: "BTN 具备位置优势，Ac 阻断坚果同花继续牌。",
    factors: ["阻断牌优势", "保护价值", "转牌反击", "常见漏洞：过度跟注"]
  },
  {
    title: "CO 对 BTN | 翻牌持续下注",
    mode: "单加注底池 / 有位置",
    street: "翻牌",
    heroPosition: "BTN",
    heroCards: ["Kh", "Qh"],
    board: ["Qd", "7s", "2h"],
    pot: 1200,
    stack: 10000,
    villainAction: "CO 持续下注 500",
    goal: "练习有位置翻牌防守：保留对手被压制牌和诈唬牌。",
    actions: [
      { label: "弃牌", amount: "0" },
      { label: "跟注", amount: "500" },
      { label: "加注", amount: "1600" }
    ],
    recommendedIndex: 1,
    recommendation: "跟注 500",
    winRate: "42%",
    mix: "弃牌 0% / 跟注 82% / 加注 18%",
    ev: "-1.20 / +0.75 / +0.10",
    evLoss: "-1.1 EV",
    errorType: "低 EV 加注",
    advice: "顶对加后门听牌更适合作为跟注；加注会让很多更差牌过早弃牌。",
    drill: "有位置时，用中等强度成牌跟注，让权益自然实现。",
    botLine: "机器人小尺度 c-bet 过频，跟注保留它的空气牌。",
    rangeNote: "BTN 有位置，KQ 在干燥 Q 高牌面具备稳定摊牌价值。",
    factors: ["范围权益实现", "保留诈唬", "避免低 EV 加注"]
  },
  {
    title: "SB 对 BB | 河牌摊牌价值",
    mode: "盲注对抗 / 河牌节点",
    street: "河牌",
    heroPosition: "SB",
    heroCards: ["As", "Jh"],
    board: ["Ah", "9c", "5d", "2s", "7c"],
    pot: 3100,
    stack: 8200,
    villainAction: "BB 河牌过牌",
    goal: "训练河牌纪律：区分薄价值下注和摊牌价值牌。",
    actions: [
      { label: "过牌", amount: "0" },
      { label: "下注", amount: "1400" },
      { label: "全下", amount: "6700" }
    ],
    recommendedIndex: 0,
    recommendation: "随后过牌",
    winRate: "63%",
    mix: "过牌 76% / 下注 24% / 全下 0%",
    ev: "+0.65 / -0.20 / -2.10",
    evLoss: "-0.9 EV",
    errorType: "过度薄价值",
    advice: "你的牌有足够摊牌价值。下注会把中等强度成牌变成很薄的诈唬。",
    drill: "能经常摊牌赢、又很难被更差牌跟注的牌，优先过牌。",
    botLine: "机器人河牌跟注偏紧，薄价值下注收益下降。",
    rangeNote: "A 高成牌有摊牌价值，但缺少被更差牌跟注的目标。",
    factors: ["摊牌价值", "不适合诈唬", "控制底池"]
  },
  {
    title: "BTN RFI | 翻前开池",
    mode: "翻前范围 / 40bb",
    street: "翻前",
    heroPosition: "BTN",
    heroCards: ["Kc", "9c"],
    board: [],
    pot: 150,
    stack: 10000,
    villainAction: "前面玩家全部弃牌",
    goal: "训练按钮位开池范围：用位置优势扩展可盈利组合。",
    actions: [
      { label: "弃牌", amount: "0" },
      { label: "加注", amount: "200" },
      { label: "全下", amount: "4000" }
    ],
    recommendedIndex: 1,
    recommendation: "加注 200",
    winRate: "48%",
    mix: "弃牌 5% / 加注 95% / 全下 0%",
    ev: "-0.55 / +0.62 / -1.70",
    evLoss: "-1.0 EV",
    errorType: "过紧弃牌",
    advice: "按钮位对盲注有位置优势，K9s 具备可玩性和同花潜力，是稳定开池牌。",
    drill: "按钮位不要用过紧范围丢掉位置红利。",
    botLine: "机器人盲注防守偏被动，按钮位可以提高开池频率。",
    rangeNote: "BTN 范围最宽，K9s 位于盈利开池区间。",
    factors: ["位置优势", "同花连通性", "盲注压力"]
  },
  {
    title: "BB 对 CO | 翻牌防守",
    mode: "单加注底池 / OOP",
    street: "翻牌",
    heroPosition: "BB",
    heroCards: ["9s", "8s"],
    board: ["Js", "Ts", "3d"],
    pot: 1400,
    stack: 9600,
    villainAction: "CO 下注 700",
    goal: "训练听牌强度评估：强听牌不要过度弃牌。",
    actions: [
      { label: "弃牌", amount: "0" },
      { label: "跟注", amount: "700" },
      { label: "加注", amount: "2200" }
    ],
    recommendedIndex: 2,
    recommendation: "加注 2200",
    winRate: "46%",
    mix: "弃牌 0% / 跟注 44% / 加注 56%",
    ev: "-1.80 / +0.25 / +0.95",
    evLoss: "-1.6 EV",
    errorType: "错过半诈唬",
    advice: "双头顺听加同花听牌有大量权益。对手持续下注范围宽时，加注能产生弃牌率并构建大底池。",
    drill: "强听牌在有弃牌率时优先主动进攻。",
    botLine: "机器人面对翻牌加注继续范围偏窄，可以提高半诈唬加注。",
    rangeNote: "9s8s 在 JTs3d 上是高权益听牌，适合混合加注。",
    factors: ["强听牌", "半诈唬", "弃牌率", "权益兑现"]
  }
];

const navItems = [
  ["home", "大厅", "概览"],
  ["training", "开始训练", "推荐决策"],
  ["study", "学习策略", "策略模块"],
  ["modes", "练习模式", "专项训练"],
  ["range", "范围查看", "13x13"],
  ["bot", "人机实战", "机器人"],
  ["review", "实时复盘", "历史手牌"],
  ["analytics", "数据统计", "漏洞"],
  ["leaderboard", "排行榜", "本地"]
];

const handFlows = [
  {
    title: "BTN 对 BB | AQs 完整牌局",
    mode: "现金局 / 6 人桌 / 100bb",
    heroPosition: "BTN",
    heroCards: ["Ac", "Qc"],
    stack: 10000,
    spots: [
      {
        street: "翻前",
        board: [],
        pot: 150,
        villainAction: "前面玩家全部弃牌，轮到 BTN 行动",
        goal: "从按钮位用位置优势构建盈利开池范围。",
        actions: [
          { label: "弃牌", amount: "0" },
          { label: "加注", amount: "250" },
          { label: "全下", amount: "10000" }
        ],
        recommendedIndex: 1,
        recommendation: "加注 250",
        winRate: "49%",
        mix: "弃牌 0% / 加注 100% / 全下 0%",
        ev: "-0.45 / +0.72 / -2.30",
        evLoss: "-1.2 EV",
        errorType: "翻前过紧",
        advice: "AQs 在按钮位是高频开池。弃牌会损失位置红利，全下则让价值牌变成低 EV 极端线路。",
        drill: "后位强 Broadway 同花牌优先标准开池，不要跳过可控底池的盈利机会。",
        botLine: "机器人盲注防守偏被动，标准开池能稳定拿到位置优势。",
        rangeNote: "按钮位范围最宽，AQs 属于核心价值开池。",
        factors: ["位置优势", "高牌权益", "同花潜力", "可控底池"]
      },
      {
        street: "翻牌",
        board: ["Ts", "8d", "3c"],
        pot: 600,
        villainAction: "BB 过牌",
        goal: "在干湿混合牌面上识别后门权益和范围压力。",
        actions: [
          { label: "过牌", amount: "0" },
          { label: "下注", amount: "220" },
          { label: "下注", amount: "480" }
        ],
        recommendedIndex: 1,
        recommendation: "下注 220",
        winRate: "34%",
        mix: "过牌 38% / 小注 54% / 大注 8%",
        ev: "+0.12 / +0.48 / +0.05",
        evLoss: "-0.8 EV",
        errorType: "尺度过大",
        advice: "AQ 高牌加后门坚果同花阻断牌适合小尺度范围下注。大注会让空气牌投入过多。",
        drill: "有范围优势但未成牌时，优先用小尺度压迫宽防守范围。",
        botLine: "机器人面对小注继续过宽，后续街道仍有剥削空间。",
        rangeNote: "Ac 阻断后门坚果同花，Q 高牌保留转牌权益。",
        factors: ["范围下注", "后门权益", "阻断牌", "小尺度"]
      },
      {
        street: "转牌",
        board: ["Ts", "8d", "3c", "2c"],
        pot: 1040,
        villainAction: "BB Probe 下注 520",
        goal: "面对过宽 Probe 时，用阻断牌和权益做反击。",
        actions: [
          { label: "弃牌", amount: "0" },
          { label: "跟注", amount: "520" },
          { label: "加注", amount: "1500" },
          { label: "全下", amount: "9300" }
        ],
        recommendedIndex: 2,
        recommendation: "加注 1500",
        winRate: "31%",
        mix: "弃牌 8% / 跟注 37% / 加注 55% / 全下 0%",
        ev: "0.00 / -0.25 / +0.95 / -1.20",
        evLoss: "-1.4 EV",
        errorType: "过度跟注",
        advice: "转牌拿到坚果同花听牌和高张阻断牌，面对过宽 Probe 可以加注获取弃牌率并保护权益。",
        drill: "当权益和弃牌率同时提升时，优先主动加注。",
        botLine: "机器人转牌 Probe 过宽，但面对加注弃牌偏多。",
        rangeNote: "AcQc 在转牌同时拥有同花听牌、阻断牌和位置优势。",
        factors: ["坚果听牌", "转牌反击", "弃牌率", "阻断牌优势"]
      },
      {
        street: "河牌",
        board: ["Ts", "8d", "3c", "2c", "7h"],
        pot: 4040,
        villainAction: "BB 跟注后河牌过牌",
        goal: "错过听牌后判断是否继续诈唬，避免无目标开火。",
        actions: [
          { label: "过牌", amount: "0" },
          { label: "下注", amount: "1100" },
          { label: "下注", amount: "2800" }
        ],
        recommendedIndex: 0,
        recommendation: "随后过牌",
        winRate: "18%",
        mix: "过牌 68% / 小注 7% / 大注 25%",
        ev: "+0.05 / -0.42 / -0.10",
        evLoss: "-0.9 EV",
        errorType: "河牌过度诈唬",
        advice: "听牌错过后仍有 A 高摊牌价值。对手转牌跟注后范围更强，低质量河牌继续开火收益不足。",
        drill: "错过听牌不等于必须诈唬；先确认对手有足够弃牌目标。",
        botLine: "机器人河牌跟注偏紧但转牌跟注范围偏强，低频放弃更稳。",
        rangeNote: "A 高有少量摊牌价值，但缺少强阻断目标。",
        factors: ["摊牌价值", "弃牌目标", "河牌纪律", "完整牌局收束"]
      }
    ]
  },
  {
    title: "BB 对 CO | 98s 防守完整牌局",
    mode: "现金局 / 6 人桌 / 100bb",
    heroPosition: "BB",
    heroCards: ["9s", "8s"],
    stack: 9600,
    spots: [
      {
        street: "翻前",
        board: [],
        pot: 400,
        villainAction: "CO 开池 250，其他玩家弃牌",
        goal: "用大盲防守范围保留可玩性组合。",
        actions: [
          { label: "弃牌", amount: "0" },
          { label: "跟注", amount: "150" },
          { label: "加注", amount: "950" }
        ],
        recommendedIndex: 1,
        recommendation: "跟注 150",
        winRate: "38%",
        mix: "弃牌 10% / 跟注 78% / 加注 12%",
        ev: "-0.35 / +0.28 / +0.05",
        evLoss: "-0.8 EV",
        errorType: "大盲弃牌过多",
        advice: "98s 有连通性和同花潜力，面对 CO 标准开池可以大盲跟注防守。",
        drill: "大盲已投入盲注，防守时要计算补价和翻后可玩性。",
        botLine: "机器人 CO 开池范围偏宽，BB 可以保留更多同花连张。",
        rangeNote: "98s 属于大盲可防守的高可玩性组合。",
        factors: ["补价", "连通性", "同花潜力", "范围防守"]
      },
      {
        street: "翻牌",
        board: ["Js", "Ts", "3d"],
        pot: 650,
        villainAction: "CO 持续下注 330",
        goal: "强听牌不要被动放弃，利用权益和弃牌率反击。",
        actions: [
          { label: "弃牌", amount: "0" },
          { label: "跟注", amount: "330" },
          { label: "加注", amount: "1150" }
        ],
        recommendedIndex: 2,
        recommendation: "加注 1150",
        winRate: "46%",
        mix: "弃牌 0% / 跟注 44% / 加注 56%",
        ev: "-1.80 / +0.25 / +0.95",
        evLoss: "-1.6 EV",
        errorType: "错过半诈唬",
        advice: "双头顺听加同花听牌有大量权益。对手持续下注范围宽时，加注能产生弃牌率并构建大底池。",
        drill: "强听牌在有弃牌率时优先主动进攻。",
        botLine: "机器人面对翻牌加注继续范围偏窄。",
        rangeNote: "9s8s 在 JTs3d 上是高权益听牌。",
        factors: ["强听牌", "半诈唬", "弃牌率", "权益兑现"]
      },
      {
        street: "转牌",
        board: ["Js", "Ts", "3d", "2c"],
        pot: 2950,
        villainAction: "CO 跟注后转牌过牌",
        goal: "半诈唬被跟注后，继续评估权益和压力。",
        actions: [
          { label: "过牌", amount: "0" },
          { label: "下注", amount: "1450" },
          { label: "全下", amount: "8120" }
        ],
        recommendedIndex: 1,
        recommendation: "下注 1450",
        winRate: "39%",
        mix: "过牌 31% / 下注 62% / 全下 7%",
        ev: "+0.10 / +0.70 / -0.55",
        evLoss: "-1.0 EV",
        errorType: "转牌放弃过早",
        advice: "转牌低张没有明显改善对手范围，你仍有大量河牌补牌和弃牌率，适合中等尺度继续施压。",
        drill: "翻牌主动加注后，空白转牌通常需要保留连续压力。",
        botLine: "机器人在转牌面对持续压力会弃掉一部分顶对弱踢脚和中等对子。",
        rangeNote: "98s 保留顺子和同花大量 outs。",
        factors: ["连续压力", "空白转牌", "听牌权益", "尺度控制"]
      },
      {
        street: "河牌",
        board: ["Js", "Ts", "3d", "2c", "Qh"],
        pot: 5850,
        villainAction: "CO 跟注后河牌过牌",
        goal: "完成顺子后选择可被更差牌支付的价值尺度。",
        actions: [
          { label: "过牌", amount: "0" },
          { label: "下注", amount: "2200" },
          { label: "全下", amount: "6670" }
        ],
        recommendedIndex: 1,
        recommendation: "下注 2200",
        winRate: "82%",
        mix: "过牌 0% / 下注 74% / 全下 26%",
        ev: "+0.90 / +2.40 / +1.65",
        evLoss: "-1.3 EV",
        errorType: "错过价值下注",
        advice: "河牌 Q 完成顺子。中等尺度能让两对、顶对和部分强 Jx 支付；全下会压掉较多更差牌。",
        drill: "完成强牌后优先寻找能被更差牌跟注的价值尺度。",
        botLine: "机器人河牌面对中等下注跟注偏宽，价值下注收益更高。",
        rangeNote: "98s 完成 Q 高顺子，是清晰价值下注。",
        factors: ["价值下注", "尺度选择", "顺子完成", "支付目标"]
      }
    ]
  }
];

const ranks = ["A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"];
const positions = ["UTG", "LJ", "HJ", "CO", "BTN", "SB", "BB"];
let memoryHistory = [];

function readHistory() {
  try {
    const raw = window.localStorage?.getItem("pokerTrainerHistory");
    return raw ? JSON.parse(raw) : [];
  } catch {
    return memoryHistory;
  }
}

function writeHistory(records) {
  memoryHistory = [...records];
  try {
    window.localStorage?.setItem("pokerTrainerHistory", JSON.stringify(records));
  } catch {
    // Some file:// WebView contexts block localStorage. Keep the session in memory.
  }
}

function completedHandsFrom(records) {
  return records.filter((record) => record.handComplete).length;
}

const initialHistory = readHistory();

const state = {
  view: "home",
  flowIndex: 0,
  streetIndex: 0,
  handNumber: completedHandsFrom(initialHistory) + 1,
  awaiting: true,
  selectedAction: -1,
  lastFeedback: null,
  adviceOpen: true,
  botBattle: false,
  rangePosition: 4,
  history: initialHistory
};

const nav = document.getElementById("nav");
const viewRoot = document.getElementById("viewRoot");
const viewTitle = document.getElementById("viewTitle");
const viewSubtitle = document.getElementById("viewSubtitle");

document.getElementById("quickStartBtn").addEventListener("click", () => startTraining(false, 0));
document.getElementById("resetSessionBtn").addEventListener("click", () => {
  state.history = [];
  state.handNumber = 1;
  state.lastFeedback = null;
  persist();
  render();
});

function init() {
  navItems.forEach(([id, label, meta]) => {
    const button = document.createElement("button");
    button.className = "nav-button";
    button.type = "button";
    button.dataset.view = id;
    button.innerHTML = `<span>${label}</span><small>${meta}</small>`;
    button.addEventListener("click", () => {
      if (id === "training") startTraining(false, 0);
      else if (id === "bot") showView("bot");
      else showView(id);
    });
    nav.appendChild(button);
  });
  render();
}

function showView(view) {
  state.view = view;
  render();
}

function persist() {
  writeHistory(state.history);
}

function render() {
  updateShell();
  const views = {
    home: renderHome,
    training: renderTraining,
    study: renderStudy,
    modes: renderModes,
    range: renderRange,
    bot: renderBot,
    review: renderReview,
    analytics: renderAnalytics,
    leaderboard: renderLeaderboard
  };
  views[state.view]();
}

function updateShell() {
  document.querySelectorAll(".nav-button").forEach((button) => {
    button.classList.toggle("active", button.dataset.view === state.view);
  });
  document.getElementById("navHands").textContent = completedHands();
  document.getElementById("navAccuracy").textContent = `${accuracy()}%`;
  document.getElementById("navLeak").textContent = mainLeak();
}

function setHeader(title, subtitle) {
  viewTitle.textContent = title;
  viewSubtitle.textContent = subtitle;
}

function startTraining(bot, index) {
  state.view = "training";
  state.botBattle = bot;
  state.flowIndex = flowFromSeed(index);
  state.streetIndex = 0;
  state.awaiting = true;
  state.selectedAction = -1;
  state.lastFeedback = null;
  render();
}

function renderHome() {
  setHeader("训练大厅", "选择学习、专项练习、范围查看、人机实战或直接开始训练。");
  viewRoot.innerHTML = `
    <section class="metric-row">
      ${metric("完整牌局", completedHands())}
      ${metric("训练决策", state.history.length)}
      ${metric("正确率", `${accuracy()}%`)}
      ${metric("主要漏洞", mainLeak())}
    </section>
    <section class="dashboard-grid">
      ${homeCard("开始训练", "进入推荐行动训练桌，查看 GTO 频率、EV 对比和教练解释。", "training")}
      ${homeCard("学习策略", "翻前范围、翻后决策、下注尺度、实时复盘、人机实战模块。", "study")}
      ${homeCard("练习模式", "翻前、翻牌、转牌、河牌和漏洞专项。", "modes")}
      ${homeCard("范围查看", "按位置查看 13x13 手牌范围密度。", "range")}
      ${homeCard("实时复盘", "查看历史行动、最佳线路、EV 和错误类型。", "review")}
      ${homeCard("人机实战", "模拟机器人倾向，连续训练并复盘漏洞。", "bot")}
      ${homeCard("数据统计", "汇总正确率、街道表现和主要漏洞。", "analytics")}
      ${homeCard("排行榜", "本地正确率排名。", "leaderboard")}
    </section>
  `;
  viewRoot.querySelectorAll("[data-go]").forEach((card) => {
    card.addEventListener("click", () => {
      const target = card.dataset.go;
      if (target === "training") startTraining(false, 0);
      else showView(target);
    });
  });
}

function homeCard(title, body, target) {
  return `<article class="card" data-go="${target}" role="button" tabindex="0">
    <h3>${title}</h3>
    <p>${body}</p>
  </article>`;
}

function renderStudy() {
  setHeader("学习策略", "先理解策略模块，再进入专项练习。");
  viewRoot.innerHTML = `
    <section class="study-grid">
      ${studyCard("翻前范围", "按位置构建 RFI、跟注、3-bet 和防守范围。重点是位置、筹码深度和后位压迫。")}
      ${studyCard("翻后决策", "围绕范围优势、坚果优势、牌面动态性和阻断牌选择下注、过牌、跟注或加注。")}
      ${studyCard("下注尺度", "小尺度用于范围下注和高频压力；大尺度用于极化价值与诈唬。")}
      ${studyCard("实时复盘", "每次行动记录最佳线路、EV 差距和错误类型，用统计页定位漏洞。")}
      ${studyCard("人机实战", "连续手牌模拟对抗机器人，在 GTO 基线和剥削调整之间切换。")}
      <article class="card">
        <h3>进入训练</h3>
        <p>按街道和错误类型做专项训练。</p>
        <div class="card-actions"><button class="primary-button" id="goModesBtn" type="button">进入专项练习</button></div>
      </article>
    </section>
  `;
  document.getElementById("goModesBtn").addEventListener("click", () => showView("modes"));
}

function studyCard(title, body) {
  return `<article class="card"><h3>${title}</h3><p>${body}</p></article>`;
}

function renderModes() {
  setHeader("练习模式", "选择专项主题，进入完整牌局连续训练。");
  const modes = [
    ["翻前范围", "BTN open、盲注防守、3-bet 频率。", 3],
    ["翻牌持续下注", "范围优势、听牌保护、IP/OOP 防守。", 1],
    ["转牌 Probe", "阻断牌、薄价值、转牌反击。", 0],
    ["河牌纪律", "摊牌价值、薄价值、诈唬候选。", 2],
    ["漏洞专项", "过度跟注、低 EV 加注、错过价值。", 4],
    ["综合随机", "连续训练所有内置节点。", 0]
  ];
  viewRoot.innerHTML = `<section class="mode-grid">${modes.map(([title, body, index]) => `
    <article class="card">
      <h3>${title}</h3>
      <p>${body}</p>
      <div class="card-actions"><button class="primary-button" data-mode-start="${index}" type="button">开始完整牌局</button></div>
    </article>
  `).join("")}</section>`;
  viewRoot.querySelectorAll("[data-mode-start]").forEach((button) => {
    button.addEventListener("click", () => startTraining(false, Number(button.dataset.modeStart)));
  });
}

function renderBot() {
  setHeader("人机实战", "模拟机器人倾向，连续训练并记录复盘。");
  viewRoot.innerHTML = `
    <section class="card">
      <h3>模拟机器人对抗</h3>
      <p>机器人会呈现过度 Probe、低频诈唬、河牌保守等倾向。训练桌会显示机器人倾向，方便你在 GTO 基线与剥削调整之间切换。</p>
      <div class="card-actions"><button class="primary-button" id="startBotBtn" type="button">开始人机实战</button></div>
    </section>
  `;
  document.getElementById("startBotBtn").addEventListener("click", () => startTraining(true, 0));
}

function renderTraining() {
  updateShell();
  const flow = currentFlow();
  setHeader(state.botBattle ? "连续人机实战" : "完整牌局训练", "一手牌按翻前、翻牌、转牌、河牌连续推进，行动后不会停在中断面板。");
  viewRoot.innerHTML = document.getElementById("trainingTemplate").innerHTML;
  const hand = currentHand();
  document.getElementById("spotMode").textContent = `第 ${state.handNumber} 手 | ${state.streetIndex + 1}/${flow.spots.length} | ${hand.mode} | ${hand.street}`;
  document.getElementById("toggleAdviceBtn").textContent = state.adviceOpen ? "建议开启" : "建议关闭";
  document.getElementById("toggleAdviceBtn").addEventListener("click", () => {
    state.adviceOpen = !state.adviceOpen;
    renderTraining();
  });
  document.getElementById("villainAction").textContent = hand.villainAction;
  document.getElementById("handLine").textContent = `Hero: ${hand.heroPosition}  ${hand.heroCards.join(" ")}    公共牌: ${hand.board.join(" ") || "翻前"}`;
  document.getElementById("potLine").textContent = `底池: ${hand.pot}    有效筹码: ${hand.stack}`;
  renderAdvisor(hand);
  renderActions(hand);
  requestAnimationFrame(() => drawTable(hand));
}

function renderAdvisor(hand) {
  const content = document.getElementById("advisorContent");
  const progress = flowProgress();
  const feedback = state.lastFeedback ? `
    <section class="advisor-block last-feedback ${state.lastFeedback.good ? "is-good" : "is-leak"}">
      <h4>${state.lastFeedback.title}</h4>
      <p>${state.lastFeedback.message}</p>
      <small>${state.lastFeedback.next}</small>
    </section>
  ` : "";
  if (state.awaiting && state.adviceOpen) {
    content.innerHTML = `
      ${progress}
      ${feedback}
      ${advisorBlock("推荐行动", `${hand.recommendation} | 胜率 ${hand.winRate}`, "gold")}
      ${advisorBlock("GTO 频率", hand.mix, "blue")}
      ${advisorBlock("EV 对比", hand.ev)}
      ${advisorBlock("教练解释", hand.advice)}
      ${advisorBlock("训练目标", hand.goal, "gold")}
      ${advisorBlock("专项练习", hand.drill, "positive")}
      ${state.botBattle ? advisorBlock("机器人倾向", hand.botLine) : ""}
      <div class="factor-row">${hand.factors.map((f) => `<span class="factor-chip">${f}</span>`).join("")}</div>
    `;
  } else if (state.awaiting) {
    content.innerHTML = `
      ${progress}
      ${feedback}
      ${advisorBlock("盲练模式", "建议已隐藏。当前牌局仍会连续推进，完整复盘会记录每一街行动。")}
    `;
  } else {
    content.innerHTML = advisorBlock("本手摘要", hand.rangeNote);
  }
}

function flowProgress() {
  const flow = currentFlow();
  return `<section class="flow-progress" aria-label="牌局进度">
    ${flow.spots.map((spot, index) => `
      <span class="progress-dot ${index < state.streetIndex ? "done" : ""} ${index === state.streetIndex ? "current" : ""}">
        ${spot.street}
      </span>
    `).join("")}
  </section>`;
}

function advisorBlock(title, body, cls = "") {
  return `<section class="advisor-block"><h4>${title}</h4><p class="${cls}">${body}</p></section>`;
}

function renderActions(hand) {
  const row = document.getElementById("actionRow");
  row.className = "action-row";
  row.innerHTML = hand.actions.map((action, index) => `
    <button class="action-button" data-action="${index}" type="button">${action.label}<br>${action.amount}</button>
  `).join("");
  row.querySelectorAll("[data-action]").forEach((button) => {
    button.addEventListener("click", () => chooseAction(Number(button.dataset.action)));
  });
}

function chooseAction(index) {
  const hand = currentHand();
  const flow = currentFlow();
  const isFinalStreet = state.streetIndex === flow.spots.length - 1;
  const good = index === hand.recommendedIndex;
  const selected = hand.actions[index];
  state.selectedAction = index;
  state.history.push({
    title: `${flow.title} #${state.handNumber}`,
    handTitle: flow.title,
    handNo: state.handNumber,
    street: hand.street,
    streetStep: `${state.streetIndex + 1}/${flow.spots.length}`,
    mode: hand.mode,
    chosen: `${selected.label} ${selected.amount}`,
    best: hand.recommendation,
    ev: good ? "+0.9 EV" : hand.evLoss,
    errorType: good ? "正确线路" : hand.errorType,
    reason: hand.advice,
    handComplete: isFinalStreet
  });
  state.lastFeedback = {
    good,
    title: `${hand.street}反馈`,
    message: good
      ? `你的选择 ${selected.label} ${selected.amount} 符合推荐线路。`
      : `你的选择 ${selected.label} ${selected.amount} 偏离推荐，推荐线路是 ${hand.recommendation}，主要偏差：${hand.errorType}。`,
    next: isFinalStreet ? "本手已完成，已自动进入下一手翻前。" : `已自动进入下一街：${flow.spots[state.streetIndex + 1].street}。`
  };
  if (isFinalStreet) {
    state.flowIndex = (state.flowIndex + 1) % handFlows.length;
    state.streetIndex = 0;
    state.handNumber += 1;
  } else {
    state.streetIndex += 1;
  }
  state.awaiting = true;
  persist();
  renderTraining();
}

function renderRange() {
  setHeader("范围查看", "选择位置，查看 13x13 手牌范围密度。");
  viewRoot.innerHTML = `
    <section class="range-layout">
      <div class="position-list">
        ${positions.map((pos, index) => `<button class="nav-button ${index === state.rangePosition ? "active" : ""}" data-position="${index}" type="button">${pos}<small>${index === state.rangePosition ? "当前" : "查看"}</small></button>`).join("")}
      </div>
      <div class="card range-board">
        <h3>${positions[state.rangePosition]} 范围密度</h3>
        <p>${rangeSummary(state.rangePosition)}</p>
        <div class="range-grid">${rangeCells()}</div>
        <div class="range-legend">
          <span class="legend-chip" style="background:#8f671b">加注高频</span>
          <span class="legend-chip" style="background:#4e91d2">混合频率</span>
          <span class="legend-chip" style="background:#1b764e">防守/跟注</span>
          <span class="legend-chip" style="background:#222d28">弃牌为主</span>
        </div>
      </div>
    </section>
  `;
  viewRoot.querySelectorAll("[data-position]").forEach((button) => {
    button.addEventListener("click", () => {
      state.rangePosition = Number(button.dataset.position);
      renderRange();
    });
  });
}

function rangeCells() {
  const cells = [];
  for (let r = 0; r < 13; r += 1) {
    for (let c = 0; c < 13; c += 1) {
      const label = r === c ? `${ranks[r]}${ranks[c]}` : r < c ? `${ranks[r]}${ranks[c]}s` : `${ranks[c]}${ranks[r]}o`;
      const strength = rangeStrength(r, c, state.rangePosition);
      cells.push(`<div class="range-cell" style="background:${rangeColor(strength)}">${label}</div>`);
    }
  }
  return cells.join("");
}

function rangeStrength(r, c, pos) {
  const pairBonus = r === c ? 6 : 0;
  const highCard = 24 - r - c;
  const suitedBonus = r < c ? 3 : 0;
  const connectorBonus = Math.abs(r - c) <= 2 ? 3 : 0;
  return highCard + pairBonus + suitedBonus + connectorBonus + pos * 2;
}

function rangeColor(strength) {
  if (strength >= 28) return "#8f671b";
  if (strength >= 23) return "#4e91d2";
  if (strength >= 18) return "#1b764e";
  return "#222d28";
}

function rangeSummary(pos) {
  return [
    "UTG 范围紧，优先高牌强踢脚、对子和高质量同花连张。",
    "LJ 比 UTG 略宽，但仍需要控制弱踢脚和被压制组合。",
    "HJ 开始加入更多同花 A、Broadway 和中等对子。",
    "CO 利用后位优势扩大偷盲范围，增加 suited connector。",
    "BTN 范围最宽，位置红利允许大量边缘盈利组合。",
    "SB 需要考虑无位置劣势，开池更偏极化。",
    "BB 以防守为主，跟注范围宽，但 3-bet 需要阻断牌和权益。"
  ][pos];
}

function renderReview() {
  setHeader("实时复盘", "查看历史行动、最佳线路、EV 和错误类型。");
  if (!state.history.length) {
    viewRoot.innerHTML = `<section class="card"><h3>暂无复盘记录</h3><p>先进入完整牌局训练，系统会记录每一街行动。</p><div class="card-actions"><button class="primary-button" id="reviewStartBtn" type="button">开始训练</button></div></section>`;
    document.getElementById("reviewStartBtn").addEventListener("click", () => startTraining(false, 0));
    return;
  }
  viewRoot.innerHTML = `<section class="review-list">${[...state.history].reverse().map((record) => `
    <article class="card review-card">
      <h3>${record.title} | ${record.street} ${record.streetStep || ""}</h3>
      <p>你的行动：${record.chosen}</p>
      <p>最佳线路：${record.best}　EV：${record.ev}</p>
      <p>错误类型：${record.errorType}　模式：${record.mode}</p>
      <p>${record.reason}</p>
    </article>
  `).join("")}</section>`;
}

function renderAnalytics() {
  setHeader("数据统计", "按街道、错误类型和正确率汇总。");
  viewRoot.innerHTML = `
    <section class="metric-row">
      ${metric("完整牌局", completedHands())}
      ${metric("训练决策", state.history.length)}
      ${metric("正确率", `${accuracy()}%`)}
      ${metric("EV 汇总", totalEv())}
    </section>
    <section class="analytics-grid">
      ${studyCard("街道表现", ["翻前", "翻牌", "转牌", "河牌"].map(streetSummary).join("　"))}
      ${studyCard("错误类型", leakBreakdown())}
      ${studyCard("训练建议", state.history.length ? "优先复盘出现次数最多的错误类型，再做完整牌局连续训练。" : "先完成 2-3 手完整牌局，统计页会显示主要漏洞。")}
      ${studyCard("当前样本", `本地记录 ${completedHands()} 手完整牌局、${state.history.length} 次决策。`)}
    </section>
  `;
}

function renderLeaderboard() {
  setHeader("排行榜", "本地训练正确率排名。");
  const rows = [
    ["1", "SharkMode", "78.4%"],
    ["2", "RangeBuilder", "74.1%"],
    ["3", "你", `${accuracy()}%`],
    ["4", "ValueTown", "66.8%"]
  ];
  viewRoot.innerHTML = `<section class="leaderboard-list">${rows.map(([rank, name, score]) => `
    <article class="card leader-row">
      <strong>${rank}</strong>
      <span>${name}</span>
      <span>正确率 ${score}</span>
    </article>
  `).join("")}</section>`;
}

function metric(label, value) {
  return `<div class="metric-card"><span>${label}</span><strong>${value}</strong></div>`;
}

function flowFromSeed(seed) {
  if (seed === 4) return 1;
  if (seed === 1) return 1;
  return 0;
}

function currentFlow() {
  return handFlows[state.flowIndex % handFlows.length];
}

function currentHand() {
  const flow = currentFlow();
  const spot = flow.spots[state.streetIndex % flow.spots.length];
  return {
    ...spot,
    title: flow.title,
    mode: spot.mode || flow.mode,
    heroPosition: spot.heroPosition || flow.heroPosition,
    heroCards: spot.heroCards || flow.heroCards,
    stack: spot.stack || flow.stack
  };
}

function completedHands() {
  return completedHandsFrom(state.history);
}

function accuracy() {
  if (!state.history.length) return 0;
  const good = state.history.filter((record) => record.ev.startsWith("+")).length;
  return Math.round((good * 100) / state.history.length);
}

function totalEv() {
  if (!state.history.length) return "0 EV";
  const sum = state.history.reduce((acc, record) => acc + Number.parseFloat(record.ev.replace(" EV", "")), 0);
  return `${sum.toFixed(1)} EV`;
}

function mainLeak() {
  if (!state.history.length) return "暂无";
  const counts = countBy(state.history.map((record) => record.errorType));
  return Object.entries(counts).sort((a, b) => b[1] - a[1])[0][0];
}

function leakBreakdown() {
  if (!state.history.length) return "暂无错误样本。";
  return Object.entries(countBy(state.history.map((record) => record.errorType)))
    .map(([name, count]) => `${name} ${count} 次`)
    .join("　");
}

function streetSummary(street) {
  const records = state.history.filter((record) => record.street === street);
  if (!records.length) return `${street}: 暂无`;
  const good = records.filter((record) => record.ev.startsWith("+")).length;
  return `${street}: ${Math.round((good * 100) / records.length)}%`;
}

function countBy(values) {
  return values.reduce((acc, value) => {
    acc[value] = (acc[value] || 0) + 1;
    return acc;
  }, {});
}

function drawTable(hand) {
  const canvas = document.getElementById("pokerCanvas");
  if (!canvas) return;
  const ctx = canvas.getContext("2d");
  const w = canvas.width;
  const h = canvas.height;
  ctx.clearRect(0, 0, w, h);
  const table = { x: 70, y: 90, w: w - 140, h: h - 190 };
  const gradient = ctx.createLinearGradient(0, table.y, 0, table.y + table.h);
  gradient.addColorStop(0, "#146744");
  gradient.addColorStop(1, "#054632");
  ctx.fillStyle = gradient;
  ellipse(ctx, table.x + table.w / 2, table.y + table.h / 2, table.w / 2, table.h / 2);
  ctx.fill();
  ctx.strokeStyle = "#09110d";
  ctx.lineWidth = 12;
  ctx.stroke();
  ctx.strokeStyle = "rgba(216,169,65,.55)";
  ctx.lineWidth = 3;
  ellipse(ctx, table.x + table.w / 2, table.y + table.h / 2, table.w / 2 - 25, table.h / 2 - 25);
  ctx.stroke();
  drawPot(ctx, w / 2, 172, hand.pot);
  drawCards(ctx, hand.board, w / 2, 292, false);
  const players = [
    ["小林", "BB", 0.20, 0.32],
    ["丹尼", "UTG", 0.50, 0.18],
    ["阿历", "CO", 0.80, 0.32],
    ["艾米", "BTN", 0.78, 0.74],
    ["珍妮", "SB", 0.22, 0.74]
  ];
  players.forEach(([name, pos, x, y], index) => drawPlayer(ctx, name, pos, String(hand.stack - index * 320), w * x, h * y, false));
  drawPlayer(ctx, "你", hand.heroPosition, String(hand.stack), w / 2, h * 0.76, true);
  drawCards(ctx, hand.heroCards, w / 2, h * 0.64, true);
}

function ellipse(ctx, cx, cy, rx, ry) {
  ctx.beginPath();
  ctx.ellipse(cx, cy, rx, ry, 0, 0, Math.PI * 2);
}

function drawPot(ctx, cx, cy, pot) {
  roundRect(ctx, cx - 115, cy - 44, 230, 88, 20);
  ctx.fillStyle = "rgba(0,0,0,.75)";
  ctx.fill();
  ctx.textAlign = "center";
  ctx.font = "700 18px Microsoft YaHei";
  ctx.fillStyle = "#a3b0a6";
  ctx.fillText("总底池", cx, cy - 8);
  ctx.font = "900 34px Microsoft YaHei";
  ctx.fillStyle = "#d8a941";
  ctx.fillText(String(pot), cx, cy + 32);
}

function drawPlayer(ctx, name, pos, stack, cx, cy, hero) {
  ctx.fillStyle = hero ? "#d8a941" : "#2a3730";
  ctx.beginPath();
  ctx.arc(cx, cy, hero ? 36 : 32, 0, Math.PI * 2);
  ctx.fill();
  ctx.textAlign = "center";
  ctx.font = "900 22px Microsoft YaHei";
  ctx.fillStyle = hero ? "#18221c" : "#aabcae";
  ctx.fillText(name[0], cx, cy + 8);
  ctx.font = "700 18px Microsoft YaHei";
  ctx.fillStyle = "#eff4ec";
  ctx.fillText(name, cx, cy + 58);
  ctx.font = "14px Microsoft YaHei";
  ctx.fillStyle = "#a3b0a6";
  ctx.fillText(`${pos}  ${stack}`, cx, cy + 80);
}

function drawCards(ctx, cards, cx, cy, hero) {
  if (!cards.length) return;
  const cardW = hero ? 62 : 54;
  const cardH = hero ? 86 : 78;
  const gap = 10;
  const start = cx - (cards.length * cardW + (cards.length - 1) * gap) / 2;
  cards.forEach((card, index) => {
    const x = start + index * (cardW + gap);
    roundRect(ctx, x, cy - cardH / 2, cardW, cardH, 8);
    ctx.fillStyle = "#f4f6ef";
    ctx.fill();
    ctx.strokeStyle = "#caced0";
    ctx.lineWidth = 2;
    ctx.stroke();
    const suit = card.slice(-1);
    ctx.fillStyle = suit === "h" || suit === "d" ? "#c2232d" : "#1b1f1e";
    ctx.textAlign = "center";
    ctx.font = `900 ${hero ? 26 : 22}px Microsoft YaHei`;
    ctx.fillText(card.slice(0, -1).toUpperCase(), x + cardW / 2, cy - 4);
    ctx.font = `700 ${hero ? 16 : 14}px Microsoft YaHei`;
    ctx.fillText(suit.toUpperCase(), x + cardW / 2, cy + 28);
  });
}

function roundRect(ctx, x, y, width, height, radius) {
  ctx.beginPath();
  ctx.moveTo(x + radius, y);
  ctx.lineTo(x + width - radius, y);
  ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
  ctx.lineTo(x + width, y + height - radius);
  ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
  ctx.lineTo(x + radius, y + height);
  ctx.quadraticCurveTo(x, y + height, x, y + height - radius);
  ctx.lineTo(x, y + radius);
  ctx.quadraticCurveTo(x, y, x + radius, y);
}

init();
