package top.faroz.gobang.gui.frame;

import top.faroz.gobang.gui.panel.MainPanel;

/**
 * @ClassName MainFrame
 * @Description boot的时候，调用的就是这个frame
 * @Author FARO_Z
 * @Date 2020/9/25 11:12 上午
 * @Version 1.0
 **/
public class MainFrame extends MyFrame{
    public static MainFrame instance=new MainFrame();

    public static MainFrame getInstance() {
        return instance;
    }

    public MainFrame() {
        super();
        this.setContentPane(MainPanel.getInstance());
    }
}
