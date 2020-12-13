package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static guiResources.KGUIRepo.IMAGEPATH;

public class Cheese extends SmartMouseEntity {
   private static final String MY_SPRITE = "Cheese.png";
   
   public Cheese(MapTile tile, SmartMouseStateManager mgr) {
      super(MY_SPRITE, tile, mgr);
      mgr.addCheese(mySpawnTile);
      myLayer = 3;
   }
   
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
