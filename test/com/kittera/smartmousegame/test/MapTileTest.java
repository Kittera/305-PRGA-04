package com.kittera.smartmousegame.test;

import com.kittera.smartmousegame.main.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTileTest {
   static SmartMouse testMouse;
   static AbstractCat testCat;
   static MapTile tile1;
   static MapTile tile2;
   static MapTile tile3;
   
   
   @BeforeAll
   static void setUp() {
      tile1 = new MapTile(CellType.PATH, 4, 8, null);
      tile2 = new MapTile(CellType.TUNNEL, 10, 16, null);
      tile3 = new MapTile(CellType.TUNNEL, 16, 16, null);
      testMouse = new SmartMouse(tile2, null);
      testCat = new SmartCat(tile1, null);
   }
   
   @Test
   void register() {
      assertFalse(tile2.register(testCat));
      assertTrue(tile3.register(testMouse));
   }
   
   @Test
   void isPath() {
      assertFalse(tile2.isPath());
      assertTrue(tile1.isPath());
   }
   
   @Test
   void isTunnel() {
      assertFalse(tile1.isTunnel());
      assertTrue(tile2.isTunnel());
   }
   
   @Test
   void mannDistanceTo() {
      assertEquals(20, tile1.mannDistanceTo(tile3));
      assertEquals(6, tile2.mannDistanceTo(tile3));
   }
   
   @Test
   void resetPathNode() {
      tile1.resetPathNode();
      assertNull(tile1.getNode());
   }
}