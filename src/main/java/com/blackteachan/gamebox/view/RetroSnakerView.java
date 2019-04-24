package com.blackteachan.gamebox.view;

import com.blackteachan.gamebox.game.retrosnaker.RetroSnaker;
import com.blackteachan.gamebox.game.retrosnaker.RetroSnaker.Callback;

import javax.swing.*;
import java.awt.*;

/**
 * @author blackteachan
 */
public class RetroSnakerView {

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

        //游戏进程
        RetroSnaker retroSnaker = new RetroSnaker();
        retroSnaker.setState(RetroSnaker.STATE_RUN);
        retroSnaker.setCallback(new RetroSnakerCallback());
        retroSnaker.start();

    }

    public class RetroSnakerCallback extends Callback {
        @Override
        public void update(int x, int y, byte[][] map) {
            long time = System.currentTimeMillis();
            System.out.println("callback - x = " + x + ", y = " + y);
            Graphics g = displayPanel.getGraphics();
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    try {
                        System.out.println("i = " + i + ", j = " + j + ", map = " + map[i][j]);
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
