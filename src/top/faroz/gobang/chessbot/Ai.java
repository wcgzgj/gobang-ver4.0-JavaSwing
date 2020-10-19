package top.faroz.gobang.chessbot;

import top.faroz.gobang.domain.ChessColor;
import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.domain.Position;
import top.faroz.gobang.gui.panel.ButtonPanel;
import top.faroz.gobang.gui.panel.ChessPanel;
import top.faroz.gobang.utils.ChessUtil;
import top.faroz.gobang.utils.ChessValueUtil;
import top.faroz.gobang.utils.ShowResultUtil;

import java.sql.ResultSet;
import java.util.List;

/**
 * @ClassName AiPlay
 * @Description TODO
 * @Author FARO_Z
 * @Date 2020/9/27 2:15 下午
 * @Version 1.0
 **/
public class Ai {

    /**
     * 外部调用Ai函数，进行下子
     * @return
     */
    public static boolean AiPlay() {
        ChessPanel chessPanel=ChessPanel.getInstance();
        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        //Ai落子失败，直接返回
        Position AiChess = AiPutChess();
        if (AiChess==null) return false;

        //落子成功，重绘棋盘
        chessPanel.repaint();
        buttonPanel.resetReminder();

        //判断Ai是否获胜
        ShowResultUtil.AiResult(AiChess);

        //判断是否平局
        ShowResultUtil.drawCheckAndShowResult();
        return true;
    }

    private static Position AiPutChess(){
        ChessPad chessPad=ChessPad.getInstance();
        ChessColor[][] pad = chessPad.getPad();
        int size = chessPad.getSize();

        //只有在黑方可以下子的时候，Ai才可以行动
        if (!chessPad.isBlackTurn()) return null;

        //如果当前棋盘上没有任何子,ai就把子放在棋盘中央
        if (chessPad.isEmptyPad() && chessPad.isBlackTurn()) {
            chessPad.putChess(new Position(7,7));
            return new Position(7,7);
        }

        //将上一回给空位赋的值全部清零，防止对这次判断有影响
        chessPad.clearEmptyPositionValue();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Position p = new Position(row, col);
                if (chessPad.isEmptyPosition(p)) {
                    List<String> allStringList = ChessUtil.getAllStringList(p);

                    ChessValueUtil.givePositionValue(p,allStringList);
                }
            }
        }
        //获得权值最大空位
        Position maxValuePosition = ChessValueUtil.getMaxValuePosition();
        //空位上落子
        chessPad.putChess(maxValuePosition);
        return maxValuePosition;
    }
}
