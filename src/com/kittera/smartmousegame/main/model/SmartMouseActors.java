package com.kittera.smartmousegame.main.model;

import java.util.List;
import java.util.Objects;

/**
 * Directory of all actors on the board.
 */
public class SmartMouseActors {
   public static RandomCat     CAT_1;
   public static ClockwiseCat  CAT_2;
   public static NorthSouthCat CAT_3;
   public static EastWestCat   CAT_4;
   public static SmartCat      CAT_5;
   public static SmartMouse    MOUSE;
   
   public static List<AbstractCat> getCats() {
      return List.of(CAT_1, CAT_2, CAT_3, CAT_4, CAT_5);
   }
   
   public static void reset() {
      List.of(CAT_1, CAT_2, CAT_3, CAT_4, CAT_5, MOUSE)
            .stream()
            .filter(Objects::nonNull)
            .forEach(SmartMouseEntity::reset);
   }
}