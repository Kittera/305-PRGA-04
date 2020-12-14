package com.kittera.smartmousegame.main.model;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapTile extends JPanel {
   private final CellType myType;
   private final Point myAddress;
   private final List<SmartMouseEntity> myInhabitants = new ArrayList<>();
   private final MouseMap myBoard;
   
   public MapTile(CellType type, int col, int row, MouseMap board) {
      myAddress = new Point(col, row);
      myType = type;
      myBoard = board;
      setBackground(myType.getBaseColor());
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      var g2 = (Graphics2D) g;
      if (myInhabitants.size() > 0) {
         Image sprite = myInhabitants.get(0)
               .getSprite(getWidth(), getHeight());
         g2.drawImage(sprite,0,0, this);
      }
   }
   
   public boolean register(SmartMouseEntity arrivingActor) {
      boolean accessGranted = switch (myType) {
         default     -> false;
         case PATH   -> true;
         case TUNNEL -> !(arrivingActor instanceof AbstractCat);
      };
      if (accessGranted) {
         if (!myInhabitants.contains(arrivingActor)) myInhabitants.add(arrivingActor);
         myInhabitants.sort(Comparator.comparing(SmartMouseEntity::getLayer));
      }
      repaint();
   
      return accessGranted;
   }
   
   public void remove(SmartMouseEntity leavingActor) {
      myInhabitants.remove(leavingActor);
      repaint();
   }
   
   public List<SmartMouseEntity> getTenantList() {
      return myInhabitants;
   }
   
   public int pathCount() {
      return (int) List.of(getNeighbor(Directions.NORTH),
                           getNeighbor(Directions.SOUTH),
                           getNeighbor(Directions.EAST),
                           getNeighbor(Directions.WEST))
                           .stream()
                           .filter(MapTile::isPath)
                           .count();
   }
   
   protected MapTile getNeighbor(Directions direction) {
      int xToGet = myAddress.x;
      int yToGet = myAddress.y;
      return switch (direction) {
         case CENTER -> this;
         case NORTH  -> myBoard.getTileAt(xToGet, yToGet - 1);
         case EAST   -> myBoard.getTileAt(xToGet + 1, yToGet);
         case SOUTH  -> myBoard.getTileAt(xToGet, yToGet + 1);
         case WEST   -> myBoard.getTileAt(xToGet - 1, yToGet);
      };
   }
   
   protected boolean isPath() {
      return myType == CellType.PATH;
   }
   
   protected boolean isTunnel() {
      return myType == CellType.TUNNEL;
   }
   
   public void clearRegistry() {
      myInhabitants.clear();
      repaint();
   }
}
