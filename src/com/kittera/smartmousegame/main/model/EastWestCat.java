package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.util.Random;

public class EastWestCat extends AbstractCat {
   private static final String MY_SPRITE = "EastWestCat.png";
   
   public EastWestCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      if (new Random().nextBoolean()) myHeading = Directions.EAST;
      else myHeading = Directions.WEST;
   }
   
   public void move() {
      Directions reverseHeading = myHeading.getCW().getCW();
      if (!super.move(myHeading)) {
         super.move(myHeading.getCW().getCW());
         myHeading = reverseHeading;
      }
   }
}
