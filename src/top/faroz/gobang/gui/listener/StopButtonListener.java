package top.faroz.gobang.gui.listener;

import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.gui.panel.ButtonPanel;
import top.faroz.gobang.gui.panel.MainPanel;
import top.faroz.gobang.gui.panel.PausePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName StopButtonListener
 * @Description TODO
 * @Author FARO_Z
 * @Date 2020/9/27 1:13 下午
 * @Version 1.0
 **/
public class StopButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ChessPad chessPad = ChessPad.getInstance();
        PausePanel pausePanel = PausePanel.getInstance();
        MainPanel mainPanel = MainPanel.getInstance();
        ButtonPanel buttonPanel = ButtonPanel.getInstance();

        buttonPanel.stop.setEnabled(false);
        buttonPanel.start.setEnabled(true);
        buttonPanel.modes.setEnabled(true);
        buttonPanel.regrate.setEnabled(false);
        buttonPanel.tips.setEnabled(false);

        buttonPanel.modes.setSelectedItem("人机对战");
        buttonPanel.sequence.setEnabled(true);

        //当点击了停止当前游戏，
        // 说明要更换游戏模式或者要开新局
        //此时必须要将棋盘清空，以备后用
        chessPad.clear();

        mainPanel.setLeftComponent(pausePanel);
        mainPanel.setDividerLocation(800);
        mainPanel.repaint();

    }
}
