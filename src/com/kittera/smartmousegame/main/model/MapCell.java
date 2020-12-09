package com.kittera.smartmousegame.main.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapCell extends JPanel {
   private final CellType myType;
   private final Point myAddress;
   private final List<SmartMouseEntity> myInhabitants = new ArrayList<>();
   
   private boolean isCrossroad;
   private boolean isDeadEnd;
   private SmartMouseEntity myDisplayEntity;
   
   public MapCell(CellType type, int col, int row) {
      myType = type;
      myAddress = new Point(col, row);
   }
   
   public void paintComponent(Graphics g) {
      myType.getBaseColor(g);
      g.fillRect(0,0,getWidth(), getHeight());
      if (isOccupied()) {
      
      }
   }
   
   private boolean isOccupied() {
      return myInhabitants.size() > 0;
   }
   
   public boolean register(SmartMouseEntity arrivingActor) {
      boolean result;
      switch (myType) {
         case BUSH, BORDER -> result = false;
         case PATH -> result = true;
         case TUNNEL -> result = !(arrivingActor instanceof AbstractCat);
         default -> throw new IllegalStateException("Unexpected value: " + myType);
      }
      return result;
   }
   
   public void remove(SmartMouseEntity leavingActor) {
      myInhabitants.remove(leavingActor);
   }
}
