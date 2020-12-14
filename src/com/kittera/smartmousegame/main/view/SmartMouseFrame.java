package com.kittera.smartmousegame.main.view;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;
import com.kittera.smartmousegame.main.model.MouseMap;

import javax.swing.*;


import static guiResources.KGUIRepo.FACE_PALM;

public class SmartMouseFrame extends JFrame {
   public SmartMouseFrame(SmartMouseStateManager stateManager, MouseMap theBoard) {
      add(new MouseGamePanel(stateManager, theBoard));
   
      //noinspection SpellCheckingInspection
      setTitle("PRGA04 Smart Mouse");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setIconImage(FACE_PALM.getImage());
      setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
   }
   
   
}
