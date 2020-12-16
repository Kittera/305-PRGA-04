package com.kittera.smartmousegame.main.model;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MapTile extends JPanel {
   private final CellType myType;
   private final Point myAddress;
   private final List<SmartMouseEntity> myInhabitants = new ArrayList<>();
   private final MouseMap myBoard;
   private PathNode myPathNode;
   
   public MapTile(CellType type, int col, int row, MouseMap board) {
      myAddress = new Point(col, row);
      myPathNode = null; //this is done so nodes are only constructed when needed
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
      return (int) getPathNeighbors().stream()
            .filter(MapTile::isPath)
            .count();
   }
   
   public List<MapTile> getPathNeighbors() {
      return List.of(getNeighbor(Directions.NORTH),
                     getNeighbor(Directions.SOUTH),
                     getNeighbor(Directions.EAST),
                     getNeighbor(Directions.WEST))
                     .stream()
                     .filter(MapTile::isPath)
                     .collect(Collectors.toList());
   }
   
   public MouseMap getBoard() {
      return myBoard;
   }
   
   public PathNode getNode() {
      return myPathNode;
   }
   
   protected MapTile getNeighbor(Directions direction) {
      int x = myAddress.x;
      int y = myAddress.y;
      return switch (direction) {
         case CENTER -> this;
         case NORTH  -> myBoard.getTileAt(myAddress.x, myAddress.y - 1);
         case EAST   -> myBoard.getTileAt(x + 1, y);
         case SOUTH  -> myBoard.getTileAt(x, y + 1);
         case WEST   -> myBoard.getTileAt(x - 1, y);
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
   
   public int mannDistanceTo(MapTile theTile) {
      int xDist = Math.abs(theTile.getAddress().x - getAddress().x);
      int yDist = Math.abs(theTile.getAddress().y - getAddress().y);
      return xDist + yDist;
   }
   
   public float euclidDistanceTo(MapTile theTile) {
      double xDist = Math.pow((theTile.getAddress().x - getAddress().x), 2);
      double yDist = Math.pow((theTile.getAddress().y - getAddress().y), 2);
      return (float) Math.sqrt(xDist + yDist);
   }
   
   public Point getAddress() {
      return myAddress;
   }
   
   public boolean hasNode() {
      return myPathNode != null;
   }
   
   protected void resetPathNode() {
      if (hasNode()) myPathNode = null;
   }
   
   public PathNode installPathNode(PathNode initialParent) {
      myPathNode = new PathNode(this, initialParent);
      return myPathNode;
   }
   
   public String toString() {
      StringBuilder ts = new StringBuilder();
      ts.append(String.format("%s Tile at:", myType));
      ts.append(String.format(" (%d, %d) ", myAddress.x, myAddress.y));
      ts.append(myInhabitants.isEmpty()? "[]" : myInhabitants.toString());
      if (hasNode()) ts.append(String.format(" :::  %s", myPathNode.toString()));
      return ts.toString();
   }
}