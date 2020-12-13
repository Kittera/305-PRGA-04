package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.util.Optional;

public class SmartMouse extends SmartMouseEntity {
   private static final String MY_SPRITE = "Mouse.png";
   
   public SmartMouse(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 2;
   }
   
   public boolean move(Directions direction) {
      boolean moved = super.move(direction);
      cheeseCheck();
      return moved;
   }
   
   private void cheeseCheck() {
      Optional<SmartMouseEntity> cheese =
            myTile.getTenantList()
            .stream()
            .filter(e -> e instanceof Cheese)
            .findFirst();
      
      if (cheese.isPresent()) {
         myTile.remove(cheese.get());
         stateMgr.cheeseEaten();
      }
   }
   
   
   public void pounced() {
      //TODO filter to tunnel tiles with a stream and findRandom()(?)
      // for respawn mechanic?
      // -gather list of all tiles somehow
      // -.stream()
      // -.filter(e -> e.getType == CellType.TUNNEL)
      // -.findAny();
   }
}
