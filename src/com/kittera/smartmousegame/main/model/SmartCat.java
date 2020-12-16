package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartCatPathFinder;
import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.awt.*;
import java.util.Stack;

public class SmartCat extends AbstractCat {
   
   private static final String MY_SPRITE = "SmartCat.png";
   private Stack<MapTile> currentPathStack = new Stack<>();
   private final SmartCatPathFinder pFinder;
   
   public SmartCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
      pFinder = new SmartCatPathFinder();
   }
   
   public void move() {
      if (!currentPathStack.isEmpty()) moveTo(currentPathStack.pop());
   }
   
   protected void refreshPath() {
      currentPathStack = pFinder.findNewPath(myTile);
   }
   
   public String toString() {
      Point loc = myTile.getAddress();
      return String.format("SmartCat at (%d, %d)", loc.x, loc.y);
   }
}
