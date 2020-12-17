package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

/**
 * Defines the ClockWise cat, which turns clockwise every time it encounters an obstacle.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class ClockwiseCat extends AbstractCat {
   private static final String MY_SPRITE = "ClockwiseCat.png";
   
   /**
    * Creates a ClockwiseCat.
    * @param tile tile to spawn on
    * @param mgr  game state manager
    */
   public ClockwiseCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      myHeading = Directions.pickRandom();
   }
   
   /**
    * Asks the ClockwiseCat to move.
    */
   public void move() {
      if (!super.move(myHeading)) {
         boolean moved;
         Directions newHeading;
         do {
            newHeading = myHeading.getCW();
            moved = super.move(newHeading);
         } while (!moved);
         myHeading = newHeading;
      }
   }
}
