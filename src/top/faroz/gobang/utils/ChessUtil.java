package top.faroz.gobang.utils;

import top.faroz.gobang.domain.ChessColor;
import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.domain.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChessUtil
 * @Description 这个工具类是用来返回棋子链条的
 * 然后再放到ChessValueUtil中进行处理
 * 并将棋子的权值赋到ChessPad中的value二维数组中
 * @Author FARO_Z
 * @Date 2020/9/27 3:41 下午
 * @Version 1.0
 **/
public class ChessUtil {

    /**
     * 将棋子的颜色序列转换为字符序列
     * 这样才能放入hashMap中进行比较
     * @param list
     * @return
     */
    public static String ChessChainToString(List<ChessColor> list) {
        String pool="";
        for (ChessColor chessColor : list) {
            if (chessColor==ChessColor.Blank) {
                pool+="-";
            } else if (chessColor==ChessColor.Black) {
                pool+="*";
            } else if(chessColor==ChessColor.White){
                pool+="o";
            } else {
                //放入一个hash表中不存在的字符
                //这样后面算权值的时候就不会把它加进去了
                pool+="#";
            }
        }
        return pool;
    }


    /**
     * 获取四个方向上的设定好的方向和位置的棋子链条
     * @param p 棋子位置
     * @param loc 棋子在棋链中的位置 （从1开始计数）
     * @param length 棋子链条的长度 （长度由4到7）->因为固定的棋形套路中，最短的是4，最长的是7
     * @return 四个方向上的设定好的方向和位置的棋子链条
     */
    //                                                                         注意:loc的起点数为1，不是0
    public static List<List<ChessColor>> getSettedLocationList(Position p, int length,int loc) {
        ChessPad chessPad = ChessPad.getInstance();
        ChessColor[][] pad = chessPad.getPad();
        int size = chessPad.getSize();
        //调用函数转换后，传入的p就是行列坐标，所以row=x,col=y;
        int row=p.x;
        int col=p.y;

        //这里将模式 和 该哪一方下 的判断分离出来，减少判断次数
        String mode = chessPad.getMode();
        String turn=null;
        if (chessPad.isBlackTurn()) {
            turn="Black";
        } else {
            turn="White";
        }



        //保证传入的棋子位置处于边界之内
        //且也要保证传入的位置上没有其他棋子
        if (!chessPad.inBoundy(p) ) {
            return null;
        }





        //存储所有棋链的容器
        List<List<ChessColor>> list = new ArrayList<>();





        /**
         * Lay方向取棋子  <---------------------------
         */
        //获取棋链的起始和终止位置
        // System.out.println(p);//p的坐标，表示几 行 几 列

        Position layStart = new Position(row, col - loc + 1);
        Position layEnd = new Position(row, col + length - loc);//末减初加1  结果为length，答案没有错误
        int LayStartCol = layStart.y;
        int LayEndCol = layEnd.y;
        int LayCommomRow=row;

        //只有在最边界的棋子都不会出界时候，才去取棋链
        if (chessPad.inBoundy(layStart) && chessPad.inBoundy(layEnd)) {

            //如果是人机模式，且是Ai
            if ("人机对战".equals(mode) && "Black".equals(turn)) {
                List<ChessColor> attackList = new ArrayList<>();
                List<ChessColor> defendList = new ArrayList<>();
                //attackList赋值
                //并且当前位置要假设为黑子 赋进去
                for (int i = LayStartCol; i <= LayEndCol; i++) {
                    if (i==col) {
                        attackList.add(ChessColor.Black);
                    } else  {
                        attackList.add(pad[LayCommomRow][i]);
                    }
                }
                //attackList赋值
                //并且当前位置要假设为白子 赋进去
                for (int i = LayStartCol; i <= LayEndCol; i++) {
                    if (i==col) {
                        defendList.add(ChessColor.White);
                    } else {
                        defendList.add(pad[LayCommomRow][i]);
                    }
                }

                list.add(attackList);
                list.add(defendList);
            } else {//人人对战   或者  人机对战中判断玩家是否获胜
                List<ChessColor> listLay = new ArrayList<>();
                for (int i = LayStartCol; i <= LayEndCol; i++) {
                    listLay.add(pad[LayCommomRow][i]);
                }
                list.add(listLay);
            }
        }

        /**
         * Stand方向取棋子  <---------------------------
         */
        Position standStart = new Position(row - loc + 1, col);
        Position standEnd = new Position(row + length - loc, col);
        int StandStartRow = standStart.x;
        int StandEndRow = standEnd.x;
        int StandCommonCol = col;

        //保证Stand方向上边界不会越界
        if (chessPad.inBoundy(standStart) && chessPad.inBoundy(standEnd)) {
            if ("人机对战".equals(mode) && "Black".equals(turn)) {
                List<ChessColor> attackList = new ArrayList<>();
                List<ChessColor> defendList = new ArrayList<>();

                for (int i = StandStartRow; i <=StandEndRow; i++) {
                    if (i==row) {
                        attackList.add(ChessColor.Black);
                    } else {
                        attackList.add(pad[i][StandCommonCol]);
                    }
                }
                for (int i = StandStartRow; i <=StandEndRow; i++) {
                    if (i==row) {
                        defendList.add(ChessColor.White);
                    } else {
                        defendList.add(pad[i][StandCommonCol]);
                    }
                }
                list.add(attackList);
                list.add(defendList);

            } else {//人人对战   或者  人机对战中判断玩家是否获胜
                List<ChessColor> listStand = new ArrayList<>();
                for (int i = StandStartRow; i <=StandEndRow; i++) {
                    listStand.add(pad[i][StandCommonCol]);
                }
                list.add(listStand);
            }
        }


        /**
         * Main 方向取棋子  <---------------------------
         */
        Position MainStart = new Position(row - loc + 1, col-loc+1);
        Position MainEnd = new Position(row + length - loc, col + length - loc);
        //这样转换，主要防止后面脑子被搞混了...
        int MainStartRow=MainStart.x;
        int MainEndRow = MainEnd.x;
        int MainStartCol = MainStart.y;
        int MainEndCol = MainEnd.y;

        if (chessPad.inBoundy(MainEnd) && chessPad.inBoundy(MainStart)) {
            if ("人机对战".equals(mode) && "Black".equals(turn)) {
                List<ChessColor> attackList = new ArrayList<>();
                List<ChessColor> defendList = new ArrayList<>();

                for (int rowi = MainStartRow, coli = MainStartCol; rowi <= MainEndRow; rowi++,coli++) {
                    if (rowi==row && coli==col) {
                        attackList.add(ChessColor.Black);
                    } else {
                        attackList.add(pad[rowi][coli]);
                    }
                }
                for (int rowi = MainStartRow, coli = MainStartCol; rowi <= MainEndRow; rowi++,coli++) {
                    if (rowi==row && coli==col) {
                        defendList.add(ChessColor.White);
                    } else {
                        defendList.add(pad[rowi][coli]);
                    }
                }
                list.add(attackList);
                list.add(defendList);


            } else {
                List<ChessColor> listMain = new ArrayList<>();
                //双变量for循环中的条件判断只能有一个
                for (int rowi = MainStartRow, coli = MainStartCol; rowi <= MainEndRow; rowi++,coli++) {
                    listMain.add(pad[rowi][coli]);
                }
                list.add(listMain);
            }
        }



        /**
         * Vice 方向取棋子  <---------------------------
         */
        Position ViceStart = new Position(row+loc-1,col-loc+1);
        Position ViceEnd = new Position(row-length+loc,col+length-loc);
        //vice方向，row递减，col递增！！！
        int ViceStartRow = ViceStart.x;
        int ViceEndRow = ViceEnd.x;
        int ViceStartCol = ViceStart.y;
        int ViceEndCol = ViceEnd.y;
        if (chessPad.inBoundy(ViceStart) && chessPad.inBoundy(ViceEnd)) {
            if ("人机对战".equals(mode) && "Black".equals(turn)) {
                List<ChessColor> attackList = new ArrayList<>();
                List<ChessColor> defendList = new ArrayList<>();

                for (int rowi=ViceStartRow,coli=ViceStartCol;rowi>=ViceEndRow;rowi--,coli++) {
                    if (rowi==row && coli==col) {
                        attackList.add(ChessColor.Black);
                    } else {
                        attackList.add(pad[rowi][coli]);
                    }
                }

                for (int rowi=ViceStartRow,coli=ViceStartCol;rowi>=ViceEndRow;rowi--,coli++) {
                    if (rowi==row && coli==col) {
                        defendList.add(ChessColor.White);
                    } else {
                        defendList.add(pad[rowi][coli]);
                    }
                }

                list.add(attackList);
                list.add(defendList);

            } else {
                List<ChessColor> listMain = new ArrayList<>();
                for (int rowi=ViceStartRow,coli=ViceStartCol;rowi>=ViceEndRow;rowi--,coli++) {
                    listMain.add(pad[rowi][coli]);
                }
                list.add(listMain);
            }
        }

        //  <-------------------------上面  四个方向的   在棋子长度指定的情况下，位置指定的情况下    获取的棋子链条
        return list;
    }



    /**
     * 获取所有可能位置，可能长度的棋子链条
     * 棋子链条，长度i最长为7，最短为4，被判断的位置可能在 长度为i的棋子链条的任意位置
     * 这个函数就是把所有可能都给取出来的
     * @param p
     * @return
     */
    public static List<List<ChessColor>> getAllList(Position p) {
        List<List<ChessColor>> list = new ArrayList<>();
        //将所有可能的棋子形状放入list中
        for (int i = 4; i <=7 ; i++) {
            for (int j = 1; j <=i ; j++) {
                List<List<ChessColor>> tmp = getSettedLocationList(p, i, j);
                if (tmp!=null) list.addAll(tmp);
            }
        }
        return list;
    }


    /**
     * 调取上面的函数，将list中的数据全部变为String ,方便HashMap比对
     * @param p
     * @return
     */
    public static List<String> getAllStringList(Position p) {

        List<String> StringList = new ArrayList<>();
        List<List<ChessColor>> allList = getAllList(p);
        // System.out.println(allList);

        for (List<ChessColor> chessColors : allList) {
            // System.out.println(chessColors);
            StringList.add(ChessChainToString(chessColors));
        }
        return StringList;
    }


}
