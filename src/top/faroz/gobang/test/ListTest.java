package top.faroz.gobang.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListTest
 * @Description 这个测试是为了验证一个list可不可以加到另一个list里
 * @Author FARO_Z
 * @Date 2020/9/27 5:55 下午
 * @Version 1.0
 **/
public class ListTest {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add(i);
        }

        List<Integer> list2 = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            list2.add(i);
        }

        list1.addAll(list2);

        for (Integer integer : list1) {
            System.out.println(integer);
        }
    }
}
