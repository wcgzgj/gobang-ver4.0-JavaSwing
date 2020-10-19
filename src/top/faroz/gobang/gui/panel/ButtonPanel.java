package top.faroz.gobang.gui.panel;

import top.faroz.gobang.domain.ChessPad;
import top.faroz.gobang.gui.listener.ModeComboBoxListener;
import top.faroz.gobang.gui.listener.RegrateButtonListener;
import top.faroz.gobang.gui.listener.StartButtonListener;
import top.faroz.gobang.gui.listener.StopButtonListener;

import javax.swing.*;
import java.awt.*;

/**
 * @ClassName ButtonPanel
 * @Description 主界面右边，用于存放选择按钮的Panel
 * @Author FARO_Z
 * @Date 2020/9/26 4:52 下午
 * @Version 1.0
 **/
public class ButtonPanel extends JPanel {
    public static ButtonPanel instance=new ButtonPanel();

    Font font = new Font("宋体", Font.BOLD, 20);
    public JButton start = new JButton("开始");
    public JButton stop = new JButton("停止当前游戏");
    public JButton regrate = new JButton("悔棋");
    public JButton tips = new JButton("提示");

    public JComboBox modes = new JComboBox();
    public JComboBox sequence = new JComboBox();

    //提醒玩家，当前是哪一方落子
    public JLabel sideReminder =new JLabel("当前落子方：");

    public static ImageIcon img=new ImageIcon("src/img/padBackGround.png");



    public static ButtonPanel getInstance() {
        return instance;
    }

    private ButtonPanel() {

        this.setLayout(new GridLayout(7,1,50,20));

        start.setFont(font);
        stop.setFont(font);
        regrate.setFont(font);
        tips.setFont(font);
        sideReminder.setFont(font);

        modes.addItem("人机对战");
        modes.addItem("人人对战");
        modes.setFont(font);

        sequence.addItem("后手");
        sequence.addItem("先手");
        sequence.setFont(font);




        this.add(start);
        this.add(stop);
        this.add(regrate);
        this.add(modes);
        this.add(sequence);
        this.add(tips);

        this.add(sideReminder);

        resetReminder();


        stop.addActionListener(new StopButtonListener());
        start.addActionListener(new StartButtonListener());
        modes.addItemListener(new ModeComboBoxListener());
        regrate.addActionListener(new RegrateButtonListener());

        stop.setEnabled(false);
        regrate.setEnabled(false);
        tips.setEnabled(false);


        repaint();

    }


    //用来重置落子提示标签
    public void resetReminder() {
        ChessPad chessPadInstance = ChessPad.getInstance();
        if (chessPadInstance.isBlackTurn()) {
            sideReminder.setText("当前落子方：黑");
        } else {
            sideReminder.setText("当前落子方：白");
        }
        //必须要调用  paintImmediately  不然在人机对战的时候，不会及时更新
        //设置的宽、高，必须是JLable标签所在的Panel中的宽高
        this.paintImmediately(0,0,200,800);
    }

}
