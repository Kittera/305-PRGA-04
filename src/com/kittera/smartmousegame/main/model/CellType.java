package com.kittera.smartmousegame.main.model;

import java.awt.*;

/**
 * Enumerates possible types for every MapTile.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
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
   
   public static CellType translateChar(char tileChar) {
      return switch (tileChar) {
         case 'B' -> BORDER;
         case 'P' -> PATH;
         case 'T' -> TUNNEL;
         case 'H' -> HEDGE;
         default  -> ERROR;
      };
   }
   
   /**
    * Ye olde toString().
    * @return String representation of enumeration.
    */
   public String toString() {
      return switch (this) {
         case PATH   -> "PATH";
         case TUNNEL -> "TUNNEL";
         case HEDGE  -> "HEDGE";
         case BORDER -> "BORDER";
         case ERROR  -> "ERROR";
      };
   }
}
