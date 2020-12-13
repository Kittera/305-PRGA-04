package com.kittera.smartmousegame.main.model;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;

public class SmartMouseActors {
   protected static RandomCat     CAT_1;
   protected static ClockwiseCat  CAT_2;
   protected static NorthSouthCat CAT_3;
   protected static EastWestCat   CAT_4;
   protected static SmartCat      CAT_5;
   public    static SmartMouse    MOUSE;
   
   public static void gameStep(@SuppressWarnings("unused") ActionEvent notUsed) {
      List.of(CAT_1, CAT_2, CAT_3, CAT_4, CAT_5)
            .stream()
            .filter(Objects::nonNull)
            .forEach(AbstractCat::move);
   }
   
   public static void reset() {
      List.of(CAT_1, CAT_2, CAT_3, CAT_4, CAT_5, MOUSE)
            .stream()
            .filter(Objects::nonNull)
            .forEach(SmartMouseEntity::reset);
   }
}
