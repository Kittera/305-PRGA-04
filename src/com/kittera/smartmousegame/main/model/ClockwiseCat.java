package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

public class ClockwiseCat extends AbstractCat {
   private static final String MY_SPRITE = "ClockwiseCat.png";
   public ClockwiseCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      myHeading = Directions.pickRandom();
   }
   
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
