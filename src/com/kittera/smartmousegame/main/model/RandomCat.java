package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

public class RandomCat extends AbstractCat {
   private static final String MY_SPRITE = "RandomCat.png";
   
   public RandomCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myHeading = Directions.pickRandom();
   }
   
   public void move() {
      boolean moved;
      int pathCount = myTile.pathCount();
      Directions newHeading;
   
      if (pathCount > 1) {//catches case where cat is spawned trapped
         do {
            newHeading = Directions.pickRandom();
            moved = super.move(newHeading);
         } while (!moved);
         myHeading = newHeading;
      } else {
         newHeading = myHeading.getCW().getCW();
         super.move(newHeading); //else continue forward
      }
   }
}