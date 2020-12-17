package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

/**
 * RandomCat moves randomly. Every time a game step is triggered, it picks a random
 * direction and attempts to make the move in that direction until it makes a successful
 * move.
 */
public class RandomCat extends AbstractCat {
   private static final String MY_SPRITE = "RandomCat.png";
   
   /**
    * RandomCat constructor.
    * @param tile MapTile where the cat is being spawned; this "places" the cat on the map.
    * @param mgr Game state tracking object,
    */
   public RandomCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myHeading = Directions.pickRandom();
   }
   
   /**
    * Asks the RandomCat to attempt to move in a random direction.
    */
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
         super.move(newHeading);
      }
   }
}