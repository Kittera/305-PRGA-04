package com.kittera.smartmousegame.main.controller;

import com.kittera.smartmousegame.main.model.Cheese;
import com.kittera.smartmousegame.main.model.MapTile;
import com.kittera.smartmousegame.main.model.MouseMap;
import com.kittera.smartmousegame.main.model.SmartMouseActors;
import com.kittera.smartmousegame.main.view.MouseGamePanel;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.*;

public class SmartMouseStateManager {
   
   private final Timer ticker;
   private final Timer elapsed;
   private static JLabel myCheeseDisplay;
   private static JLabel myLivesDisplay;
   private static JLabel myTimeDisplay;
   private MouseGamePanel myDisplayPanel;
   private final List<MapTile> cheeseTiles;
   
   private boolean isInDebugMode;
   private boolean started;
   private int cheeseCount;
   private int lifeCount;
   private int secElapsed;
   private MouseMap theBoard;
   
   public SmartMouseStateManager() {
      isInDebugMode = false;
      lifeCount     = 3;
      cheeseCount   = 0;
      cheeseTiles = new ArrayList<>();
      
      ticker = new Timer(200, SmartMouseActors::gameStep);
      ticker.setInitialDelay(10);
      elapsed = new Timer(1000, this::tick);
   }
   
   public void addCheese(MapTile tile) {
      new Cheese(tile, this);
      cheeseTiles.add(tile);
      cheeseCount++;
   }
   
   public void addReadouts(JLabel lives, JLabel cheese, JLabel time) {
      myCheeseDisplay = cheese;
      myLivesDisplay  = lives;
      myTimeDisplay   = time;
      resetLabels();
   }
   
   public void cheeseEaten() {
      if (cheeseCount > 0) {
         cheeseCount--;
         myCheeseDisplay.setText(" " + cheeseCount + " ");
      } else youWin();
   }
   
   public void gameStart() {
      started = true;
      elapsed.start();
      secElapsed = 0;
      if (isInDebugMode) System.out.println("Game timer Started.");
   }
   
   public boolean getDebugMode() {
      return isInDebugMode;
   }
   
   public MouseMap getBoard() {
      return theBoard;
   }
   
   public void intervalChanged(int msPerFrame) {
      ticker.setDelay(msPerFrame);
   }
   
   public void mousePounced() {
      elapsed.stop();
      ticker.stop();
      lifeCount--;
      myLivesDisplay.setText(": " + lifeCount);
      if (lifeCount < 1) gameOver();
   }
   
   public void registerBoard(MouseMap board) {
      theBoard = board;
   }
   
   public void registerDisplayPanel(MouseGamePanel mouseGamePanel) {
      myDisplayPanel = mouseGamePanel;
   }
   
   public void resetGame() {
      theBoard.clearBoard();
      lifeCount = 3;
      resetCheese();
      resetLabels();
      SmartMouseActors.reset();
      myDisplayPanel.repaint();
   }
   
   private void resetCheese() {
      cheeseCount = 0;
      List<MapTile> temp = new LinkedList<>(cheeseTiles);
      cheeseTiles.clear();
      for (MapTile tile : temp) addCheese(tile);
   }
   
   public void setDebugMode(boolean foo) {
      isInDebugMode = foo;
   }
   
   public void startTimer() {
      ticker.start();
      if (!started) gameStart();
      if (isInDebugMode) System.out.println("Ticker Timer Started.");
   }
   
   public void stopTimer() {
      ticker .stop();
      elapsed.stop();
      if (isInDebugMode) System.out.println("Ticker Timer Stopped.");
   }
   
   public void tick(@SuppressWarnings("unused") ActionEvent notUsed) {
      secElapsed++;
      int minutes = secElapsed / 60;
      int seconds = secElapsed % 60;
      String secondString = (seconds < 10 ? "0" : "" ) + seconds;
      String time = String.format(" Time Elapsed:  %d:%s", minutes, secondString);
      myTimeDisplay.setText(time);
   }
   
   private void resetLabels() {
      myCheeseDisplay.setText(" " + cheeseCount + " ");
      myLivesDisplay.setText(" " + lifeCount);
      myTimeDisplay.setText(" Time Elapsed:  0:00");
   }
   
   private void youWin() {
      myCheeseDisplay.setText(" " + cheeseCount + " ");
      //TODO
   }
   
   private void gameOver() {
      //TODO
   }
}