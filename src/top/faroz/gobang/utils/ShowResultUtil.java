package top.faroz.gobang.utils;

import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.domain.Position;
import top.faroz.gobang.gui.frame.MainFrame;
import top.faroz.gobang.gui.panel.ButtonPanel;
import top.faroz.gobang.gui.panel.MainPanel;
import top.faroz.gobang.gui.panel.PausePanel;

import javax.swing.*;
import java.util.List;

/**
 * @ClassName ShowResultUtils
 * @Description 用于展示游戏结果
 * 将游戏结果的展示与监听器分离，方便代码的调试
 * @Author FARO_Z
 * @Date 2020/9/28 4:29 下午
 * @Version 1.0
 **/
public class ShowResultUtil {

    //用于展示PVP模式下的游戏结果
    public static void PVPResult(List<String> allStringList) {
        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        MainPanel mainPanel = MainPanel.getInstance();
        //这里要将绘图坐标转换为棋盘坐标
        for (String s : allStringList) {
            /**
             *   ***** 表示五个黑子连成一串
             *   ooooo 表示五个白子连成一串
             */
            if ("*****".equals(s)) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(),"黑方获胜","游戏结束",JOptionPane.INFORMATION_MESSAGE);

                buttonPanel.stop.setEnabled(false);
                buttonPanel.start.setEnabled(true);
                buttonPanel.modes.setEnabled(true);
                buttonPanel.regrate.setEnabled(false);
                buttonPanel.tips.setEnabled(false);

                mainPanel.setLeftComponent(PausePanel.getInstance());
                mainPanel.setDividerLocation(800);
                mainPanel.repaint();
            } else if("ooooo".equals(s)) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(),"白方获胜","游戏结束",JOptionPane.INFORMATION_MESSAGE);

                buttonPanel.stop.setEnabled(false);
                buttonPanel.start.setEnabled(true);
                buttonPanel.modes.setEnabled(true);
                buttonPanel.regrate.setEnabled(false);
                buttonPanel.tips.setEnabled(false);

                buttonPanel.modes.setSelectedItem("人机对战");
                buttonPanel.sequence.setEnabled(true);

                mainPanel.setLeftComponent(PausePanel.getInstance());
                mainPanel.setDividerLocation(800);
                mainPanel.repaint();
            }
        }
    }

    public static boolean PVEResult(List<String> allStringList) {
        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        MainPanel mainPanel = MainPanel.getInstance();

        for (String s : allStringList) {
            //玩家只有可能是白子，所以只要白子的判断
            if ("ooooo".equals(s)) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "玩家获胜", "游戏结束", JOptionPane.INFORMATION_MESSAGE);

                buttonPanel.stop.setEnabled(false);
                buttonPanel.start.setEnabled(true);
                buttonPanel.modes.setEnabled(true);
                buttonPanel.regrate.setEnabled(false);
                buttonPanel.tips.setEnabled(false);

                buttonPanel.modes.setSelectedItem("人机对战");
                buttonPanel.sequence.setEnabled(true);

                mainPanel.setLeftComponent(PausePanel.getInstance());
                mainPanel.setDividerLocation(800);
                mainPanel.repaint();

                return true;
            }
        }
        return false;
    }

    public static void AiResult(Position p) {
        MainPanel mainPanel = MainPanel.getInstance();
        ButtonPanel buttonPanel = ButtonPanel.getInstance();

        List<String> allStringList = ChessUtil.getAllStringList(p);
        for (String s : allStringList) {
            if ("*****".equals(s)) {
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Ai获胜", "游戏结束", JOptionPane.INFORMATION_MESSAGE);

                buttonPanel.stop.setEnabled(false);
                buttonPanel.start.setEnabled(true);
                buttonPanel.modes.setEnabled(true);
                buttonPanel.regrate.setEnabled(false);
                buttonPanel.tips.setEnabled(false);

                buttonPanel.modes.setSelectedItem("人机对战");
                buttonPanel.sequence.setEnabled(true);

                mainPanel.setLeftComponent(PausePanel.getInstance());
                mainPanel.setDividerLocation(800);
                mainPanel.repaint();
            }
        }
    }

    //检查是否是平局
    public static void drawCheckAndShowResult() {
        ChessPad chessPad = ChessPad.getInstance();
        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        MainPanel mainPanel = MainPanel.getInstance();
        if (chessPad.isFullPad()) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "平局", "游戏结束", JOptionPane.INFORMATION_MESSAGE);

            buttonPanel.stop.setEnabled(false);
            buttonPanel.start.setEnabled(true);
            buttonPanel.modes.setEnabled(true);
            buttonPanel.regrate.setEnabled(false);
            buttonPanel.tips.setEnabled(false);

            buttonPanel.modes.setSelectedItem("人机对战");
            buttonPanel.sequence.setEnabled(true);

            mainPanel.setLeftComponent(PausePanel.getInstance());
            mainPanel.setDividerLocation(800);
            mainPanel.repaint();
        }
    }
}
