package com.kittera.smartmousegame.main.model;

import java.awt.*;

public enum CellType {
   PATH, TUNNEL, BUSH, BORDER;
   
   protected void getBaseColor(Graphics g) {
      switch (this) {
         case PATH   -> g.setColor(new Color(0x6F6F6F));
         case TUNNEL -> g.setColor(new Color(0x894C00));
         case BUSH   -> g.setColor(new Color(0x009E00));
         case BORDER -> g.setColor(new Color(0x000000));
      }
   }
}
