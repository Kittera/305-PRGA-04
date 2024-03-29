package com.kittera.smartmousegame.main.view;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;
import com.kittera.smartmousegame.main.model.*;

import static com.kittera.smartmousegame.main.model.SmartMouseActors.CAT_1;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.CAT_2;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.CAT_3;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.CAT_4;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.CAT_5;
import static com.kittera.smartmousegame.main.model.SmartMouseActors.MOUSE;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Acts as the container for all MapTiles in the SMart Mouse Game.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class MouseMap {
   private static MapTile[][] mapTileArray;
   private final Scanner fileScan;
   private final SmartMouseStateManager stateMgr;
   
   /**
    * Creates a fresh MouseMap, ready to be populated.
    * @param inFile file to be read for map generation
    * @param mgr manager
    */
   public MouseMap(File inFile, SmartMouseStateManager mgr) {
      fileScan = scan(inFile);
      stateMgr = mgr;
      stateMgr.registerBoard(this);
      start();
   }
   
   /**
    * Clears all ENTITIES from the MouseMap's tiles.
    */
   public void clearBoard() {
      for (MapTile[] row : mapTileArray) {
         for (MapTile tile : row)
            tile.clearRegistry();
      }
   }
   
   /**
    * Provides a List of all MapTiles on this MouseMap.
    * @return List of all tiles
    */
   public ArrayList<MapTile> getTiles() {
      ArrayList<MapTile> allTiles = new ArrayList<>();
      for (MapTile[] row : mapTileArray) {
         allTiles.addAll(Arrays.asList(row));
      }
      return allTiles;
   }
   
   /**
    * Accessor for board width.
    * @return board width in columns
    */
   public int getCols() {
      return mapTileArray[0].length;
   }
   
   /**
    * Accessor for board height.
    * @return board height in rows
    */
   public int getRows() {
      return mapTileArray.length;
   }
   
   /**
    * Accessor for a particular tile on the MouseMap
    * @param x x coordinate of desired tile
    * @param y y coordinate of desired tile
    * @return desire tile
    */
   public MapTile getTileAt(int x, int y) {
      return mapTileArray[y][x];
   }
   
   protected void start() {
      System.out.println("Loading map...");
      parseBoardSize(fileScan);
      fillMapArray(fileScan);
      parseEntities(fileScan);
   }
   
   private Scanner scan(File file) {
      try {
         return new Scanner(file);
      } catch (FileNotFoundException notThere) {
         throw new IllegalArgumentException("Filename specified wouldn't be found.");
      }
   }
   
   private void fillMapArray(Scanner fileScan) {
      //capture loop control value
      int colMax = mapTileArray[0].length;
      int rowMax = mapTileArray.length;
      
      //outer loop to process each row
      //row = 0 -> rowMax, row++
      for (int row = 0; row < rowMax; row++) {
         char[] curRowChars;
         
         try { //to capture next line of tile characters
            curRowChars = fileScan.nextLine().toCharArray();
         } catch (NoSuchElementException ranOut) {
            throw new IllegalArgumentException("Reached end of file while parsing. code 1");
         }
         
         if (curRowChars.length < colMax) //this means the array contains too few elements
            throw new IllegalArgumentException("Encountered an incomplete character row.");
         
         //now process current row into MapCells
         //col = 0 -> colMax, col++
         for (int col = 0; col < colMax; col++) {
            var theType = CellType.translateChar(curRowChars[col]);
            if (theType == CellType.ERROR) { // if error, stop parsing
               throw new IllegalArgumentException("Invalid tile character found.");
            }
            mapTileArray[row][col] = new MapTile(theType, col, row, this);
         }
      }
   }
   
   private void parseBoardSize(Scanner fileScan) {
      String sizeLine;
      int boardCols;
      int boardRows;
      
      try { //to grab next line then
         sizeLine = fileScan.nextLine(); //grab whole line
         String[] tokens = sizeLine.split(","); //split around commas
         boardRows = Integer.parseInt(tokens[0]);//take second token as # of rows(y)
         boardCols = Integer.parseInt(tokens[1]);//take first token as # of columns(x)
         mapTileArray = new MapTile[boardRows][boardCols];
      } catch (NumberFormatException e) { //catch exception if parseInt() fails
         throw new IllegalArgumentException("Board size format incorrect. Halted.");
      } catch (NoSuchElementException e) { //catch exception if nextLine() fails
         throw new IllegalArgumentException("Input file is empty?");
      }
      
      //enforce invariant
      if (boardCols < 15 || boardCols > 30)
         throw new IllegalArgumentException(boardCols + " columns found. Valid range: 15-30");
      if (boardRows < 10 || boardRows > 20)
         throw new IllegalArgumentException(boardRows + " rows found. Valid range: 10-30");
   }
   
   private void parseEntities(Scanner fileScan) {
      while (fileScan.hasNextLine()) {
         String[] tokens = fileScan.nextLine().split(" ");
         if (tokens[0].contains("//")) continue;
         try {
            String entityType = tokens[0];
            int xPos = Integer.parseInt(tokens[1]);
            int yPos = Integer.parseInt(tokens[2]);
            spawnEntity(entityType, getTileAt(xPos, yPos));
         } catch (ArrayIndexOutOfBoundsException why) {
            throw new IllegalArgumentException("Encountered an incomplete entity specification.");
         } catch (NumberFormatException nan) {
            throw new IllegalArgumentException("Couldn't read an entity's coordinates.");
         }
      }
   }
   
   private void spawnEntity(String entityType, MapTile tile) {
      switch (entityType) {
         case "C" -> stateMgr.addCheese(tile);
         case "M" -> MOUSE = new SmartMouse(tile, stateMgr);
         case "1" -> CAT_1 = new RandomCat(tile, stateMgr);
         case "2" -> CAT_2 = new ClockwiseCat(tile, stateMgr);
         case "3" -> CAT_3 = new NorthSouthCat(tile, stateMgr);
         case "4" -> CAT_4 = new EastWestCat(tile, stateMgr);
         case "5" -> CAT_5 = new SmartCat(tile, stateMgr);
      }
   }
}