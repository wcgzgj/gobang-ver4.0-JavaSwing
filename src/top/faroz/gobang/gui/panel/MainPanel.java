package top.faroz.gobang.gui.panel;

import javax.swing.*;

/**
 * @ClassName MainPanel
 * @Description TODO
 * @Author FARO_Z
 * @Date 2020/9/26 4:49 下午
 * @Version 1.0
 **/
public class MainPanel extends JSplitPane {
    private static MainPanel instance=new MainPanel();

    public static JPanel leftPanel=new JPanel();
    // public static JPanel rightPanel=new JPanel();


    public static MainPanel getInstance() {
        return instance;
    }

    public MainPanel() {
        // super(JSplitPane.HORIZONTAL_SPLIT, ChessPanel.getInstance(),ButtonPanel.getInstance());
        // super(JSplitPane.HORIZONTAL_SPLIT, leftPanel,ButtonPanel.getInstance());
        super(JSplitPane.HORIZONTAL_SPLIT, PausePanel.getInstance(),ButtonPanel.getInstance());

        //设置分割线位置
        this.setDividerLocation(800);
        // this.setBackground(Color.BLACK);
        //禁止中间分隔线移动
        this.setEnabled(false);

        // leftPanel.setLayout(new BorderLayout());
        // leftPanel.add(ChessPanel.getInstance(),BorderLayout.CENTER);

    }
}
