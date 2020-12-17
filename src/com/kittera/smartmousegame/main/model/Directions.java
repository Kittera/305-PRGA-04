package com.kittera.smartmousegame.main.model;

import java.util.Random;

/**
 * Enumerates cardinal directions and provides a random chooser mechanism.
 */
public enum Directions {
   CENTER, NORTH, EAST, SOUTH, WEST;
   
   /**
    * Returns the next cardinal direction clockwise from current.
    * @return clockwise direction from this one
    */
   public Directions getCW() {
      return switch (this) {
         case NORTH  -> EAST;
         case EAST   -> SOUTH;
         case SOUTH  -> WEST;
         case WEST   -> NORTH;
         case CENTER -> pickRandom();
      };
   }
   
   /**
    * Picks a random direction.
    * @return random direction
    */
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
