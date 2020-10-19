package top.faroz.gobang.utils;

import javax.swing.*;

/**
 * @ClassName GUIUtils
 * @Description TODO
 * @Author FARO_Z
 * @Date 2020/9/25 1:25 下午
 * @Version 1.0
 **/
public class GUIUtil {
    //使用自定义皮肤
    public static void useCustomLook() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
