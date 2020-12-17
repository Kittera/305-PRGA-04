package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.util.Random;

/**
 * Defines a NorthSouthCat, which only travels north or south alternating  when it hits
 * an obstacle.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class NorthSouthCat extends AbstractCat {
   private static final String MY_SPRITE = "NorthSouthCat.png";
   
   /**
    * Creates a NorthSouthCat.
    * @param tile spawn tile
    * @param mgr  game state manager
    */
   public NorthSouthCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      if (new Random().nextBoolean()) myHeading = Directions.NORTH;
      else myHeading = Directions.SOUTH;
   }
   
   /**
    * Asks this cat to move.
    */
   public void move() {
      Directions reverseHeading = myHeading.getCW().getCW();
      if (!super.move(myHeading)) {
         super.move(myHeading.getCW().getCW());
         myHeading = reverseHeading;
      }
   }
}
