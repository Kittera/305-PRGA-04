package com.kittera.smartmousegame;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;
import com.kittera.smartmousegame.main.view.MouseMap;
import com.kittera.smartmousegame.main.view.SmartMouseFrame;

import java.io.File;

/**
 * Main class for SmartMouse Game. Conducts the game's setup process.
 */
public class SmartMouseMain {
   
   /**
    * "Setup Script" for Smart Mouse.
    * @param args used to receive name of the file containing map info.
    */
   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("A file must be provided to load the level from.");
         System.out.println("The file can be provided as the first and only CLI argument.");
         System.exit(0);
      }
      
      MouseMap loadedMap;
      SmartMouseStateManager gameState;
      
      try { //to load the map from given file
         System.out.println("Finding file...");
         File mapFile = new File(args[0]);
         gameState    = new SmartMouseStateManager();
         loadedMap    = new MouseMap(mapFile, gameState);
         System.out.println("Map loaded.\nLaunching...");
         new SmartMouseFrame(gameState, loadedMap);
      } catch (Exception e) {
         System.out.println("Map loading failed: " + e.getMessage());
         System.exit(0);
      }
   }
}