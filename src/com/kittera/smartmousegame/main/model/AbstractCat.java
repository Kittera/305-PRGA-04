package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

/**
 * Defines behavior common to all Cats in Smart Mouse.
 */
public abstract class AbstractCat extends SmartMouseEntity {
   
   public AbstractCat(String spriteName, MapTile tile, SmartMouseStateManager mgr) {
      super(spriteName, tile, mgr);
      myLayer = 1;
   }
   
   public boolean move(Directions direction) {
      boolean moved = super.move(direction);
      if (moved) mouseCheck();
      return moved;
   }
   
   public void moveTo(MapTile destTile) {
      if (destTile != myTile && destTile.register(this)) {
         myTile.remove(this);
         myTile = destTile;
      }
   }
   
   void pounce(SmartMouseEntity m) {
      ((SmartMouse) m).pounced();
   }
   
   protected void mouseCheck() {
      myTile.getTenantList()
            .stream()
            .filter(e -> e instanceof SmartMouse)
            .findFirst()
            .ifPresent(this::pounce);
   }
   
   public abstract void move();
}
