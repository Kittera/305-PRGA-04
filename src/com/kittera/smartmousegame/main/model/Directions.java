package com.kittera.smartmousegame.main.model;

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
