package top.faroz.gobang.gui.listener;

import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.gui.panel.ButtonPanel;
import top.faroz.gobang.gui.panel.ChessPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName RegrateButtonListener
 * @Description 悔棋的监听器
 * "人人对战"  悔棋的实现方式  是将每次落下的子存入一个栈里面
 * 如果选择悔棋，就出栈  然后将该点颜色置空   再重绘棋盘
 * @Author FARO_Z
 * @Date 2020/9/28 11:12 下午
 * @Version 1.0
 **/
public class RegrateButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ChessPad chessPad = ChessPad.getInstance();
        ChessPanel chessPanel = ChessPanel.getInstance();
        ButtonPanel buttonPanel = ButtonPanel.getInstance();


        // //如果悔棋成功，则对棋盘进行重绘
        if (chessPad.regrate()) {
            chessPanel.repaint();
            buttonPanel.resetReminder();
        }


    }
}
