package com.codex.pokertrainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private static final int BG = Color.rgb(5, 10, 7);
    private static final int PANEL = Color.rgb(15, 24, 21);
    private static final int PANEL_2 = Color.rgb(22, 35, 31);
    private static final int GOLD = Color.rgb(216, 169, 65);
    private static final int GOLD_DARK = Color.rgb(143, 103, 27);
    private static final int GREEN = Color.rgb(37, 164, 103);
    private static final int RED = Color.rgb(208, 66, 70);
    private static final int BLUE = Color.rgb(78, 145, 210);
    private static final int TEXT = Color.rgb(239, 244, 236);
    private static final int MUTED = Color.rgb(163, 176, 166);

    private final List<ActionRecord> history = new ArrayList<ActionRecord>();
    private Hand[] hands;
    private int handIndex;
    private int selectedAction = -1;
    private int smallBlind = 50;
    private int bigBlind = 100;
    private int startingStack = 10000;
    private int rangePositionIndex = 4;
    private boolean awaitingAction = true;
    private boolean copilotOpen = true;
    private boolean trainingStarted;
    private boolean reviewBackToGame;
    private boolean botBattle;
    private String profile = "GTO-剥削融合";
    private String focusMode = "综合训练";

    private enum Screen {
        HOME,
        GAME,
        REVIEW,
        LEADERBOARD,
        STUDY,
        MODES,
        RANGE,
        BOT,
        ANALYTICS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hands = buildHands();
        showHome();
    }

    private void showHome() {
        botBattle = false;
        FrameLayout root = baseRoot();
        ScrollView scroll = new ScrollView(this);
        root.addView(scroll, frame(-1, -1));

        LinearLayout shell = vertical();
        shell.setGravity(Gravity.CENTER_HORIZONTAL);
        shell.setPadding(dp(42), dp(22), dp(42), dp(22));
        scroll.addView(shell, linear(-1, -2));

        TextView title = text("中文 GTO 德扑训练器", 32, TEXT, true);
        title.setGravity(Gravity.CENTER);
        shell.addView(title, linear(-1, -2));

        TextView subtitle = text("学习策略、专项练习、实时复盘、人机实战和漏洞统计的原创融合版。", 15, MUTED, false);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(8), 0, dp(18));
        shell.addView(subtitle, linear(-1, -2));

        LinearLayout stats = horizontal();
        stats.setGravity(Gravity.CENTER);
        stats.addView(statChip("本轮手牌", String.valueOf(history.size())));
        stats.addView(statChip("正确率", currentAccuracy() + "%"));
        stats.addView(statChip("训练风格", profile));
        stats.addView(statChip("当前模式", focusMode));
        shell.addView(stats, linear(-1, -2));

        LinearLayout row1 = horizontal();
        row1.setGravity(Gravity.CENTER);
        row1.setPadding(0, dp(22), 0, 0);
        shell.addView(row1, linear(-1, -2));

        row1.addView(homeCard("开始训练", "按当前模式进入推荐决策训练。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTraining(true, false, "综合训练", 0);
            }
        }));
        row1.addView(homeCard("学习策略", "按位置、牌面和下注尺度学习。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.STUDY);
            }
        }));
        row1.addView(homeCard("练习模式", "选择翻前、翻牌、转牌、河牌专项。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.MODES);
            }
        }));
        row1.addView(homeCard("范围查看", "查看位置范围和组合密度。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.RANGE);
            }
        }));

        LinearLayout row2 = horizontal();
        row2.setGravity(Gravity.CENTER);
        row2.setPadding(0, dp(14), 0, 0);
        shell.addView(row2, linear(-1, -2));

        row2.addView(homeCard("实时复盘", "查看行动、最佳线路和 EV 损失。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReview(false);
            }
        }));
        row2.addView(homeCard("人机实战", "连续对抗模拟机器人并复盘漏洞。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.BOT);
            }
        }));
        row2.addView(homeCard("数据统计", "按街道、错误类型和正确率汇总。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.ANALYTICS);
            }
        }));
        row2.addView(homeCard("排行榜", "追踪本地训练正确率。", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.LEADERBOARD);
            }
        }));

        TextView footer = text("离线训练版本。策略数据为内置教学样例，不连接任何外部求解器。", 12, Color.rgb(120, 132, 123), false);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, dp(20), 0, 0);
        shell.addView(footer, linear(-1, -2));

        setContentView(root);
    }

    private void render(Screen screen) {
        if (screen == Screen.HOME) {
            showHome();
        } else if (screen == Screen.GAME) {
            renderGame();
        } else if (screen == Screen.REVIEW) {
            openReview(false);
        } else if (screen == Screen.LEADERBOARD) {
            renderLeaderboard();
        } else if (screen == Screen.STUDY) {
            renderStudy();
        } else if (screen == Screen.MODES) {
            renderModes();
        } else if (screen == Screen.RANGE) {
            renderRangeViewer();
        } else if (screen == Screen.BOT) {
            renderBotIntro();
        } else {
            renderAnalytics();
        }
    }

    private void startTraining(boolean reset, boolean bot, String mode, int startIndex) {
        trainingStarted = true;
        botBattle = bot;
        focusMode = mode;
        if (reset) {
            history.clear();
            handIndex = Math.max(0, startIndex);
        }
        awaitingAction = true;
        selectedAction = -1;
        renderGame();
    }

    private void openReview(boolean returnToGame) {
        reviewBackToGame = returnToGame;
        renderReview();
    }

    private void renderGame() {
        final Hand hand = hands[handIndex % hands.length];
        FrameLayout root = baseRoot();

        LinearLayout shell = vertical();
        root.addView(shell, frame(-1, -1));

        LinearLayout top = horizontal();
        top.setGravity(Gravity.CENTER_VERTICAL);
        top.setPadding(dp(16), dp(10), dp(16), dp(8));
        top.setBackgroundColor(Color.rgb(7, 13, 10));
        shell.addView(top, linear(-1, dp(54)));

        Button home = smallButton("大厅");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome();
            }
        });
        top.addView(home, linear(dp(96), dp(42)));

        String prefix = botBattle ? "人机实战 | " : "";
        TextView header = text(prefix + hand.title, 18, TEXT, true);
        header.setGravity(Gravity.CENTER);
        top.addView(header, linear(0, -1, 1f));

        Button copilot = smallButton(copilotOpen ? "建议开启" : "建议关闭");
        copilot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copilotOpen = !copilotOpen;
                renderGame();
            }
        });
        top.addView(copilot, linear(dp(138), dp(42)));

        Button review = smallButton("复盘");
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReview(true);
            }
        });
        top.addView(review, linear(dp(104), dp(42)));

        LinearLayout body = horizontal();
        body.setPadding(dp(14), dp(10), dp(14), dp(10));
        shell.addView(body, linear(-1, 0, 1f));

        PokerTableView table = new PokerTableView(this, hand, awaitingAction, selectedAction);
        body.addView(table, linear(0, -1, 1f));

        ScrollView sideScroll = new ScrollView(this);
        sideScroll.setFillViewport(true);
        sideScroll.setBackground(panelBg(PANEL));
        body.addView(sideScroll, linear(dp(392), -1));

        LinearLayout side = vertical();
        side.setPadding(dp(14), dp(12), dp(14), dp(12));
        sideScroll.addView(side);

        TextView stage = text("训练场景  |  " + hand.mode + "  |  " + hand.street, 11, GOLD, true);
        side.addView(stage, linear(-1, -2));

        TextView spot = text(hand.villainAction, 16, TEXT, true);
        spot.setPadding(0, dp(4), 0, dp(4));
        side.addView(spot, linear(-1, -2));

        side.addView(text("Hero: " + hand.heroPosition + "  " + hand.heroCards + "    公共牌: " + hand.board, 12, MUTED, false), linear(-1, -2));
        side.addView(text("底池: " + hand.pot + "    有效筹码: " + hand.heroStack, 12, MUTED, false), linear(-1, -2));
        if (botBattle) {
            TextView botLine = text("机器人倾向：" + hand.botLine, 12, RED, true);
            botLine.setPadding(0, dp(4), 0, 0);
            side.addView(botLine, linear(-1, -2));
        }

        if (awaitingAction && copilotOpen) {
            addCoachLine(side, "推荐行动", hand.recommendation + "  |  胜率 " + hand.winRate, hand.recommendedColor(), 1);
            addCoachLine(side, "GTO 频率", hand.mixSummary(), BLUE, 2);
            addCoachLine(side, "EV 对比", hand.evSummary(), MUTED, 2);
            addCoachLine(side, "教练解释", hand.advice, MUTED, 3);
            addCoachLine(side, "训练目标", hand.goal, GOLD, 2);
            addCoachLine(side, "专项练习", hand.drill, GREEN, 2);
            for (String factor : hand.factors) {
                TextView chip = text("  " + factor + "  ", 12, TEXT, false);
                chip.setBackground(panelBg(Color.rgb(30, 45, 39)));
                LinearLayout.LayoutParams cp = linear(-1, -2);
                cp.setMargins(0, dp(5), 0, 0);
                side.addView(chip, cp);
            }
        } else if (awaitingAction) {
            addCoachLine(side, "盲练模式", "建议已隐藏。先按自己的判断行动，之后再看复盘。", MUTED, 3);
        } else {
            addCoachLine(side, "本手摘要", hand.rangeNote, MUTED, 3);
        }

        LinearLayout bottom = awaitingAction ? horizontal() : vertical();
        bottom.setGravity(awaitingAction ? Gravity.CENTER_VERTICAL : Gravity.CENTER);
        bottom.setPadding(dp(16), dp(10), dp(16), dp(12));
        bottom.setBackgroundColor(Color.rgb(8, 14, 11));
        shell.addView(bottom, linear(-1, awaitingAction ? dp(110) : dp(126)));

        if (awaitingAction) {
            for (int i = 0; i < hand.actions.length; i++) {
                final int index = i;
                Button action = actionButton(hand.actions[i], hand.amounts[i]);
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseAction(index);
                    }
                });
                LinearLayout.LayoutParams ap = linear(0, dp(72), 1f);
                ap.setMargins(dp(7), 0, dp(7), 0);
                bottom.addView(action, ap);
            }
        } else {
            boolean good = selectedAction == hand.recommendedIndex;
            TextView result = text(good ? "正确决策" : "建议复盘此手", 16, good ? GREEN : RED, true);
            result.setGravity(Gravity.CENTER);
            bottom.addView(result, linear(-1, -2));

            TextView summary = text(buildFeedback(hand), 12, MUTED, false);
            summary.setGravity(Gravity.CENTER);
            summary.setMaxLines(2);
            bottom.addView(summary, linear(-1, -2));

            LinearLayout controls = horizontal();
            controls.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams cp = linear(-1, dp(62));
            cp.setMargins(0, dp(8), 0, 0);
            bottom.addView(controls, cp);

            Button next = primaryButton((handIndex + 1) % hands.length == 0 ? "下一轮" : "下一手");
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handIndex = (handIndex + 1) % hands.length;
                    awaitingAction = true;
                    selectedAction = -1;
                    renderGame();
                }
            });
            LinearLayout.LayoutParams np = linear(0, -1, 1f);
            np.setMargins(dp(7), 0, dp(7), 0);
            controls.addView(next, np);

            Button handReview = secondaryButton("打开复盘", String.valueOf(history.size()) + " 手牌");
            handReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openReview(true);
                }
            });
            LinearLayout.LayoutParams rp = linear(0, -1, 1f);
            rp.setMargins(dp(7), 0, dp(7), 0);
            controls.addView(handReview, rp);
        }

        setContentView(root);
    }

    private void chooseAction(int index) {
        Hand hand = hands[handIndex % hands.length];
        selectedAction = index;
        awaitingAction = false;
        history.add(new ActionRecord(
                hand.title,
                hand.street,
                hand.mode,
                hand.actions[index] + " " + hand.amounts[index],
                hand.actions[hand.recommendedIndex] + " " + hand.amounts[hand.recommendedIndex],
                index == hand.recommendedIndex ? "+0.9 EV" : hand.evLoss,
                hand.errorType,
                hand.advice
        ));
        renderGame();
    }

    private String buildFeedback(Hand hand) {
        String selected = hand.actions[selectedAction] + " " + hand.amounts[selectedAction];
        String best = hand.actions[hand.recommendedIndex] + " " + hand.amounts[hand.recommendedIndex];
        if (selectedAction == hand.recommendedIndex) {
            return "你的选择是 " + selected + "。符合推荐线路。练习重点：" + hand.drill;
        }
        return "你的选择是 " + selected + "。推荐线路：" + best + "。主要偏差：" + hand.errorType + "。";
    }

    private void renderStudy() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "学习策略");
        TextView moduleLine = text("模块：翻前范围 / 翻后决策 / 下注尺度 / 实时复盘 / 人机实战", 13, GOLD, true);
        moduleLine.setGravity(Gravity.CENTER);
        moduleLine.setPadding(0, dp(8), 0, dp(6));
        shell.addView(moduleLine, linear(-1, -2));
        ScrollView scroll = new ScrollView(this);
        LinearLayout list = vertical();
        scroll.addView(list);
        shell.addView(scroll, linear(-1, 0, 1f));

        addStudyCard(list, "翻前范围", "按位置构建 RFI、跟注、3-bet 和防守范围。重点不是记单手牌，而是理解位置、筹码深度和后位压迫。");
        addStudyCard(list, "翻后决策", "围绕范围优势、坚果优势、牌面动态性和阻断牌选择下注、过牌、跟注或加注。");
        addStudyCard(list, "下注尺度", "小尺度用于范围下注和高频压力；大尺度用于极化价值与诈唬。训练时同时看频率和 EV。");
        addStudyCard(list, "实时复盘", "每次行动记录最佳线路、EV 差距和错误类型，用统计页定位过度跟注、低 EV 加注等漏洞。");
        addStudyCard(list, "人机实战", "连续手牌模拟对抗机器人。机器人倾向会提示你如何在 GTO 基线和剥削调整之间切换。");

        Button start = primaryButton("进入专项练习");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render(Screen.MODES);
            }
        });
        LinearLayout.LayoutParams sp = linear(-1, dp(64));
        sp.setMargins(0, dp(12), 0, 0);
        shell.addView(start, sp);

        setContentView(root);
    }

    private void renderModes() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "练习模式");

        LinearLayout grid = vertical();
        grid.setPadding(0, dp(14), 0, 0);
        shell.addView(grid, linear(-1, 0, 1f));

        LinearLayout row1 = horizontal();
        grid.addView(row1, linear(-1, 0, 1f));
        row1.addView(modeCard("翻前范围", "BTN open、盲注防守、3-bet 频率。", 3, "翻前专项"));
        row1.addView(modeCard("翻牌持续下注", "范围优势、听牌保护、IP/OOP 防守。", 1, "翻牌专项"));
        row1.addView(modeCard("转牌 Probe", "阻断牌、薄价值、转牌反击。", 0, "转牌专项"));

        LinearLayout row2 = horizontal();
        grid.addView(row2, linear(-1, 0, 1f));
        row2.addView(modeCard("河牌纪律", "摊牌价值、薄价值、诈唬候选。", 2, "河牌专项"));
        row2.addView(modeCard("漏洞专项", "过度跟注、低 EV 加注、错过价值。", 4, "漏洞专项"));
        row2.addView(modeCard("综合随机", "连续训练所有内置节点。", 0, "综合训练"));

        setContentView(root);
    }

    private void renderRangeViewer() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "范围查看");

        LinearLayout body = horizontal();
        body.setPadding(0, dp(14), 0, 0);
        shell.addView(body, linear(-1, 0, 1f));

        LinearLayout left = vertical();
        left.setPadding(0, 0, dp(14), 0);
        body.addView(left, linear(dp(330), -1));

        final String[] positions = {"UTG", "LJ", "HJ", "CO", "BTN", "SB", "BB"};
        for (int i = 0; i < positions.length; i++) {
            final int index = i;
            Button b = smallButton(positions[i] + (i == rangePositionIndex ? "  当前" : ""));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rangePositionIndex = index;
                    renderRangeViewer();
                }
            });
            LinearLayout.LayoutParams bp = linear(-1, dp(48));
            bp.setMargins(0, 0, 0, dp(8));
            left.addView(b, bp);
        }

        LinearLayout right = vertical();
        body.addView(right, linear(0, -1, 1f));

        TextView title = text(positions[rangePositionIndex] + " 范围密度", 20, TEXT, true);
        right.addView(title, linear(-1, -2));
        right.addView(text(rangeSummary(rangePositionIndex), 13, MUTED, false), linear(-1, -2));

        RangeGridView grid = new RangeGridView(this, rangePositionIndex);
        LinearLayout.LayoutParams gp = linear(-1, 0, 1f);
        gp.setMargins(0, dp(12), 0, dp(12));
        right.addView(grid, gp);

        LinearLayout legend = horizontal();
        legend.addView(legendChip("加注高频", GOLD));
        legend.addView(legendChip("混合频率", BLUE));
        legend.addView(legendChip("防守/跟注", GREEN));
        legend.addView(legendChip("弃牌为主", Color.rgb(48, 61, 54)));
        right.addView(legend, linear(-1, dp(42)));

        setContentView(root);
    }

    private void renderBotIntro() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "人机实战");

        LinearLayout body = vertical();
        body.setGravity(Gravity.CENTER);
        shell.addView(body, linear(-1, 0, 1f));

        TextView title = text("模拟机器人对抗", 28, TEXT, true);
        title.setGravity(Gravity.CENTER);
        body.addView(title, linear(-1, -2));
        TextView desc = text("机器人会给出固定倾向：过度 Probe、低频诈唬或河牌保守。你连续决策，系统记录 EV、错误类型和复盘。", 16, MUTED, false);
        desc.setGravity(Gravity.CENTER);
        desc.setPadding(dp(120), dp(12), dp(120), dp(20));
        body.addView(desc, linear(-1, -2));

        Button start = primaryButton("开始人机实战");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTraining(true, true, "人机实战", 0);
            }
        });
        body.addView(start, linear(dp(360), dp(70)));

        setContentView(root);
    }

    private void renderReview() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "实时复盘");

        ScrollView scroll = new ScrollView(this);
        LinearLayout list = vertical();
        scroll.addView(list);
        shell.addView(scroll, linear(-1, 0, 1f));

        if (history.isEmpty()) {
            TextView empty = text("还没有可复盘的手牌。先完成一次训练决策。", 18, MUTED, false);
            empty.setGravity(Gravity.CENTER);
            list.addView(empty, linear(-1, dp(120)));
            Button start = primaryButton("开始训练");
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startTraining(true, false, "综合训练", 0);
                }
            });
            list.addView(start, linear(-1, dp(68)));
        } else {
            for (int i = history.size() - 1; i >= 0; i--) {
                ActionRecord record = history.get(i);
                LinearLayout card = vertical();
                card.setPadding(dp(18), dp(14), dp(18), dp(14));
                card.setBackground(panelBg(PANEL));
                LinearLayout.LayoutParams lp = linear(-1, -2);
                lp.setMargins(0, 0, 0, dp(12));
                list.addView(card, lp);
                card.addView(text(record.title + "  |  " + record.street, 17, TEXT, true), linear(-1, -2));
                card.addView(text("你的行动：" + record.chosen, 14, MUTED, false), linear(-1, -2));
                card.addView(text("最佳线路：" + record.best + "   EV: " + record.ev, 14, record.ev.startsWith("+") ? GREEN : RED, false), linear(-1, -2));
                card.addView(text("错误类型：" + record.errorType + "   模式：" + record.mode, 13, GOLD, false), linear(-1, -2));
                TextView why = text(record.reason, 13, Color.rgb(182, 192, 184), false);
                why.setPadding(0, dp(8), 0, 0);
                card.addView(why, linear(-1, -2));
            }
        }

        setContentView(root);
    }

    private void renderAnalytics() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "数据统计");

        LinearLayout stats = horizontal();
        stats.setGravity(Gravity.CENTER);
        stats.setPadding(0, dp(12), 0, dp(14));
        shell.addView(stats, linear(-1, -2));
        stats.addView(statChip("已练手牌", String.valueOf(history.size())));
        stats.addView(statChip("正确率", currentAccuracy() + "%"));
        stats.addView(statChip("EV 结果", totalEvLabel()));
        stats.addView(statChip("主要漏洞", mainLeak()));

        ScrollView scroll = new ScrollView(this);
        LinearLayout list = vertical();
        scroll.addView(list);
        shell.addView(scroll, linear(-1, 0, 1f));

        addStudyCard(list, "街道表现", streetSummary("翻前") + "  " + streetSummary("翻牌") + "  " + streetSummary("转牌") + "  " + streetSummary("河牌"));
        addStudyCard(list, "错误类型", leakBreakdown());
        addStudyCard(list, "训练建议", history.isEmpty() ? "先完成 5-10 手训练，统计页会显示主要漏洞。" : "优先复盘出现次数最多的错误类型，再用练习模式做专项训练。");

        setContentView(root);
    }

    private void renderLeaderboard() {
        FrameLayout root = baseRoot();
        LinearLayout shell = pageShell(root, "排行榜");

        String[] rows = {
                "1   SharkMode      正确率 78.4%",
                "2   RangeBuilder    正确率 74.1%",
                "3   你              正确率 " + currentAccuracy() + "%",
                "4   ValueTown       正确率 66.8%"
        };
        for (String row : rows) {
            TextView r = text(row, 20, row.contains("你") ? GOLD : TEXT, true);
            r.setPadding(dp(24), dp(16), dp(24), dp(16));
            r.setBackground(panelBg(PANEL));
            LinearLayout.LayoutParams lp = linear(dp(680), -2);
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            lp.setMargins(0, dp(16), 0, 0);
            shell.addView(r, lp);
        }

        setContentView(root);
    }

    private void showCustomGameDialog() {
        LinearLayout box = vertical();
        box.setPadding(dp(18), dp(8), dp(18), 0);
        final EditText blinds = new EditText(this);
        blinds.setHint("小盲，例如 50");
        blinds.setInputType(InputType.TYPE_CLASS_NUMBER);
        blinds.setText(String.valueOf(smallBlind));
        final EditText stack = new EditText(this);
        stack.setHint("起始筹码，例如 10000");
        stack.setInputType(InputType.TYPE_CLASS_NUMBER);
        stack.setText(String.valueOf(startingStack));
        box.addView(blinds, linear(-1, -2));
        box.addView(stack, linear(-1, -2));

        new AlertDialog.Builder(this)
                .setTitle("训练设置")
                .setMessage("设置本地训练局。大盲会自动设为小盲的 2 倍。")
                .setView(box)
                .setNegativeButton("取消", null)
                .setPositiveButton("开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            smallBlind = Math.max(1, Integer.parseInt(blinds.getText().toString()));
                            bigBlind = smallBlind * 2;
                            startingStack = Math.max(bigBlind * 20, Integer.parseInt(stack.getText().toString()));
                        } catch (Exception ignored) {
                            smallBlind = 50;
                            bigBlind = 100;
                            startingStack = 10000;
                        }
                        hands = buildHands();
                        startTraining(true, false, "自定义训练", 0);
                    }
                })
                .show();
    }

    private Hand[] buildHands() {
        int bb = Math.max(2, bigBlind);
        return new Hand[]{
                new Hand(
                        "BTN 对 BB | 转牌 Probe",
                        "现金局 / 6 人桌 / 100bb",
                        "转牌",
                        "BTN",
                        "Ac Qc",
                        "Ts 8d 3c 2c",
                        bb * 18,
                        startingStack,
                        "BB 下注 " + (bb * 10),
                        "训练面对过宽转牌 Probe 时，利用阻断牌和权益做反击。",
                        new String[]{"弃牌", "跟注", "加注", "全下"},
                        new String[]{"0", String.valueOf(bb * 10), String.valueOf(bb * 24), String.valueOf(bb * 91)},
                        2,
                        "加注 " + (bb * 24),
                        "31%",
                        "弃牌 8% / 跟注 37% / 加注 55% / 全下 0%",
                        "0.00 / -0.35 / +0.90 / -1.10",
                        "-1.4 EV",
                        "过度跟注",
                        "对手 Probe 频率偏高。顶对加坚果同花阻断牌，适合用加注获取薄价值并保护权益。",
                        "当价值和保护同时提升手牌时，优先选择加注。",
                        "机器人转牌 Probe 过宽，但面对加注弃牌偏多。",
                        "范围优势在 BTN，Ac 阻断坚果同花继续牌。",
                        new String[]{"阻断牌优势", "保护价值", "转牌反击", "常见漏洞：过度跟注"}
                ),
                new Hand(
                        "CO 对 BTN | 翻牌持续下注",
                        "单加注底池 / 有位置",
                        "翻牌",
                        "BTN",
                        "Kh Qh",
                        "Qd 7s 2h",
                        bb * 12,
                        startingStack,
                        "CO 持续下注 " + (bb * 5),
                        "练习有位置翻牌防守：保留对手被压制牌和诈唬牌。",
                        new String[]{"弃牌", "跟注", "加注"},
                        new String[]{"0", String.valueOf(bb * 5), String.valueOf(bb * 16)},
                        1,
                        "跟注 " + (bb * 5),
                        "42%",
                        "弃牌 0% / 跟注 82% / 加注 18%",
                        "-1.20 / +0.75 / +0.10",
                        "-1.1 EV",
                        "低 EV 加注",
                        "顶对加后门听牌更适合作为跟注；加注会让很多更差牌过早弃牌。",
                        "有位置时，用中等强度成牌跟注，让权益自然实现。",
                        "机器人小尺度 c-bet 过频，跟注保留它的空气牌。",
                        "BTN 有位置，KQ 在干燥 Q 高牌面具备稳定摊牌价值。",
                        new String[]{"范围权益实现", "保留诈唬", "避免低 EV 加注"}
                ),
                new Hand(
                        "SB 对 BB | 河牌摊牌价值",
                        "盲注对抗 / 河牌节点",
                        "河牌",
                        "SB",
                        "As Jh",
                        "Ah 9c 5d 2s 7c",
                        bb * 31,
                        startingStack - bb * 18,
                        "BB 河牌过牌",
                        "训练河牌纪律：区分薄价值下注和摊牌价值牌。",
                        new String[]{"过牌", "下注", "全下"},
                        new String[]{"0", String.valueOf(bb * 14), String.valueOf(bb * 67)},
                        0,
                        "随后过牌",
                        "63%",
                        "过牌 76% / 下注 24% / 全下 0%",
                        "+0.65 / -0.20 / -2.10",
                        "-0.9 EV",
                        "过度薄价值",
                        "你的牌有足够摊牌价值。下注会把中等强度成牌变成很薄的诈唬。",
                        "能经常摊牌赢、又很难被更差牌跟注的牌，优先过牌。",
                        "机器人河牌跟注偏紧，薄价值下注收益下降。",
                        "A 高成牌有摊牌价值，但缺少被更差牌跟注的目标。",
                        new String[]{"摊牌价值", "不适合诈唬", "控制底池"}
                ),
                new Hand(
                        "BTN RFI | 翻前开池",
                        "翻前范围 / 40bb",
                        "翻前",
                        "BTN",
                        "Kc 9c",
                        "",
                        bb * 1 + bb / 2,
                        startingStack,
                        "前面玩家全部弃牌",
                        "训练按钮位开池范围：用位置优势扩展可盈利组合。",
                        new String[]{"弃牌", "加注", "全下"},
                        new String[]{"0", String.valueOf(bb * 2), String.valueOf(bb * 40)},
                        1,
                        "加注 " + (bb * 2),
                        "48%",
                        "弃牌 5% / 加注 95% / 全下 0%",
                        "-0.55 / +0.62 / -1.70",
                        "-1.0 EV",
                        "过紧弃牌",
                        "按钮位对盲注有位置优势，K9s 具备可玩性和同花潜力，是稳定开池牌。",
                        "按钮位不要用过紧范围丢掉位置红利。",
                        "机器人盲注防守偏被动，按钮位可以提高开池频率。",
                        "BTN 范围最宽，K9s 位于盈利开池区间。",
                        new String[]{"位置优势", "同花连通性", "盲注压力"}
                ),
                new Hand(
                        "BB 对 CO | 翻牌防守",
                        "单加注底池 / OOP",
                        "翻牌",
                        "BB",
                        "9s 8s",
                        "Js Ts 3d",
                        bb * 14,
                        startingStack - bb * 4,
                        "CO 下注 " + (bb * 7),
                        "训练听牌强度评估：强听牌不要过度弃牌。",
                        new String[]{"弃牌", "跟注", "加注"},
                        new String[]{"0", String.valueOf(bb * 7), String.valueOf(bb * 22)},
                        2,
                        "加注 " + (bb * 22),
                        "46%",
                        "弃牌 0% / 跟注 44% / 加注 56%",
                        "-1.80 / +0.25 / +0.95",
                        "-1.6 EV",
                        "错过半诈唬",
                        "双头顺听加同花听牌有大量权益。对手持续下注范围宽时，加注能产生弃牌率并构建大底池。",
                        "强听牌在有弃牌率时优先主动进攻。",
                        "机器人面对翻牌加注继续范围偏窄，可以提高半诈唬加注。",
                        "9s8s 在 JTs3d 上是高权益听牌，适合混合加注。",
                        new String[]{"强听牌", "半诈唬", "弃牌率", "权益兑现"}
                )
        };
    }

    private LinearLayout pageShell(FrameLayout root, String titleText) {
        LinearLayout shell = vertical();
        shell.setPadding(dp(28), dp(22), dp(28), dp(22));
        root.addView(shell, frame(-1, -1));

        LinearLayout top = horizontal();
        top.setGravity(Gravity.CENTER_VERTICAL);
        shell.addView(top, linear(-1, dp(54)));

        Button back = smallButton(reviewBackToGame && trainingStarted ? "返回" : "大厅");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewBackToGame && trainingStarted) {
                    reviewBackToGame = false;
                    renderGame();
                } else {
                    showHome();
                }
            }
        });
        top.addView(back, linear(dp(100), dp(42)));

        TextView title = text(titleText, 28, TEXT, true);
        title.setGravity(Gravity.CENTER);
        top.addView(title, linear(0, -1, 1f));
        top.addView(new View(this), linear(dp(100), dp(42)));
        return shell;
    }

    private View modeCard(String title, String body, final int startIndex, final String mode) {
        LinearLayout card = vertical();
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(panelBg(PANEL));
        card.setGravity(Gravity.CENTER);
        TextView t = text(title, 22, GOLD, true);
        t.setGravity(Gravity.CENTER);
        TextView b = text(body, 14, MUTED, false);
        b.setGravity(Gravity.CENTER);
        b.setPadding(0, dp(12), 0, dp(18));
        Button start = primaryButton("开始");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTraining(true, false, mode, startIndex);
            }
        });
        card.addView(t, linear(-1, -2));
        card.addView(b, linear(-1, -2));
        card.addView(start, linear(-1, dp(58)));
        LinearLayout.LayoutParams lp = linear(0, -1, 1f);
        lp.setMargins(dp(8), dp(8), dp(8), dp(8));
        card.setLayoutParams(lp);
        return card;
    }

    private void addStudyCard(LinearLayout parent, String title, String body) {
        LinearLayout card = vertical();
        card.setPadding(dp(18), dp(14), dp(18), dp(14));
        card.setBackground(panelBg(PANEL));
        LinearLayout.LayoutParams lp = linear(-1, -2);
        lp.setMargins(0, dp(10), 0, 0);
        parent.addView(card, lp);
        card.addView(text(title, 17, GOLD, true), linear(-1, -2));
        TextView b = text(body, 14, MUTED, false);
        b.setPadding(0, dp(8), 0, 0);
        card.addView(b, linear(-1, -2));
    }

    private View statChip(String label, String value) {
        LinearLayout chip = vertical();
        chip.setGravity(Gravity.CENTER);
        chip.setPadding(dp(14), dp(10), dp(14), dp(10));
        chip.setBackground(panelBg(PANEL));
        TextView l = text(label, 11, MUTED, false);
        l.setGravity(Gravity.CENTER);
        TextView v = text(value, 14, GOLD, true);
        v.setGravity(Gravity.CENTER);
        chip.addView(l, linear(-1, -2));
        chip.addView(v, linear(-1, -2));
        LinearLayout.LayoutParams lp = linear(dp(190), dp(70));
        lp.setMargins(dp(8), 0, dp(8), 0);
        chip.setLayoutParams(lp);
        return chip;
    }

    private View homeCard(String title, String body, View.OnClickListener listener) {
        LinearLayout card = vertical();
        card.setPadding(dp(18), dp(14), dp(18), dp(14));
        card.setBackground(panelBg(PANEL));
        card.setGravity(Gravity.CENTER);
        card.setOnClickListener(listener);
        TextView t = text(title, 17, title.equals("开始训练") ? GOLD : TEXT, true);
        t.setGravity(Gravity.CENTER);
        TextView b = text(body, 12, MUTED, false);
        b.setGravity(Gravity.CENTER);
        b.setPadding(0, dp(8), 0, 0);
        t.setOnClickListener(listener);
        b.setOnClickListener(listener);
        t.setClickable(true);
        b.setClickable(true);
        card.addView(t, linear(-1, -2));
        card.addView(b, linear(-1, -2));
        LinearLayout.LayoutParams lp = linear(0, dp(122), 1f);
        lp.setMargins(dp(8), 0, dp(8), 0);
        card.setLayoutParams(lp);
        return card;
    }

    private void addCoachLine(LinearLayout parent, String label, String value, int valueColor, int maxLines) {
        TextView l = text(label, 11, GOLD, true);
        l.setPadding(0, dp(8), 0, 0);
        parent.addView(l, linear(-1, -2));
        TextView v = text(value, 13, valueColor, false);
        v.setMaxLines(maxLines);
        parent.addView(v, linear(-1, -2));
    }

    private Button actionButton(String title, String amount) {
        Button button = new Button(this);
        button.setText(title + "\n" + amount);
        button.setTextSize(16);
        button.setTextColor(TEXT);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setBackground(panelBg(PANEL_2));
        return button;
    }

    private Button secondaryButton(String title, String detail) {
        Button button = new Button(this);
        button.setText(title + "\n" + detail);
        button.setTextSize(16);
        button.setTextColor(TEXT);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setBackground(panelBg(PANEL_2));
        return button;
    }

    private Button primaryButton(String title) {
        Button button = new Button(this);
        button.setText(title);
        button.setTextSize(18);
        button.setTextColor(BG);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setAllCaps(false);
        button.setBackground(goldBg());
        return button;
    }

    private Button smallButton(String title) {
        Button button = new Button(this);
        button.setText(title);
        button.setTextSize(13);
        button.setTextColor(TEXT);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        button.setAllCaps(false);
        button.setBackground(panelBg(PANEL_2));
        return button;
    }

    private View legendChip(String label, int color) {
        TextView v = text("  " + label + "  ", 12, TEXT, false);
        v.setGravity(Gravity.CENTER);
        v.setBackground(panelBg(color));
        LinearLayout.LayoutParams lp = linear(0, -1, 1f);
        lp.setMargins(dp(4), 0, dp(4), 0);
        v.setLayoutParams(lp);
        return v;
    }

    private TextView text(String value, int sp, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setIncludeFontPadding(false);
        if (bold) {
            t.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return t;
    }

    private FrameLayout baseRoot() {
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(BG);
        return root;
    }

    private LinearLayout vertical() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    private LinearLayout horizontal() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

    private FrameLayout.LayoutParams frame(int w, int h) {
        return new FrameLayout.LayoutParams(w, h);
    }

    private LinearLayout.LayoutParams linear(int w, int h) {
        return new LinearLayout.LayoutParams(w, h);
    }

    private LinearLayout.LayoutParams linear(int w, int h, float weight) {
        return new LinearLayout.LayoutParams(w, h, weight);
    }

    private android.graphics.drawable.GradientDrawable panelBg(int color) {
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(dp(12));
        bg.setStroke(dp(1), Color.rgb(53, 70, 60));
        return bg;
    }

    private android.graphics.drawable.GradientDrawable goldBg() {
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setOrientation(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM);
        bg.setColors(new int[]{Color.rgb(236, 194, 85), GOLD_DARK});
        bg.setCornerRadius(dp(12));
        return bg;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }

    private int currentAccuracy() {
        if (history.isEmpty()) {
            return 0;
        }
        int good = 0;
        for (ActionRecord record : history) {
            if (record.ev.startsWith("+")) {
                good++;
            }
        }
        return Math.round((good * 100f) / history.size());
    }

    private String totalEvLabel() {
        if (history.isEmpty()) {
            return "0 EV";
        }
        float total = 0f;
        for (ActionRecord record : history) {
            try {
                total += Float.parseFloat(record.ev.replace(" EV", ""));
            } catch (Exception ignored) {
            }
        }
        return String.format(Locale.US, "%.1f EV", total);
    }

    private String mainLeak() {
        if (history.isEmpty()) {
            return "暂无";
        }
        String bestLeak = history.get(0).errorType;
        int bestCount = 0;
        for (ActionRecord outer : history) {
            int count = 0;
            for (ActionRecord inner : history) {
                if (outer.errorType.equals(inner.errorType)) {
                    count++;
                }
            }
            if (count > bestCount) {
                bestCount = count;
                bestLeak = outer.errorType;
            }
        }
        return bestLeak;
    }

    private String streetSummary(String street) {
        int total = 0;
        int good = 0;
        for (ActionRecord record : history) {
            if (street.equals(record.street)) {
                total++;
                if (record.ev.startsWith("+")) {
                    good++;
                }
            }
        }
        if (total == 0) {
            return street + ": 暂无";
        }
        return street + ": " + Math.round((good * 100f) / total) + "%";
    }

    private String leakBreakdown() {
        if (history.isEmpty()) {
            return "暂无错误样本。";
        }
        StringBuilder builder = new StringBuilder();
        for (ActionRecord outer : history) {
            if (builder.toString().contains(outer.errorType + " ")) {
                continue;
            }
            int count = 0;
            for (ActionRecord inner : history) {
                if (outer.errorType.equals(inner.errorType)) {
                    count++;
                }
            }
            if (builder.length() > 0) {
                builder.append("  ");
            }
            builder.append(outer.errorType).append(" ").append(count).append(" 次");
        }
        return builder.toString();
    }

    private String rangeSummary(int pos) {
        String[] summaries = {
                "UTG 范围紧，优先高牌强踢脚、对子和高质量同花连张。",
                "LJ 比 UTG 略宽，但仍需要控制弱踢脚和被压制组合。",
                "HJ 开始加入更多同花 A、Broadway 和中等对子。",
                "CO 利用后位优势扩大偷盲范围，增加 suited connector。",
                "BTN 范围最宽，位置红利允许大量边缘盈利组合。",
                "SB 需要考虑无位置劣势，开池更偏极化。",
                "BB 以防守为主，跟注范围宽，但 3-bet 需要阻断牌和权益。"
        };
        return summaries[Math.max(0, Math.min(pos, summaries.length - 1))];
    }

    private static final class Hand {
        final String title;
        final String mode;
        final String street;
        final String heroPosition;
        final String heroCards;
        final String board;
        final int pot;
        final int heroStack;
        final String villainAction;
        final String goal;
        final String[] actions;
        final String[] amounts;
        final int recommendedIndex;
        final String recommendation;
        final String winRate;
        final String mix;
        final String evs;
        final String evLoss;
        final String errorType;
        final String advice;
        final String drill;
        final String botLine;
        final String rangeNote;
        final String[] factors;

        Hand(String title, String mode, String street, String heroPosition, String heroCards, String board, int pot,
             int heroStack, String villainAction, String goal, String[] actions, String[] amounts, int recommendedIndex,
             String recommendation, String winRate, String mix, String evs, String evLoss, String errorType,
             String advice, String drill, String botLine, String rangeNote, String[] factors) {
            this.title = title;
            this.mode = mode;
            this.street = street;
            this.heroPosition = heroPosition;
            this.heroCards = heroCards;
            this.board = board;
            this.pot = pot;
            this.heroStack = heroStack;
            this.villainAction = villainAction;
            this.goal = goal;
            this.actions = actions;
            this.amounts = amounts;
            this.recommendedIndex = recommendedIndex;
            this.recommendation = recommendation;
            this.winRate = winRate;
            this.mix = mix;
            this.evs = evs;
            this.evLoss = evLoss;
            this.errorType = errorType;
            this.advice = advice;
            this.drill = drill;
            this.botLine = botLine;
            this.rangeNote = rangeNote;
            this.factors = factors;
        }

        String mixSummary() {
            return mix;
        }

        String evSummary() {
            return evs;
        }

        int recommendedColor() {
            String action = actions[recommendedIndex].toLowerCase(Locale.US);
            if (action.contains("raise") || action.contains("bet") || action.contains("加注")
                    || action.contains("下注") || action.contains("全下")) {
                return GOLD;
            }
            if (action.contains("fold") || action.contains("弃牌")) {
                return RED;
            }
            return GREEN;
        }
    }

    private static final class ActionRecord {
        final String title;
        final String street;
        final String mode;
        final String chosen;
        final String best;
        final String ev;
        final String errorType;
        final String reason;

        ActionRecord(String title, String street, String mode, String chosen, String best, String ev, String errorType, String reason) {
            this.title = title;
            this.street = street;
            this.mode = mode;
            this.chosen = chosen;
            this.best = best;
            this.ev = ev;
            this.errorType = errorType;
            this.reason = reason;
        }
    }

    private final class RangeGridView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final int position;
        private final String[] ranks = {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};

        RangeGridView(Activity context, int position) {
            super(context);
            this.position = position;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int size = Math.min(getWidth(), getHeight());
            float cell = size / 13f;
            float leftPad = (getWidth() - size) / 2f;
            float topPad = (getHeight() - size) / 2f;
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(Math.max(10, cell * 0.28f));
            for (int r = 0; r < 13; r++) {
                for (int c = 0; c < 13; c++) {
                    float x = leftPad + c * cell;
                    float y = topPad + r * cell;
                    int strength = rangeStrength(r, c, position);
                    paint.setColor(rangeColor(strength));
                    canvas.drawRect(x + 1, y + 1, x + cell - 1, y + cell - 1, paint);
                    paint.setColor(TEXT);
                    String label;
                    if (r == c) {
                        label = ranks[r] + ranks[c];
                    } else if (r < c) {
                        label = ranks[r] + ranks[c] + "s";
                    } else {
                        label = ranks[c] + ranks[r] + "o";
                    }
                    canvas.drawText(label, x + cell / 2f, y + cell * 0.58f, paint);
                }
            }
        }

        private int rangeStrength(int r, int c, int pos) {
            int pairBonus = r == c ? 6 : 0;
            int highCard = 24 - r - c;
            int suitedBonus = r < c ? 3 : 0;
            int connectorBonus = Math.abs(r - c) <= 2 ? 3 : 0;
            int positionBonus = pos * 2;
            return highCard + pairBonus + suitedBonus + connectorBonus + positionBonus;
        }

        private int rangeColor(int strength) {
            if (strength >= 28) {
                return GOLD_DARK;
            }
            if (strength >= 23) {
                return BLUE;
            }
            if (strength >= 18) {
                return Color.rgb(27, 118, 78);
            }
            return Color.rgb(34, 45, 40);
        }
    }

    private final class PokerTableView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Hand hand;
        private final boolean awaiting;
        private final int selected;

        PokerTableView(Activity context, Hand hand, boolean awaiting, int selected) {
            super(context);
            this.hand = hand;
            this.awaiting = awaiting;
            this.selected = selected;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int w = getWidth();
            int h = getHeight();
            RectF table = new RectF(dp(36), dp(38), w - dp(36), h - dp(38));
            paint.setShader(new LinearGradient(0, table.top, 0, table.bottom,
                    Color.rgb(20, 103, 68), Color.rgb(5, 70, 50), Shader.TileMode.CLAMP));
            canvas.drawOval(table, paint);
            paint.setShader(null);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(8));
            paint.setColor(Color.rgb(9, 17, 13));
            canvas.drawOval(table, paint);
            paint.setStrokeWidth(dp(2));
            paint.setColor(Color.argb(90, 216, 169, 65));
            canvas.drawOval(new RectF(table.left + dp(18), table.top + dp(18), table.right - dp(18), table.bottom - dp(18)), paint);
            paint.setStyle(Paint.Style.FILL);

            drawPot(canvas, w / 2f, h * 0.26f);
            if (hand.board.length() > 0) {
                drawCards(canvas, hand.board.split(" "), w / 2f, h * 0.42f, false);
            }

            String[] names = {"小林", "丹尼", "阿历", "艾米", "珍妮"};
            String[] positions = {"BB", "UTG", "CO", "BTN", "SB"};
            float[][] seat = {
                    {0.21f, 0.30f},
                    {0.50f, 0.15f},
                    {0.79f, 0.30f},
                    {0.78f, 0.74f},
                    {0.22f, 0.74f}
            };
            for (int i = 0; i < names.length; i++) {
                drawPlayer(canvas, names[i], positions[i], String.valueOf(hand.heroStack - (i * 320)), w * seat[i][0], h * seat[i][1], false);
            }
            drawHero(canvas, w / 2f, h * 0.75f);

            if (!awaiting && selected >= 0) {
                drawDecisionBadge(canvas, w / 2f, h * 0.90f);
            }
        }

        private void drawPot(Canvas canvas, float cx, float cy) {
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.argb(210, 0, 0, 0));
            canvas.drawRoundRect(new RectF(cx - dp(100), cy - dp(36), cx + dp(100), cy + dp(36)), dp(18), dp(18), paint);
            paint.setColor(MUTED);
            paint.setTextSize(dp(12));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText("总底池", cx, cy - dp(8), paint);
            paint.setColor(GOLD);
            paint.setTextSize(dp(24));
            canvas.drawText(String.valueOf(hand.pot), cx, cy + dp(22), paint);
            paint.setTypeface(Typeface.DEFAULT);
        }

        private void drawHero(Canvas canvas, float cx, float cy) {
            drawPlayer(canvas, "你", hand.heroPosition, String.valueOf(hand.heroStack), cx, cy, true);
            drawCards(canvas, hand.heroCards.split(" "), cx, cy - dp(32), true);
        }

        private void drawPlayer(Canvas canvas, String name, String pos, String stack, float cx, float cy, boolean hero) {
            paint.setColor(hero ? GOLD : Color.rgb(42, 55, 48));
            canvas.drawCircle(cx, cy, dp(hero ? 26 : 24), paint);
            paint.setColor(hero ? Color.rgb(24, 34, 28) : Color.rgb(170, 188, 174));
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(dp(hero ? 16 : 13));
            canvas.drawText(name.substring(0, 1), cx, cy + dp(6), paint);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setColor(TEXT);
            paint.setTextSize(dp(12));
            canvas.drawText(name, cx, cy + dp(40), paint);
            paint.setColor(MUTED);
            paint.setTextSize(dp(10));
            canvas.drawText(pos + "  " + stack, cx, cy + dp(54), paint);
        }

        private void drawDecisionBadge(Canvas canvas, float cx, float cy) {
            boolean good = selected == hand.recommendedIndex;
            paint.setColor(good ? Color.rgb(20, 92, 61) : Color.rgb(100, 38, 39));
            canvas.drawRoundRect(new RectF(cx - dp(150), cy - dp(28), cx + dp(150), cy + dp(28)), dp(18), dp(18), paint);
            paint.setColor(TEXT);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(dp(14));
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(good ? "符合推荐线路" : "建议复盘此手", cx, cy + dp(5), paint);
            paint.setTypeface(Typeface.DEFAULT);
        }

        private void drawCards(Canvas canvas, String[] cards, float cx, float cy, boolean heroCards) {
            float cardW = dp(heroCards ? 36 : 32);
            float cardH = dp(heroCards ? 50 : 46);
            float gap = dp(6);
            float start = cx - ((cards.length * cardW + (cards.length - 1) * gap) / 2f);
            for (int i = 0; i < cards.length; i++) {
                float left = start + i * (cardW + gap);
                RectF r = new RectF(left, cy - cardH / 2f, left + cardW, cy + cardH / 2f);
                paint.setColor(Color.rgb(244, 246, 239));
                canvas.drawRoundRect(r, dp(6), dp(6), paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(dp(1));
                paint.setColor(Color.rgb(202, 207, 199));
                canvas.drawRoundRect(r, dp(6), dp(6), paint);
                paint.setStyle(Paint.Style.FILL);
                String card = cards[i].trim();
                boolean redSuit = card.endsWith("h") || card.endsWith("d");
                paint.setColor(redSuit ? Color.rgb(194, 35, 45) : Color.rgb(27, 31, 30));
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setTextSize(dp(heroCards ? 14 : 13));
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(card.length() > 0 ? card.substring(0, card.length() - 1).toUpperCase(Locale.US) : "?",
                        r.centerX(), r.centerY() - dp(2), paint);
                paint.setTextSize(dp(8));
                canvas.drawText(card.length() > 0 ? card.substring(card.length() - 1).toUpperCase(Locale.US) : "",
                        r.centerX(), r.centerY() + dp(16), paint);
                paint.setTypeface(Typeface.DEFAULT);
            }
        }
    }
}
