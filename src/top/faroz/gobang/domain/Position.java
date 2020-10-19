package top.faroz.gobang.domain;

/**
 * 防止位置
 *
 *
 * 注意！！！：绘图用的坐标是点阵坐标
 *    而棋盘pad 用的坐标是 行列坐标
 *    要相互转换的时候，一定要用PositionUtil中的 drawToPad  或者  padToDraw
 */
public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "当前Position的数据是 : x->"+ x +" , "+"y->"+ y;
    }
}
