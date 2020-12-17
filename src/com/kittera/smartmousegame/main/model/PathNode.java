package com.kittera.smartmousegame.main.model;

import java.awt.Point;
import java.util.Objects;

import static com.kittera.smartmousegame.main.controller.SmartCatPathFinder.STEP_SIZE;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.MOUSE;

/**
 * Class used to create PAth Nodes for use in the SmartCat A* Pathfinding Algorithm.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class PathNode implements Comparable<PathNode> {
   
   private int mySourceCost;       //g-cost or source cost; length of shortest known route
   private PathNode myPathParent;  //node this tile was last explored FROM
   
   private final short myDestCost; //h-cost never changes for a given pathfinding sweep
   private final MapTile myTile;   //which tile this node is associated with
   
   /**
    * Creates a new PathNode on the given tile and with an initial parent of the exploring
    * node that reached this one.
    * @param theTile tile to be installed in
    * @param initialParentNode the node whose exploration caused this to be called
    */
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
   
   /**
    * Compares two PathNodes first by total cost then by destination cost, ascending.
    * @param otherNode node being compared
    * @return result of subtraction rule
    */
   @Override
   public int compareTo(PathNode otherNode) {
      int fCostDiff = getTotalCost() - otherNode.getTotalCost();
      return (fCostDiff == 0)? (getDestCost() - otherNode.getDestCost()) : (fCostDiff);
   }
   
   /**
    * Accessor for the tile this PathNode is installed on.
    * @return  reference to hosting MapTile
    */
   public  MapTile getTile() {
      return myTile;
   }
   
   /**
    * Accessor for this PathNode's current parent node.
    * @return reference to current parent node
    */
   public PathNode getParent() {
      return myPathParent;
   }
   
   /**
    * Accessor for the pre-calculated H-cost or destination cost; the guess for how far
    * away any node is from the target.
    * @return  value of myDestCost field
    */
   public int  getDestCost() { return myDestCost; }
   
   /**
    * Accessor for this node's latest total cost estimate.
    * @return  value in myTotalCost field
    */
   public int  getTotalCost() {
      return mySourceCost + myDestCost;
   }
   
   /**
    * Accessor for the cost of thew current shortest known path to this node.
    * @return  value in mySourceCost field
    */
   public int  getSourceCost() {
      return mySourceCost;
   }
   
   /**
    * Offers this node a new parent node. This is also the call which "explores" one
    * node from another. This method compares the current best-known source cost of this
    * node to the sum of the potential parent node's source cost and the step size.
    * If the potential parent took a shorter path to get to this one, it becomes the
    * new parent.
    * @param newParent potential new parent node
    */
   public void offerNewParent(PathNode newParent) {
      if (newParent.hasBetterPath(mySourceCost)) {
         mySourceCost = STEP_SIZE + newParent.getSourceCost();
         myPathParent = newParent;
      }
   }
   
   /**
    * Compares the current source-cost of the node being explored to the exploring node.
    * @param neighborGCost source cost of the node being polled
    * @return true if this node has a better source cost which is true for a better path
    */
   private boolean hasBetterPath(int neighborGCost) {
      if (isStartNode()) return true;
      return mySourceCost + STEP_SIZE < neighborGCost;
   }
   
   /**
    * Used to ascertain whether this is the start node.
    * @return true iff parent of this node is null
    */
   private boolean isStartNode() {
      return myPathParent == null;
   }
   
   /**
    * Creates a String for debugging including cost information and location
    * @return built String
    */
   public String toString() {
      StringBuilder pathNodeToString = new StringBuilder();
      Point loc = myTile.getAddress();
      pathNodeToString.append(String.format("[%d,%d] {", loc.x, loc.y));
      pathNodeToString.append(String.format("G:%d ", getSourceCost()));
      pathNodeToString.append(String.format("H:%d ", getDestCost()));
      pathNodeToString.append(String.format("F:%d}", getTotalCost()));
      pathNodeToString.append(String.format(" Parent: %s", myPathParent));
      return pathNodeToString.toString();
   }
}