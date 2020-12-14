package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static guiResources.KGUIRepo.IMAGEPATH;

public abstract class SmartMouseEntity  {
   protected final MapTile mySpawnTile;
   private final Image mySprite;
   protected final SmartMouseStateManager stateMgr;
   protected Directions myHeading;
   protected MapTile myTile;
   protected int myLayer;
   
   public SmartMouseEntity(String spriteName, MapTile tile, SmartMouseStateManager mgr) {
      myHeading = Directions.CENTER;
      mySpawnTile = tile;
      myTile = mySpawnTile;
      mySpawnTile.register(this);
      stateMgr = mgr;
      try {
         mySprite  = ImageIO.read(new File(IMAGEPATH + spriteName));
      } catch (IOException stop) {
         throw new IllegalArgumentException("Actor sprite not found at:" + IMAGEPATH + spriteName);
      }
   }
   
   public Image getSprite(int width, int height) {
      return mySprite.getScaledInstance(width, height, Image.SCALE_DEFAULT);
   }
   public int getLayer() {return myLayer;}
   
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
   
   public void reset() {
      if (myTile != mySpawnTile) {
         mySpawnTile.register(this);
         myTile.remove(this);
         myTile = mySpawnTile;
      }
   }
}
