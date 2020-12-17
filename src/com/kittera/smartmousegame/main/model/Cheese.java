package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import static guiResources.KGUIRepo.IMAGEPATH;

/**
 * Defines the Cheese entity for Smart Mouse.
 */
public class Cheese extends SmartMouseEntity {
   private static final String MY_SPRITE = "Cheese.png";
   
   /**
    * Creates a Cheese.
    * @param tile tile to create cheese on
    * @param mgr game state manager
    */
   public Cheese(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      myLayer = 3;
   }
   
   /**
    * Gets a sprite for drawing a Cheese.
    * @param scale dimension of sprite(square)
    * @return scaled sprite Image
    */
   public static Image getCheeseSprite(int scale) {
      Image sprite;
      try {
         sprite = ImageIO.read(new File(IMAGEPATH + MY_SPRITE));
      } catch (IOException stop) {
         throw new IllegalArgumentException("Actor sprite not found at:" + IMAGEPATH + MY_SPRITE);
      }
      return sprite.getScaledInstance(scale, scale, Image.SCALE_DEFAULT);
   }
}
