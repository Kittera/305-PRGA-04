package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.Timer;

/**
 * Defines the Mouse object in the Smart Mouse Game.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class SmartMouse extends SmartMouseEntity {
   private static final String MY_SPRITE = "Mouse.png";
   private final Random tilePicker;
   private int myLives;
   
   /**
    * Creates the Mouse.
    * @param tile spawn tile for the Mouse
    * @param mgr this game-instance state manager.
    */
   public SmartMouse(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 2;
      tilePicker = new Random();
      myLives = 3;
   }
   
   /**
    * Asks the Mouse to move in a particular, user-decided direction.
    * @param direction enumerated direction to move
    * @return whether destination tile accepted the movement.
    */
   public boolean move(Directions direction) {
      boolean moved = super.move(direction);
      if (moved) cheeseCheck();
      return moved;
   }
   
   /**
    * Creates a string for debugging this Mouse.
    * @return String including location
    */
   public String toString() {
      Point loc = myTile.getAddress();
      return String.format("Mouse at (%d, %d)", loc.x, loc.y);
   }
   
   /**
    * Called when the mouse is pounced by a Cat. Triggers respawn mechanism.
    */
   protected void pounced() {
      stateMgr.mousePounced(--myLives);
      var tunnels =
            stateMgr.getBoard()
                  .getTiles()
                  .stream()
                  .filter(MapTile::isTunnel)
                  .collect(Collectors.toList());
      
      if (tunnels.isEmpty()) {
         teleportTo(mySpawnTile);
         spawnFlash();
      }
      else {
         Timer flitting = new Timer(70, e -> flitAround(tunnels));
         Timer stopTime = new Timer(1500, e -> {flitting.stop(); spawnFlash();});
         stopTime.setRepeats(false);
         flitting.start();
         stopTime.start();
      }
   }
   
   /**
    * Performs a check on the current tile for the presence of any cheese, and eats
    * it if so.
    */
   private void cheeseCheck() {
      Optional<SmartMouseEntity> cheese = //store as Optional because result could be nil
            myTile.getTenantList()
                  .stream()
                  .filter(e -> e instanceof Cheese)
                  .findFirst();
      
      if (cheese.isPresent()) {
         myTile.remove(cheese.get());
         stateMgr.cheeseEaten();
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
}
