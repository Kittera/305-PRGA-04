package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static guiResources.KGUIRepo.IMAGEPATH;

/**
 * Abstraction class for all entities in the Smart Mouse Game.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public abstract class SmartMouseEntity  {
   protected final MapTile mySpawnTile;
   private final Image mySprite;
   protected final SmartMouseStateManager stateMgr;
   protected Directions myHeading;
   protected MapTile myTile;
   protected int myLayer;
   
   /**
    * Initializes the state of all fields shared by all Entities on the board.
    * @param spriteName name of the source image for this instance's sprite
    * @param tile tile to spawn on
    * @param mgr  game state manager hookup
    */
   public SmartMouseEntity(String spriteName, MapTile tile, SmartMouseStateManager mgr) {
      myHeading = Directions.CENTER;
      mySpawnTile = tile;
      myTile = mySpawnTile;
      myTile.register(this);
      stateMgr = mgr;
      try {
         mySprite  = ImageIO.read(new File(IMAGEPATH + spriteName));
      } catch (IOException stop) {
         throw new IllegalArgumentException("Actor sprite not found at:" + IMAGEPATH + spriteName);
      }
   }
   
   /**
    * Accessor for this entity's render layer.
    * @return value of myLayer
    */
   public int getLayer() {return myLayer;}
   
   /**
    * Accessor for the Image that is to be used as this entity's sprite.
    * @param width width to scale the sprite data to
    * @param height height to scale the sprite data to
    * @return scaled instance of mySprite, a new Image
    */
   public Image getSprite(int width, int height) {
      return mySprite.getScaledInstance(width, height, Image.SCALE_DEFAULT);
   }
   
   /**
    * Accessor for the tile this entity currently inhabits.
    * @return reference to inhabited tile
    */
   public MapTile getTile() {
      return myTile;
   }
   
   /**
    * Asks this entity to move in a direction.
    * @param direction enumerated direction to move toward
    * @return whether move could be made
    */
   public boolean move(Directions direction) {
      MapTile destTile =
            direction == Directions.CENTER ? myTile : myTile.getNeighbor(direction);
      boolean moved = destTile.register(this);
      if (destTile != myTile && moved) {
         myTile.remove(this);
         myTile = destTile;
      }
      return moved;
   }
   
   /**
    * Asks this entity to revert to its state as of game load.
    */
   public void reset() {
      myTile.remove(this);
      mySpawnTile.register(this);
      myTile = mySpawnTile;
   }
}