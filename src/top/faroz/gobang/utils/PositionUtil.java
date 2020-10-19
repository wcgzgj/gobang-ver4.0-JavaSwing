package top.faroz.gobang.utils;

import top.faroz.gobang.domain.Position;
import top.faroz.gobang.gui.panel.ChessPanel;

/**
 * @ClassName PositionUtil
 * @Description Position类型的转换
 * @Author FARO_Z
 * @Date 2020/9/27 11:48 上午
 * @Version 1.0
 **/
public class PositionUtil {
    /**
     * 矩阵坐标转换为绘图坐标
     * @param p
     * @return
     */
    //将行列坐标 转换为点阵绘图坐标
    public static Position padToDraw(Position p) {
        ChessPanel instance = ChessPanel.getInstance();
        int gap=instance.gap;
        int size=instance.size;

        int drawX=p.y;
        int drawY=p.x;

        return new Position(gap+drawX*size+size,gap+drawY*size+size);
    }

    /**
     * 绘图坐标边转换为矩阵坐标
      * @param p
     * @return
     */
    //这会将点阵坐标转换为行列坐标
    public static Position drawToPad(Position p) {
        ChessPanel instance = ChessPanel.getInstance();
        int gap=instance.gap;
        int size=instance.size;

        int row=p.y;
        int col=p.x;

        return new Position((row-gap)/size-1,(col-gap)/size-1);
    }
}
