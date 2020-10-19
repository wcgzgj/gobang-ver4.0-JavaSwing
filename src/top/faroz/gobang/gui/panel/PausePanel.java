package top.faroz.gobang.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * @ClassName PausePanel
 * @Description 这个面板是暂停用的panel
 * 当玩家没有选择开始的时候，会显示这个Panel,以提示玩家选择游戏模式
 * @Author FARO_Z
 * @Date 2020/9/26 8:39 下午
 * @Version 1.0
 **/
public class PausePanel extends JPanel {
    public static JLabel tip=new JLabel("            请选择游戏模式\n并点击开始");
    Font font = new Font("隶书", Font.BOLD, 40);

    private static PausePanel instance=new PausePanel();

    public static PausePanel getInstance() {
        return instance;
    }

    private PausePanel() {
        this.setSize(new Dimension(800,800));
        this.setBackground(null);
        this.setLayout(new BorderLayout());

    }

    public void paint(Graphics g) {
        ImageIcon img = new ImageIcon("src/img/pauseBk.jpg");
        img.paintIcon(this,g,0,0);
        Graphics2D g2= (Graphics2D) g;
        g2.setFont(font);
        g2.setColor(Color.CYAN);
        g2.drawString("欢迎来到FARO_Z的五子棋",160,300);
        g2.drawString("请选择模式   并点击开始",180,400);

        // tip.setFont(font);
        // this.add(tip,BorderLayout.CENTER);
    }

}
