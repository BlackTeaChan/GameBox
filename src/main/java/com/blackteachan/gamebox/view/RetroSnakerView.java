package com.blackteachan.gamebox.view;

import com.blackteachan.gamebox.game.retrosnaker.RetroSnaker;
import com.blackteachan.gamebox.game.retrosnaker.RetroSnaker.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author blackteachan
 */
public class RetroSnakerView {

    private RetroSnaker retroSnaker;

    private JPanel jpanel;
    private JPanel displayPanel;

    public RetroSnakerView() {
        JFrame frame = new JFrame("Retro Snaker");
        frame.setContentPane(jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //固定大小
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //监听按键
        frame.addKeyListener(new OnKeyListener());

        //游戏进程
        retroSnaker = new RetroSnaker();
        retroSnaker.setState(RetroSnaker.STATE_RUN);
        retroSnaker.setCallback(new RetroSnakerCallback());
        retroSnaker.start();


    }

    /**
     * 键盘监听事件
     */
    public class OnKeyListener implements KeyListener{
        public void keyTyped(KeyEvent e) {
            System.out.println("keyTyped - KeyText: " + KeyEvent.getKeyText(e.getKeyCode()) + ", KeyCode: " + e.getKeyCode());
        }
        public void keyPressed(KeyEvent e) {
            retroSnaker.remove(e.getKeyCode() - KeyEvent.VK_LEFT);
            System.out.println("keyPressed - KeyText: " + KeyEvent.getKeyText(e.getKeyCode()) + ", KeyCode: " + e.getKeyCode());
        }
        public void keyReleased(KeyEvent e) {
            System.out.println("keyReleased - KeyText: " + KeyEvent.getKeyText(e.getKeyCode()) + ", KeyCode: " + e.getKeyCode());
        }
    }

    /**
     * 游戏线程回调
     */
    public class RetroSnakerCallback extends Callback {
        @Override
        public void update(int x, int y, byte[][] map) {
            long time = System.currentTimeMillis();
            Graphics g = displayPanel.getGraphics();
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    try {
                        Color color = Color.white;
                        if(map[i][j] == 0){
                            color = Color.white;
                        }else if(map[i][j] == 1){
                            color = Color.black;
                        }
                        g.setColor(color);
                        g.fill3DRect(j*20,i*20,20,20, true);
                    }catch (Exception e){
                        System.out.println(e + "\ni = " + i + ", j = " + j);
                    }
                }
            }
            System.out.println("RetroSnakerCallback.update() - time : " + (System.currentTimeMillis() - time) + "ms");
        }
    }

}
