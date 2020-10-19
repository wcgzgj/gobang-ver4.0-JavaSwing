package top.faroz.gobang.start;

import top.faroz.gobang.gui.frame.MainFrame;
import top.faroz.gobang.utils.GUIUtil;

import javax.swing.*;

/**
 * @ClassName BootFrame
 * @Description 启动类
 * @Author FARO_Z
 * @Date 2020/9/26 4:45 下午
 * @Version 1.0
 **/
public class BootFrame {
    public static void main(String[] args) {
        //使用自定义皮肤
        GUIUtil.useCustomLook();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame.getInstance().setVisible(true);
            }
        });
    }
}
