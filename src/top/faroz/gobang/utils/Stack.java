package top.faroz.gobang.utils;

import java.util.LinkedList;

/**
 * @ClassName Stack
 * @Description 需要一个栈，用来存放下棋的顺序，方便后面悔棋用
 * @Author FARO_Z
 * @Date 2020/9/28 9:57 下午
 * @Version 1.0
 **/
public class Stack <T>{
    private LinkedList<T> stack=new LinkedList<T>();

    public void push(T val) {
        stack.add(val);
    }

    public T pop() {
        return stack.getLast();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}
