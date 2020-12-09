package com.kittera.smartmousegame.main.controller;

import com.kittera.smartmousegame.main.model.SmartMouseEntities;

import javax.swing.*;

public class SmartMouseStateManager {
   
   private final Timer ticker;
   private final Timer elapsed;
   
   private boolean isInDebugMode;
   private int lifeCount;
   
   public SmartMouseStateManager() {
      isInDebugMode = false;
      lifeCount = 3;
      
      
      elapsed = new Timer(1000, null);
      
      ticker = new Timer(1000, null);
      ticker.addActionListener(SmartMouseEntities.CAT_1);
      ticker.addActionListener(SmartMouseEntities.CAT_2);
      ticker.addActionListener(SmartMouseEntities.CAT_3);
      ticker.addActionListener(SmartMouseEntities.CAT_4);
      ticker.addActionListener(SmartMouseEntities.CAT_5);
   }
   
   public void setDebugMode(boolean foo) {
      isInDebugMode = foo;
   }
   
   public boolean getDebugMode() {
      return isInDebugMode;
   }
   
   public void startTimer() {
      ticker.start();
      if (isInDebugMode) System.out.println("Ticker Timer Started.");
   }
   public void stopTimer() {
      ticker.stop();
      if (isInDebugMode) System.out.println("Ticker Timer Stopped.");
   }
   
   public void gameStart() {
      elapsed.start();
      if (isInDebugMode) System.out.println("Game timer Started.");
   }
}
