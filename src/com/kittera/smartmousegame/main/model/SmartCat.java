package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

public class SmartCat extends AbstractCat {
   
   private static final String MY_SPRITE = "SmartCat.png";
   
   public SmartCat(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 1;
   }
   
   public void move() {
      var theMouse = SmartMouseActors.MOUSE;
      
      //find delta x and delta y
      
   }
}
