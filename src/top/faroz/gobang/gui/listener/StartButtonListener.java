package top.faroz.gobang.gui.listener;

import top.faroz.gobang.chessbot.Ai;
import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.gui.panel.ButtonPanel;
import top.faroz.gobang.gui.panel.ChessPanel;
import top.faroz.gobang.gui.panel.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName StartButtonListener
 * @Description 开始按钮监听器，
 * 每次点击开始，都要重置  pad , value
 * 不然会影响下一局游戏
 * @Author FARO_Z
 * @Date 2020/9/27 1:51 下午
 * @Version 1.0
 **/
public class StartButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        MainPanel mainPanel = MainPanel.getInstance();
        ChessPad chessPad = ChessPad.getInstance();
        ChessPanel chessPanel = ChessPanel.getInstance();

        buttonPanel.stop.setEnabled(true);
        buttonPanel.start.setEnabled(false);
        buttonPanel.modes.setEnabled(false);

        //每次点击开始后，都要保证上一局游戏的数据清零，不会影响下一局游戏
        chessPad.clear();
        String mode = (String) buttonPanel.modes.getSelectedItem();
        //不要用else判断，因为不知道还有什么错误...
        if ("人人对战".equals(mode)) {
            chessPad.setBlackFirst();
            buttonPanel.resetReminder();
            chessPad.setPVPMode();
            buttonPanel.regrate.setEnabled(true);
            buttonPanel.tips.setEnabled(false);
        }
        if ("人机对战".equals(mode)) {
            //
            // buttonPanel.resetReminder();
            chessPad.setPVEMode();
            buttonPanel.regrate.setEnabled(true);
            buttonPanel.tips.setEnabled(true);
            buttonPanel.sequence.setEnabled(false);

            //如果玩家选择做后手
            if ("后手".equals(buttonPanel.sequence.getSelectedItem())) {
                chessPad.setBlackFirst();
                buttonPanel.resetReminder();
                Ai.AiPlay();
                buttonPanel.resetReminder();
            } else if ("先手".equals(buttonPanel.sequence.getSelectedItem())){ //如果玩家选择做先手
                chessPad.setWhiteFirst();
                buttonPanel.resetReminder();
            }


        }

        mainPanel.setLeftComponent(chessPanel);
        mainPanel.setDividerLocation(800);
        mainPanel.repaint();

    }
}
