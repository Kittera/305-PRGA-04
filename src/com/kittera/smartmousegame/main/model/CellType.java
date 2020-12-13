package com.kittera.smartmousegame.main.model;

import java.awt.*;

public enum CellType {
   PATH, TUNNEL, HEDGE, BORDER, ERROR;
   
   protected Color getBaseColor() {
      return switch (this) {
         case PATH          -> new Color(0xC4C4C4);
         case TUNNEL        -> new Color(0x472500);
         case HEDGE         -> new Color(0x1AA81A);
         case BORDER, ERROR -> Color.BLACK;
      };
   }
   
   protected static CellType translateChar(char tileChar) {
      return switch (tileChar) {
         case 'B' -> BORDER;
         case 'P' -> PATH;
         case 'T' -> TUNNEL;
         case 'H' -> HEDGE;
         default  -> ERROR;
      };
   }
}
