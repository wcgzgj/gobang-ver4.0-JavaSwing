package top.faroz.gobang.gui.panel;

import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.domain.Position;
import top.faroz.gobang.gui.listener.ChessPanelListener;
import top.faroz.gobang.utils.PositionUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChessPanel
 * @Description 主界面左边，用于存放棋盘的Panel
 * @Author FARO_Z
 * @Date 2020/9/26 4:52 下午
 * @Version 1.0
 **/



public class ChessPanel extends JPanel {
    public static final int gap=80;
    public static final int size=40;
    public static final int rows=17;
    public static final int cols=17;
    public static final int chessBorder=15;//棋子的半径是15
    private static ImageIcon image= new ImageIcon("src/img/padBackGround.png");

    public static ChessPanel instance=new ChessPanel();

    public static ChessPanel getInstance() {
        return instance;
    }
    private ChessPanel() {
        //为界面添加鼠标监听器
        //这样就能响应并绘制棋子
        this.addMouseListener(new ChessPanelListener());
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawChessPad(g);
        drawChess(g);
    }

    //棋盘的大小是800*800
    //最后要绘制的棋盘，要是15*15个交点的
    public void drawChessPad(Graphics g) {
        Graphics2D g2=(Graphics2D)g;
        g2.setBackground(Color.CYAN);
        image.paintIcon(this,g,0,0);
        for (int i = 0; i < rows; i++) {
            if (i==0 || i==rows-1) {
                g2.setStroke(new BasicStroke(3.0f));
            } else {
                g2.setStroke(new BasicStroke(1.0f));
            }
            g2.drawLine(gap,gap+i*size,gap+size*16,gap+i*size);
        }

        for (int i = 0; i < cols; i++) {
            if (i==0 || i==rows-1) {
                g2.setStroke(new BasicStroke(3.0f));
            } else {
                g2.setStroke(new BasicStroke(1.0f));
            }
            g2.drawLine(gap+i*size,gap,gap+i*size,gap+size*16);
        }

        //绘制棋盘上的四个点
        Point p1 = new Point(gap + size * 3, gap + size * 3);
        Point p2 = new Point(gap + size * 13, gap + size * 3);
        Point p3 = new Point(gap + size * 3, gap + size * 13);
        Point p4 = new Point(gap + size * 13, gap + size * 13);
        g.fillOval(p1.x-3,p1.y-3,6,6);
        g.fillOval(p2.x-3,p2.y-3,6,6);
        g.fillOval(p3.x-3,p3.y-3,6,6);
        g.fillOval(p4.x-3,p4.y-3,6,6);

    }

    public void drawChess(Graphics g) {
        ChessPad p = ChessPad.getInstance();
        Graphics2D g2=(Graphics2D)g;
        List<Position> blackList = p.getBlackPositionList();
        List<Position> whiteList = p.getWhitePositionList();
        for (Position pos : blackList) {
            // System.out.println("pos中的坐标是:"+pos.toString());
            Position drawPositipn = PositionUtil.padToDraw(pos);
            g2.setColor(Color.BLACK);
            int x=drawPositipn.x-chessBorder;
            int y=drawPositipn.y-chessBorder;

            g2.fillOval(x,y,chessBorder*2,chessBorder*2);
        }

        for (Position pos : whiteList) {
            Position drawPositipn = PositionUtil.padToDraw(pos);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2.f));
            int x=drawPositipn.x-chessBorder;
            int y=drawPositipn.y-chessBorder;
            g2.fillOval(x,y,chessBorder*2,chessBorder*2);
            g2.setColor(Color.BLACK);
            g2.drawOval(x,y,chessBorder*2,chessBorder*2);
        }
    }


    /**
     * 返回绘图板上所有交点的位置
     * 通过这个数据，方便我进行绘图
     * @return
     */
    public List<Position> getAllPositions() {
        List<Position> list = new ArrayList<>();
        for (int i = 1; i <=15 ; i++) {
            for (int j = 1; j <=15 ; j++) {
                list.add(new Position(gap+i*size,gap+j*size));
            }
        }
        return list;
    }
}
