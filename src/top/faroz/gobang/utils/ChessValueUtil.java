package top.faroz.gobang.utils;

import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.domain.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ChessUtil
 * @Description 这里的设计是这样的
 * 将所有可能的棋形和其对应的权值存入hashmap中
 * 当遍历每个空位的四个方向和棋子所在的可能位置时
 * 将棋子链返回
 * @Author FARO_Z
 * @Date 2020/9/27 3:18 下午
 * @Version 1.0
 **/
public class ChessValueUtil {
    public static HashMap<String,Long> map=new HashMap<>();
    //五子棋棋形，最长为7，最短为4（有些棋形必须将空位包含进去）

    /**
     *   *:表示黑子
     *   -:表示空位
     *   o:表示白子
     */
    static {
        //还要重新设计赋分策略，才能保证五子棋机器人的准确判断
        //因为已经固定了 *为黑子，且默认将机器人设为黑方，所以机器人只能是黑方

        //还要设置防守方的数值，防止被gank掉
        //右边put的map值，是防守分数，这样Ai就不会一味的猛冲

        //左边：黑方Ai的棋子判断                      右边：Ai结束后，玩家可能的棋子判断
        map.put("*****", (long) 100000);//连五
        /*                                 */map.put("ooooo", (long) 30000);
        map.put("-****-", (long) 5000);//活四
        /*                                 */map.put("-oooo-", (long) 3000);
        map.put("*-***", (long) 700);//冲四  1
        /*                                 */map.put("o-ooo", (long) 150);
        map.put("***-*", (long) 700);//冲四  1  反向
        /*                                 */map.put("ooo-o", (long) 150);
        map.put("-****o", (long) 1000);//冲四  2
        /*                                 */map.put("-oooo*", (long) 200);
        map.put("o****-", (long) 1000);//冲四  2  反向
        /*                                 */map.put("*oooo-", (long) 200);
        map.put("**-**", (long) 700);//冲四   3
        /*                                 */map.put("oo-oo", (long) 200);
        map.put("-***-", (long) 500);//活三   1
        /*                                 */map.put("-ooo-", (long) 100);
        map.put("*-**", (long) 150);//活三  2
        /*                                 */map.put("o-oo", (long) 50);
        map.put("**-*", (long) 150);//活三  2   反向
        /*                                 */map.put("oo-o", (long) 50);
        map.put("--***o", (long) 100);//眠三  1
        /*                                 */map.put("--ooo*", (long) 20);
        map.put("o***--", (long) 100);//眠三  1  反向
        /*                                 */map.put("*ooo--", (long) 20);
        map.put("-*-**o", (long) 80);//眠三   2
        /*                                 */map.put("-o-oo*", (long) 15);
        map.put("o**-*-", (long) 80);//眠三   2  反向
        /*                                 */map.put("*oo-o-", (long) 15);
        map.put("-**-*o", (long) 60);//眠三   3
        /*                                 */map.put("-oo-o*", (long) 10);
        map.put("o*-**-", (long) 60);//眠三   3   反向
        /*                                 */map.put("*o-oo-", (long) 10);
        map.put("*--**", (long) 60);//眠三   4
        /*                                 */map.put("o--oo", (long) 10);
        map.put("**--*", (long) 60);//眠三   4   反向
        /*                                 */map.put("oo--o", (long) 10);
        map.put("*-*-*", (long) 60);//眠三   5
        /*                                 */map.put("o-o-o", (long) 10);
        map.put("o-***-o", (long) 60);//眠三   6
        /*                                 */map.put("*-ooo-*", (long) 2);
        map.put("--**--", (long) 50);//活二  1
        /*                                 */map.put("--oo--", (long) 2);
        map.put("-*-*-", (long) 20);//活二   2
        /*                                 */map.put("-o-o-", (long) 2);
        map.put("*--*", (long) 20);//活二   3
        /*                                 */map.put("o--o", (long) 2);
        map.put("---**o", (long) 10);//眠二  1
        /*                                 */map.put("---oo*", (long) 1);
        map.put("o**---", (long) 10);//眠二  1   反向
        /*                                 */map.put("*oo---", (long) 1);
        map.put("--*-*o", (long) 10);//眠二  2
        /*                                 */map.put("--o-o*", (long) 1 );
        map.put("o*-*--", (long) 10);//眠二  2   反向
        /*                                 */map.put("*o-o--", (long) 1);
        map.put("-*--*o", (long) 10);//眠二  3
        /*                                 */map.put("-o--o*", (long) 1);
        map.put("o*--*-", (long) 10);//眠二  3   反向
        /*                                 */map.put("*o--o-", (long) 1);
        map.put("*---*", (long) 10);//眠二  4
        /*                                 */map.put("o---o", (long) 1);
        //上面之所以int类型不能自动向上转换为long类型
        //是因为hashMap中的value使用的是包装类Long
        //包装类虽然能自动装箱，但是不能将基础类型转换，再装箱
        //所以需要手动转换为long类型
    }

    //获取权值最大的Position位置
    //且必须保证
    public static Position getMaxValuePosition() {
        ChessPad chessPad = ChessPad.getInstance();
        long[][] value = ChessPad.getInstance().getValue();
        int size=ChessPad.getInstance().getSize();
        long max = value[0][0];
        Position position = new Position(0, 0);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (value[row][col]>max) {
                    max=value[row][col];
                    position.x =row;
                    position.y =col;
                }
            }
        }

        //设置随机算法，保证玩家不会因为固定套路取胜
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(position);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (value[row][col]==value[position.x][position.y]) {
                    positions.add(new Position(row,col));
                }
            }
        }

        //最大下标要小于size
        int maxIndex = positions.size()-1;
        //返回随机位置
        return positions.get((int) (Math.random()*maxIndex));
    }

    public static boolean givePositionValue(Position p, List<String> allStringList) {
        ChessPad chessPad = ChessPad.getInstance();
        if (!chessPad.isEmptyPosition(p)) return false;
        for (String s : allStringList) {

            Long val = map.get(s);
            if (val!=null) {
                //因为这里使用的是appendValue,
                // 所以每次在Ai赋值的时候，
                // 要先将上一次Ai赋的值 清零
                chessPad.appendValue(p,val);
            }
        }
        return true;
    }



}
