package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.Timer;

import static com.kittera.smartmousegame.main.model.SmartMouseActors.CAT_5;

public class SmartMouse extends SmartMouseEntity {
   private static final String MY_SPRITE = "Mouse.png";
   private final Random tilePicker;
   
   public SmartMouse(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 2;
      tilePicker = new Random();
   }
   
   public boolean move(Directions direction) {
      boolean moved = super.move(direction);
      if (moved) cheeseCheck();
      if (moved && CAT_5 != null && !myTile.isTunnel()) CAT_5.refreshPath();
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
   
   protected void pounced() {
      var tunnels =
            stateMgr.getBoard()
                  .getTiles()
                  .stream()
                  .filter(MapTile::isTunnel)
                  .collect(Collectors.toList());
      if (tunnels.isEmpty()) {
         teleportTo(mySpawnTile);
         spawnFlash();
      } else {
         Timer flitting = new Timer(70, e -> flitAround(tunnels));
         Timer stopTime = new Timer(1500, e -> {flitting.stop(); spawnFlash();});
         stopTime.setRepeats(false);
         flitting.start();
         stopTime.start();
      }
   }
   
   private void spawnFlash() {
      myTile.remove(this);
      Timer appearing    = new Timer(200, e -> myTile.register(this));
      Timer disappearing = new Timer(200, e -> myTile.remove(this));
      Timer stopTime     = new Timer(1000, e -> stop(appearing, disappearing));
      appearing.setInitialDelay(100);
      stopTime.setRepeats(false);
      disappearing.start();
      appearing.start();
      stopTime.start();
   }
   
   private void stop(Timer appearing, Timer disappearing) {
      appearing.stop();
      disappearing.stop();
   }
   
   private void flitAround(List<MapTile> tunnels) {
      teleportTo(tunnels.get(tilePicker.nextInt(tunnels.size() - 1)));
   }
   
   private void teleportTo(MapTile destTile) {
      if (destTile != myTile && destTile.register(this)) {
         myTile.remove(this);
         myTile = destTile;
      }
   }
   
   public String toString() {
      Point loc = myTile.getAddress();
      return String.format("Mouse at (%d, %d)", loc.x, loc.y);
   }
}
