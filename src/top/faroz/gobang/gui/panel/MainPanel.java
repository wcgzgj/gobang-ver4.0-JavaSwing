package top.faroz.gobang.gui.panel;

import javax.swing.*;

/**
 * @ClassName MainPanel
 * @Description 垂直分隔主界面
 * @Author FARO_Z
 * @Date 2020/9/26 4:49 下午
 * @Version 1.0
 **/
public class MainPanel extends JSplitPane {
    private static MainPanel instance=new MainPanel();

    public static JPanel leftPanel=new JPanel();

    public static MainPanel getInstance() {
        return instance;
    }

    public MainPanel() {
        super(JSplitPane.HORIZONTAL_SPLIT, PausePanel.getInstance(),ButtonPanel.getInstance());

        //设置分割线位置
        this.setDividerLocation(800);
        //禁止中间分隔线移动
        this.setEnabled(false);
    }
}
