package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.util.Random;

public class NorthSouthCat extends AbstractCat {
   private static final String MY_SPRITE = "NorthSouthCat.png";
   
   public NorthSouthCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      if (new Random().nextBoolean()) myHeading = Directions.NORTH;
      else myHeading = Directions.SOUTH;
   }
   
   public void move() {
      Directions reverseHeading = myHeading.getCW().getCW();
      if (!super.move(myHeading)) {
         super.move(myHeading.getCW().getCW());
         myHeading = reverseHeading;
      }
   }
}
