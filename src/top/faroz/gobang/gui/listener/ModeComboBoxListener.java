package top.faroz.gobang.gui.listener;

import top.faroz.gobang.gui.panel.ButtonPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @ClassName ModeComboBoxListener
 * @Description 选择模式的监听器
 * 如果选择  "人机对战"   就可以选择先后手
 * 如果选择  "人人对战"   就不能选择先后手
 * @Author FARO_Z
 * @Date 2020/9/28 5:08 下午
 * @Version 1.0
 **/
public class ModeComboBoxListener implements ItemListener {



    @Override
    public void itemStateChanged(ItemEvent e) {
        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        if (e.getItem().equals("人人对战")) {
            buttonPanel.sequence.setEnabled(false);
            buttonPanel.repaint();
        } else if (e.getItem().equals("人机对战")) {
            buttonPanel.sequence.setEnabled(true);
            buttonPanel.repaint();
        }
    }
}
