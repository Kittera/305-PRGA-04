package com.kittera.smartmousegame.main.view;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import javax.swing.*;

public class SmartMouseFrame extends JFrame {
   public SmartMouseFrame(SmartMouseStateManager stateManager) {
      setTitle("PRGA04 Smart Mouse");
      
      add(new MouseGamePanel(stateManager));
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(625,600);
      pack();
      setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
   }
}
