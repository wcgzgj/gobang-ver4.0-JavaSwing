package top.faroz.gobang.gui.listener;

import top.faroz.gobang.chessbot.Ai;
import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.domain.Position;
import top.faroz.gobang.gui.panel.ButtonPanel;
import top.faroz.gobang.gui.panel.ChessPanel;
import top.faroz.gobang.gui.panel.MainPanel;
import top.faroz.gobang.utils.ChessUtil;
import top.faroz.gobang.utils.PositionUtil;
import top.faroz.gobang.utils.ShowResultUtil;

import java.awt.event.*;
import java.util.List;

/**
 * @ClassName ChessPadListener
 * @Description 棋盘监听器，
 * 用来监听用户的点击动作，
 * 并对用户的点击进行相应
 * @Author FARO_Z
 * @Date 2020/9/27 10:43 上午
 * @Version 1.0
 **/
public class ChessPanelListener implements MouseListener {


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * 当鼠标松开的时候，
     * 先是改变ChessPad中的 pad的值
     * 然后，再调用ChessPanel中的paint方法，重新绘制
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        ChessPad chessPad = ChessPad.getInstance();
        MainPanel mainPanel = MainPanel.getInstance();
        ButtonPanel buttonPanel = ButtonPanel.getInstance();


        //获取 绘制的棋盘上 每个交点的坐标  用来判读后面鼠标点击的点是不是在范围内
        List<Position> list = ChessPanel.getInstance().getAllPositions();
        int x = e.getX();
        int y = e.getY();
        //每颗棋子的半径
        int chessBorder=ChessPanel.getInstance().chessBorder;
        Position p=null;
        for (Position position : list) {
            /**
             * 这里既要判断玩家点击的范围是否在可落子的范围内
             * 也要判断该位置之前有没有棋子
             */
            if ( (x<=position.x +chessBorder && x>=position.x -chessBorder)//判断落子范围
                    && (y<=position.y +chessBorder && y>=position.y -chessBorder)//判断落子范围
                    && chessPad.isEmptyPosition(PositionUtil.drawToPad(position))//保证落子的位置没有其他棋子
                    ) {
                p=position;
                if (chessPad.getMode().equals("人机对战") && chessPad.isBlackTurn()) {//如果是人机对战，必须保证现在是玩家落子
                    p=null;
                }
            }
        }

        //将棋子置于pad中
        //并重新绘制棋盘
        if (null!=p) {
            //注意！！！：putChess()会自动调用 changeTurn，可能会对下面的判断产生影响
            boolean result=ChessPad.getInstance().putChess(PositionUtil.drawToPad(p));
            //立即绘制，不会产生延迟
            ChessPanel.getInstance().paintImmediately(0,0,800,800);
            buttonPanel.resetReminder();
        }

        //落子后，判断是否平局
        ShowResultUtil.drawCheckAndShowResult();

        //p!=null表示玩家的落子成功
        if (null!=p ) {
            /**
             * 人机对战的前提下
             * 虽然白方已经落完子了 （落完子后会自动调用changeTurn函数，就是说现在是blackTurn）
             * 但是现在还是在判断玩家，所以需要暂时将 turn 再换回white
             * 虽然没什么用，但是这体现设计严谨
             */
            if ("人机对战".equals(chessPad.getMode())) {
                chessPad.setWhiteFirst();
            }
            /**
             * 在 人机 还是 人人 判断之前，先获取棋子链条的List
             * 这样可以复用，减少性能损失
             */
            List<String> allStringList = ChessUtil.getAllStringList(PositionUtil.drawToPad(p));

            if ("人人对战".equals(chessPad.getMode())) {
                ShowResultUtil.PVPResult(allStringList);
            }

            if ("人机对战".equals(chessPad.getMode())) {
                //判断玩家是否获胜
                if (!ShowResultUtil.PVEResult(allStringList)) {//如果玩家没有获胜
                    //还要让之前被转换的turn转换回去
                    //让Ai落子
                    chessPad.setBlackFirst();
                    buttonPanel.resetReminder();
                    Ai.AiPlay();
                }

            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
