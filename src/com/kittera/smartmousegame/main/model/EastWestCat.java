package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.util.Random;

/**
 * Defines an EastWest Cat which only moves alternating east to west upon encountering an
 * obstacle.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class EastWestCat extends AbstractCat {
   private static final String MY_SPRITE = "EastWestCat.png";
   
   /**
    * Creates an EastWestCat.
    * @param tile spawn tile
    * @param mgr  game state manager
    */
   public EastWestCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      if (new Random().nextBoolean()) myHeading = Directions.EAST;
      else myHeading = Directions.WEST;
   }
   
   /**
    * Asks the cat to move.
    */
   public void move() {
      Directions reverseHeading = myHeading.getCW().getCW();
      if (!super.move(myHeading)) {
         super.move(myHeading.getCW().getCW());
         myHeading = reverseHeading;
      }
   }
}
