package top.faroz.gobang.gui.frame;

import javax.swing.*;
import java.awt.*;

/**
 * @ClassName MyFrame
 * @Description 自定义顶层窗体
 * @Author FARO_Z
 * @Date 2020/9/25 11:13 上午
 * @Version 1.0
 **/
public class MyFrame extends JFrame {
    public MyFrame() {
        // super();   MainFrame继承MyFrame，M yFrame继承JFrame
        //在实例化MainFrame的时候， 也会调用MyPanel的父类
        //所以，MyFrame不使用super()也没有关系
        this.setTitle("FARO_Z的五子棋游戏");
        this.setBounds(40,40,1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
    }
}
