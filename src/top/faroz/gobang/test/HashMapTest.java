package top.faroz.gobang.test;

import top.faroz.gobang.utils.ChessValueUtil;

import java.util.HashMap;

/**
 * @ClassName HashMapTest
 * @Description TODO
 * @Author FARO_Z
 * @Date 2020/9/28 4:41 下午
 * @Version 1.0
 **/

/**
 * 通过这个实验可知道,找不到Key值的情况下，HashMap返回Key值
 */
public class HashMapTest {
    public static void main(String[] args) {
        // HashMap<Integer, Integer> map = new HashMap<>();
        // for (int i = 0; i < 10; i++) {
        //     map.put(i,i*2);
        // }
        //
        // if (map.get(11)==null) {
        //     System.out.println("嬲");
        // }
        //
        // if (map.get(0)==0) {
        //     System.out.println("嬲");
        // }


        HashMap<String, Long> map = ChessValueUtil.map;
        System.out.println(map.get("*****"));
        System.out.println(map.get("-****"));
        System.out.println(map.get("o***--"));
    }
}
