package com.kittera.smartmousegame.main.model;

import java.awt.*;
import java.util.Objects;

import static com.kittera.smartmousegame.main.controller.SmartCatPathFinder.STEP_SIZE;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.MOUSE;

public class PathNode implements Comparable<PathNode> {
   
   private int mySourceCost;       //g-cost
   private PathNode myPathParent;  //node this tile was last explored FROM
   
   private final short myDestCost; //h-cost
   private final MapTile myTile;
   
   public PathNode(MapTile theTile, PathNode initialParentNode) {
      myTile = theTile;
      //myDestCost = (short) Math.round(myTile.euclidDistanceTo(MOUSE.getTile()));
      myDestCost = (short) myTile.mannDistanceTo(MOUSE.getTile());
      
      if (Objects.isNull(initialParentNode)) {
         myPathParent = null;
         mySourceCost = 0;
      } else {
         myPathParent = initialParentNode;
         mySourceCost = STEP_SIZE + initialParentNode.mySourceCost;
      }
   }
   
   @Override
   public int compareTo(PathNode o) {
      int fCostDiff = getTotalCost() - o.getTotalCost();
      return (fCostDiff == 0)? (getDestCost() - o.getDestCost()) : (fCostDiff);
   }
   
   public  MapTile getTile() {
      return myTile;
   }
   
   public PathNode getParent() {
      return myPathParent;
   }
   
   public int  getDestCost() { return myDestCost; }
   
   public int  getTotalCost() {
      return mySourceCost + myDestCost;
   }
   
   public int  getSourceCost() {
      return mySourceCost;
   }
   
   public void offerNewParent(PathNode newParent) {
      if (newParent.hasBetterPath(mySourceCost)) {
         mySourceCost = STEP_SIZE + newParent.mySourceCost;
         myPathParent = newParent;
      }
   }
   
   private boolean hasBetterPath(int neighborGCost) {
      if (isStartNode()) return true;
      return mySourceCost + STEP_SIZE < neighborGCost;
   }
   
   private boolean isStartNode() {
      return myPathParent == null;
   }
   
   public String toString() {
      StringBuilder pnts = new StringBuilder();
      Point loc = myTile.getAddress();
      pnts.append(String.format("[%d,%d] {", loc.x, loc.y));
      pnts.append(String.format("G:%d ", getSourceCost()));
      pnts.append(String.format("H:%d ", getDestCost()));
      pnts.append(String.format("F:%d}", getTotalCost()));
      pnts.append(String.format(" Parent: %s", myPathParent));
      return pnts.toString();
   }
}
