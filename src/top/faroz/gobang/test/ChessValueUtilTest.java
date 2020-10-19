package top.faroz.gobang.test;

import top.faroz.gobang.utils.ChessValueUtil;

/**
 * @ClassName ChessValueUtilTest
 * @Description TODO
 * @Author FARO_Z
 * @Date 2020/9/27 4:24 下午
 * @Version 1.0
 **/
public class ChessValueUtilTest {
    //测试顺利
    //HashMao中String的key值是通过equals比较的
    public static void main(String[] args) {
        System.out.println(ChessValueUtil.map.get("*****"));
    }
}
