package top.faroz.gobang.domain;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName ChessPad
 * @Description 使用单例模式，维护两个二维数组
 * 一个存储的是棋子的颜色，一个是棋子的权值
 * 通过这种方式，能够使得棋盘易于维护
 * 也不用再额外创造一个类Chess了
 * @Author FARO_Z
 * @Date 2020/9/26 3:50 下午
 * @Version 1.0
 **/
public class ChessPad {
    /**
     * 存储棋子的二维数组
     * 内部使用的值是枚举类中的颜色
     * 初始化的时候，全部设置为blank(空)
     */
    private static ChessColor[][] pad=new ChessColor[15][15];

    /**
     * 存储每个位置的权值
     * 将权值和棋子分离开来，便于维护
     */
    private static long [][]value=new long[15][15];

    private int size=15;
    private static ChessPad instance=new ChessPad();

    /**
     * 标识当前应该是黑方落子，还是白方落子
     */
    private static boolean BlackTurn=true;

    private static boolean AiMode=true;

    //存放下入的棋子
    private static Stack<Position> stack = new Stack();

    public static ChessPad getInstance() {
        return instance;
    }

    //构造函数
    private ChessPad() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pad[i][j]=ChessColor.Blank;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                value[i][j]=-1;
            }
        }
    }


    //传入的必须是行列坐标
    public boolean putBlack(Position p) {
        if (!inBoundy(p)) return false;
        if (!isEmptyPosition(p)) return false;
        if(!isBlackTurn()) return false;

        pad[p.x][p.y]=ChessColor.Black;
        //不管是不是人机对决，都将放置过棋子的位置，权值设为-1
        value[p.x][p.y]=-1;

        this.changeTurn();
        return true;
    }

    //传入的必须是行列坐标
    public boolean putWhite(Position p) {
        if (!inBoundy(p)) return false;
        if (!isEmptyPosition(p)) return false;
        if (!isWhiteTurn()) return false;

        pad[p.x][p.y]=ChessColor.White;
        //不管是不是人机对决，都将放置过棋子的位置，权值设为-1
        value[p.x][p.y]=-1;

        changeTurn();
        return true;
    }

    //按照黑白顺序落子
    //这样就不用每次都被putWhite还是putBlack困扰了
    public boolean putChess(Position p) {
        this.stack.push(p);
        if (putBlack(p)) return true;
        if (putWhite(p)) return true;
        //如果前面没有return，说明这个点不合条件，需要pop()
        this.stack.pop();
        return false;
    }

    public boolean inBoundy(Position p) {
        if (p.x >=0 && p.x < 15 && p.y >=0 && p.y <15) return true;
        return false;
    }

    //传入的必须是行列坐标
    public boolean isEmptyPosition(Position p) {
        if (pad[p.x][p.y]!=ChessColor.Blank) return false;
        return true;
    }

    /**
     * 判断当前是不是黑方落子
     * @return
     */
    public static boolean isBlackTurn() {
        return BlackTurn;
    }

    /**
     * 判断当前是不是白方落子
     * @return
     */
    public static boolean isWhiteTurn() {
        return !BlackTurn;
    }

    public boolean isEmptyPad() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (pad[row][col]!=ChessColor.Blank) return false;
            }
        }
        return true;
    }

    //判断棋盘有没有满，满了的话，要提示平局
    public boolean isFullPad() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                //出现了空位，返回false
                if (pad[row][col]==ChessColor.Blank) return false;
            }
        }
        return true;
    }

    /**
     * 获取所有的黑子位置，方便画图
     * 返回的是行列坐标
     * @return
     */
    public List<Position> getBlackPositionList() {
        List<Position> list = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (pad[row][col]==ChessColor.Black) {
                    //pad使用的是行列坐标，但是画图使用的是点阵坐标
                    list.add(new Position(row,col));
                }
            }
        }
        return list;
    }

    public List<Position> getWhitePositionList() {
        List<Position> list = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (pad[row][col]==ChessColor.White) {
                    list.add(new Position(row,col));
                }
            }
        }
        return list;
    }

    /**
     * 交换落子对象化
     * 在每次调用putBlackChess或者putWhiteChess后
     * 都要调用该函数，保证同一个人不会连续落子两次
     */
    private void changeTurn() {
        this.BlackTurn=!this.BlackTurn;
    }

    /**
     * 悔棋，
     * 调用该函数后，会将上一步的棋子去除，
     * 存储棋子的栈会弹出一个元素
     *
     * @return
     */
    public boolean regrate() {
        if (this.getMode().equals("人人对战")) {
            if (stack.size()==0) return false;
            Position tmp = stack.pop();
            int row=tmp.x;
            int col=tmp.y;
            pad[row][col]=ChessColor.Blank;
            value[row][col]=0;
            clearEmptyPositionValue();
            changeTurn();
            return true;
        }
        //人机对战中，玩家悔棋，不但之前的黑子要被pop出栈，玩家之前的白子也要pop出栈
        //这里的悔棋，是基于Ai落子速度较快，在玩家刚落完子后，Ai就成功落子
        //不存在悔棋的时候，现在还是等待黑方落子的局面
        if (this.getMode().equals("人机对战") && isWhiteTurn()) {
            if (stack.size()<2) return false;//至少要出栈两个
            Position blackPosition = stack.pop();
            Position whitePosition = stack.pop();
            int blackRow=blackPosition.x;
            int whiteRow=whitePosition.x;
            int blackCol=blackPosition.y;
            int whiteCOl=whitePosition.y;

            pad[blackRow][blackCol]=ChessColor.Blank;
            pad[whiteRow][whiteCOl]=ChessColor.Blank;
            clearEmptyPositionValue();
            //人机 悔棋后，下一步是玩家落子
            setWhiteFirst();
            return true;
        }
        return false;
    }


    //设置黑方现行
    public void setBlackFirst() {
        this.BlackTurn=true;
    }

    //设置白方现行
    public void setWhiteFirst() {
        this.BlackTurn=false;
    }

    //清理存放棋盘的二维数组
    public void clearPad() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pad[i][j]=ChessColor.Blank;
            }
        }
    }

    //清理存放value的二维数组
    public void clearValue() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                value[i][j]=0;
            }
        }
    }


    /**
     * 清理空位置的value，方便Ai下一次赋值
     */
    public void clearEmptyPositionValue() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (pad[row][col] ==ChessColor.Blank) {
                    value[row][col]=0;
                }
            }
        }
    }

    //清理存储位置的栈
    public void clearStack() {
        this.stack.clear();
    }

    //将棋盘二维数组  value二维数组  和stack全部清理
    public void clear() {
        clearPad();
        clearValue();
        clearStack();
    }

    public void setPVEMode() {
        AiMode=true;
    }

    public void setPVPMode() {
        AiMode=false;
    }

    public boolean isPVEMode() {
        return AiMode;
    }

    public boolean isPVPMode() {
        return !AiMode;
    }

    public String getMode() {
        if (AiMode) return "人机对战";
        return "人人对战";
    }

    /**
     * 将value值累加到value二维数组的对应位置
     * @param p
     * @param value
     * @return
     */
    //传入的必须是行列坐标
    public boolean appendValue(Position p,long value) {
        if (!inBoundy(p)) return false;
        if (!isEmptyPosition(p)) return false;
        this.value[p.x][p.y]+=value;
        return true;
    }

    //返回权值二维数组
    public long[][] getValue() {
        return this.value;
    }

    //返回棋盘二维数组
    public ChessColor[][] getPad() {
        return this.pad;
    }

    //返回存储下棋顺序的
    public Stack getStack() {
        return this.stack;
    }

    public int getSize (){
        return size;
    }

    public void outputPad() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(pad[i][j]+",");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void outputValue() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(value[row][col]+",     ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }



}
