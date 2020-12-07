package com.kittera.smartmousegame;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;
import com.kittera.smartmousegame.main.view.SmartMouseFrame;

public class SmartMouseMain {
   public static void main(String[] args) {
      var gameState = new SmartMouseStateManager();
      
      new SmartMouseFrame(gameState);
   
   
   }
}
