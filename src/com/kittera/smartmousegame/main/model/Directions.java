package com.kittera.smartmousegame.main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public enum Directions {
   CENTER, NORTH, EAST, SOUTH, WEST;
   
   public Directions getCW() {
      return switch (this) {
         case NORTH  -> EAST;
         case EAST   -> SOUTH;
         case SOUTH  -> WEST;
         case WEST   -> NORTH;
         case CENTER -> pickRandom();
      };
   }
   
   public static Directions get(Object button, Object[] buttonArr) {
      ArrayList<Object> moveButtons = new ArrayList<>();
      Collections.addAll(moveButtons, buttonArr);
      
      return switch (moveButtons.indexOf(button)) {
         case 0  -> NORTH;
         case 1  -> SOUTH;
         case 2  -> WEST;
         case 3  -> EAST;
         default -> CENTER;
      };
   }
   
   public static Directions pickRandom() {
      Random picker = new Random();
      return switch (picker.nextInt(4)) {
         default -> NORTH;
         case 1  -> EAST;
         case 2  -> SOUTH;
         case 3  -> WEST;
      };
   }
}
