package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartCatPathFinder;
import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.awt.Point;
import java.util.Stack;

/**
 * Smart Cat with A* pathfinding.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class SmartCat extends AbstractCat {
   
   private static final String MY_SPRITE = "SmartCat.png";
   private Stack<MapTile> currentPathStack = new Stack<>();
   private final SmartCatPathFinder pFinder;
   
   /**
    * Creates the Smart Cat.
    * @param tile spawning tile
    * @param mgr  game=instance state manager
    */
   public SmartCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      pFinder = new SmartCatPathFinder();
   }
   
   /**
    * Asks the Smart Cat to move to the next tile in its path stack, if any are present.
    */
   public void move() {
      if (!currentPathStack.isEmpty()) {
         moveTo(currentPathStack.pop());
         mouseCheck();
      }
   }
   
   /**
    * Asks the SmartCat to refresh its path stack using its pathfinder.
    */
   public void refreshPath() {
      if (SmartMouseActors.MOUSE.getTile().isPath())
         currentPathStack = pFinder.findNewPath(myTile);
   }
   
   /**
    * Creates a String for debugging this cat.
    * @return String including location
    */
   public String toString() {
      Point loc = myTile.getAddress();
      return String.format("SmartCat at (%d, %d)", loc.x, loc.y);
   }
}
