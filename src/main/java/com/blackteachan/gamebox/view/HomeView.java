package com.blackteachan.gamebox.view;

import javax.swing.*;

public class HomeView {
    private JPanel jpanel;

    public HomeView(){
        JFrame frame = new JFrame("HomeView");
        frame.setContentPane(jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
