package com.kittera.smartmousegame.main.controller;

import com.kittera.smartmousegame.main.model.MapTile;
import com.kittera.smartmousegame.main.model.PathNode;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.MOUSE;

import java.util.*;

public class SmartCatPathFinder {
   
   private final Queue<PathNode> openList   = new PriorityQueue<>(128);
   private final List<MapTile> exploredList = new ArrayList<>(256);
   
   public static final int STEP_SIZE = 10;
   
   public SmartCatPathFinder() {}
   
   public Stack<MapTile> findNewPath(MapTile startTile) {
      boolean pathFound = false;
      MapTile mouseTile = MOUSE.getTile();
      PathNode endNode = null;
   
      if (!startTile.hasNode()) startTile.installPathNode(null);
      openList.offer(startTile.getNode());
      
      while (!openList.isEmpty() && !pathFound) {
         PathNode currentNode = openList.poll();
         endNode = currentNode;
         
         if (currentNode.getTile() == mouseTile) {
            pathFound = true;
            continue;
         }
         
         List<MapTile> currentNeighborTiles = currentNode.getTile().getPathNeighbors();
   
         //This stream will create path nodes for tiles not yet given one
         currentNeighborTiles.stream()           //enter stream mode
               .filter (e -> !e.hasNode()) //filter to tiles not yet given a new Node
               .map    (e -> e.installPathNode(currentNode)) //make new PathNode for each
               .forEach(openList::offer); //add those nodes to openList
         
         //this stream performs exploration from this node.
         currentNeighborTiles.parallelStream()
               .filter (e -> !exploredList.contains(e))
               .map    (MapTile::getNode)
               .forEach(e -> e.offerNewParent(currentNode));
         
         exploredList.add(currentNode.getTile());
      }
      openList.clear();
      exploredList.clear();
      return pathTrace(endNode);
   }
   
   private Stack<MapTile> pathTrace(PathNode selectedNode) {
      Stack<MapTile> pathStack = rPathTrace(new Stack<>(), selectedNode);
      selectedNode.getTile().getBoard().resetNodes();
      return pathStack;
   }
   
   private Stack<MapTile> rPathTrace(Stack<MapTile> pathList, PathNode nextNode){
      if (nextNode == null) return pathList;
      else {
         pathList.push(nextNode.getTile());
         return rPathTrace(pathList, nextNode.getParent());
      }
   }
}
