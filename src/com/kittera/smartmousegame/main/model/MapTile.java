package com.kittera.smartmousegame.main.model;

import com.kittera.smartmousegame.main.view.MouseMap;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for encapsulating the behavior and state of a single tile on the MouseMap.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class MapTile extends JPanel {
   private final CellType myType;
   private final Point myAddress;
   private final List<SmartMouseEntity> myInhabitants = new ArrayList<>();
   private final MouseMap myBoard;
   private PathNode myPathNode;
   
   /**
    * Crafts a new MapTile.
    * @param type type of tile to create,
    * @param col column this tile is to be in
    * @param row row this tile is to be in
    * @param board the MouseMap that will contain this tile
    */
   public MapTile(CellType type, int col, int row, MouseMap board) {
      myAddress = new Point(col, row);
      myPathNode = null; //this is done so nodes are only constructed when needed
      myType = type;
      myBoard = board;
      setBackground(myType.getBaseColor());
   }
   
   /**
    * Used as the request for "docking permission" for a given actor. If they are allowed
    * on this kind of tile, <code>true</code> is returned and the actor is added to the
    * inhabitant list.
    * @param arrivingActor actor attempting to move here
    * @return whether the actor is able to move here
    */
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
   
   /**
    * De-registers a given actor from this tile.
    * @param leavingActor actor moving out of this tile
    */
   public void remove(SmartMouseEntity leavingActor) {
      myInhabitants.remove(leavingActor);
      repaint();
   }
   
   /**
    * Accessor for the list of all entities at this tile's location
    * @return contents of myInhabitants
    */
   public List<SmartMouseEntity> getTenantList() {
      return myInhabitants;
   }
   
   /**
    * Counts how many CellType.PATH tiles are cardinally adjacent to this one.
    * @return how many tiles were counted
    */
   public int pathCount() {
      return (int) getPathNeighbors().stream()
            .filter(MapTile::isPath)
            .count();
   }
   
   /**
    * Accessor for the board holding this tile.
    * @return the MouseMap within which this tile resides
    */
   public MouseMap getBoard() {
      return myBoard;
   }
   
   /**
    * Accessor for a particular neighbor tile in any cardinal direction.
    * @param direction which tile to grab
    * @return reference to that tile
    */
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
   
   /**
    * Answers whether this tile is a PATH tile.
    * @return the answer
    */
   public boolean isPath() {
      return myType == CellType.PATH;
   }
   
   /**
    * Answers whether this tile is a TUNNEL tile.
    * @return the answer
    */
   public boolean isTunnel() {
      return myType == CellType.TUNNEL;
   }
   
   /**
    * Clears all entities from this tile's inhabitant list.
    */
   public void clearRegistry() {
      myInhabitants.clear();
      repaint();
   }
   
   /**
    * Returns the Point storing this tile's location on the MouseMap.
    * @return location as Point
    */
   public Point getAddress() {
      return myAddress;
   }
   
   /**
    * Provides the Manhattan Distance from this tile to the specified tile
    * @param theTile tile to be range-found
    * @return Manhattan Distance to theTile
    */
   public int mannDistanceTo(MapTile theTile) {
      int xDist = Math.abs(theTile.getAddress().x - getAddress().x);
      int yDist = Math.abs(theTile.getAddress().y - getAddress().y);
      return xDist + yDist;
   }
   
   /**
    * Accessor for all CellType.PATH tiles cardinally adjacent to this one.
    * @return all neighboring MapTiles of PATH type in a List
    */
   public List<MapTile> getPathNeighbors() {
      return List.of(getNeighbor(Directions.NORTH), getNeighbor(Directions.SOUTH),
            getNeighbor(Directions.EAST),  getNeighbor(Directions.WEST))
            .stream()
            .filter(MapTile::isPath)
            .collect(Collectors.toList());
   }
   
   /**
    * Determines if a PathNode has been installed yet for the current sweep.
    * @return true iff current path node reference != null
    */
   public boolean hasNode() {
      return myPathNode != null;
   }
   
   /**
    * Accessor for this tile's currently-installed PathNode, if any.
    * @return reference to current path node or null
    */
   public PathNode getNode() {
      return myPathNode;
   }
   
   /**
    * Clears the reference to this tile's path node, essentially throwing it away.
    */
   public void resetPathNode() {
      if (hasNode()) myPathNode = null;
   }
   
   /**
    * Installs a new PathNode in this tile for the duration of a given path sweep.
    * @param initialParent node commanding the installation
    * @return the newly created node, so it can be added to openList
    */
   public PathNode installPathNode(PathNode initialParent) {
      myPathNode = new PathNode(this, initialParent);
      return myPathNode;
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
   
   public String toString() {
      StringBuilder ts = new StringBuilder();
      ts.append(String.format("%s Tile at:", myType));
      ts.append(String.format(" (%d, %d) ", myAddress.x, myAddress.y));
      ts.append(myInhabitants.isEmpty()? "[]" : myInhabitants.toString());
      if (hasNode()) ts.append(String.format(" :::  %s", myPathNode.toString()));
      return ts.toString();
   }
}