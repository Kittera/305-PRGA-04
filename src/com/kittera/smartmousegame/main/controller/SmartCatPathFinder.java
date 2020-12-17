package com.kittera.smartmousegame.main.controller;

import com.kittera.smartmousegame.main.model.MapTile;
import com.kittera.smartmousegame.main.model.PathNode;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.MOUSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Class for conducting the A* Pathfinding Algorithm for SmartMouseGame's SmartCat(s).
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class SmartCatPathFinder {
   
   private final Queue<PathNode> openList   = new PriorityBlockingQueue<>(128);
   private final List<MapTile> exploredList = new ArrayList<>(256);
   
   /**
    * Step cost used for moving in cardinal directions on a 2D grid.
    */
   public static final int STEP_SIZE = 10;
   
   /**
    * Primary driving loop for A-Star pathfinding algorithm.
    * @param startTile tile to install starting PathNode on for exploration
    * @return a Stack<MapTile> for use by a SmartCat
    */
   public Stack<MapTile> findNewPath(MapTile startTile) {
      MapTile mouseTile = MOUSE.getTile();
      PathNode endNode = null;
   
      if (!startTile.hasNode()) startTile.installPathNode(null);
      openList.offer(startTile.getNode());
      
      while (!openList.isEmpty()) {
         PathNode currentNode = openList.poll(); //ALSO REMOVES FROM QUEUE!
         endNode = currentNode;
         
         if (endNode.getTile() == mouseTile) break;
         
         //acquire list of all neighboring tiles which at this point may or may not have nodes installed
         List<MapTile> currentNeighborTiles = currentNode.getTile().getPathNeighbors();
   
         //This stream will create path nodes for neighboring tiles not yet given one
         currentNeighborTiles.parallelStream()           //enter stream mode
               .filter (e -> !e.hasNode()) //filter to tiles not yet given a new Node
               .map    (e -> e.installPathNode(currentNode)) //make new PathNode for each
               .forEach(openList::offer); //add those nodes to openList
         
         //this stream performs exploration from this node.
         currentNeighborTiles.parallelStream() //enter multithreaded stream
               .filter (e -> !exploredList.contains(e)) //filter to tiles not yet explored FROM
               .map    (MapTile::getNode) //change focus to the tiles' associated node
               .forEach(e -> e.offerNewParent(currentNode)); //explore node from currentNode
         
         exploredList.add(currentNode.getTile());
      }
      openList.clear(); //flush openList for next round
      exploredList.clear(); //flush exploredList for next round
      return pathTrace(endNode); //trace parent pointers into stack
   }
   
   /**
    * Begins path trace.
    * @param selectedNode last node selected as best path
    * @return built-up Stack
    */
   private Stack<MapTile> pathTrace(PathNode selectedNode) {
      Stack<MapTile> pathStack = rPathTrace(new Stack<>(), selectedNode);
      clearMapTileNodes(selectedNode);
      return pathStack;
   }
   
   /**
    * Recursively pushes MapTiles onto a Stack by tracing PathNode parents back to start.
    * @param pathList Stack to be built
    * @param nextNode next Node to be pushed
    * @return pathList once everything that can be has been added
    */
   private Stack<MapTile> rPathTrace(Stack<MapTile> pathList, PathNode nextNode){
      if (nextNode == null) return pathList;
      else {
         pathList.push(nextNode.getTile());
         return rPathTrace(pathList, nextNode.getParent());
      }
   }
   
   /**
    * Clears all generated PathNodes from the map, and thus memory.
    * @param endNode any tile will do but we pass in pathNode to get access to the board
    */
   private void clearMapTileNodes(PathNode endNode) {
      endNode.getTile()
            .getBoard()
            .getTiles()
            .parallelStream()
            .forEach(MapTile::resetPathNode);
   }
}