package com.kittera.smartmousegame.main.controller;

import com.kittera.smartmousegame.main.model.AbstractCat;
import com.kittera.smartmousegame.main.model.Cheese;
import com.kittera.smartmousegame.main.model.MapTile;
import com.kittera.smartmousegame.main.view.MouseMap;
import com.kittera.smartmousegame.main.model.SmartMouseActors;
import com.kittera.smartmousegame.main.view.MouseGamePanel;

import static com.kittera.smartmousegame.main.model.SmartMouseActors.*;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Manages the state of this instance of the Smart Mouse Game.
 * @author Kittera Ashleigh McCloud
 * @version 0.9
 */
public class SmartMouseStateManager {
   
   private final Timer ticker;
   private final Timer elapsed;
   private final Timer catPathFinding;
   
   private static JLabel myCheeseDisplay;
   private static JLabel myLivesDisplay;
   private static JLabel myTimeDisplay;
   private MouseGamePanel myDisplayPanel;
   private final List<MapTile> cheeseTiles;
   
   private boolean isInDebugMode;
   private boolean started;
   private boolean victory;
   private boolean gameOver;
   
   private int cheeseCount;
   private int lifeCount;
   private int secElapsed;
   private MouseMap theBoard;
   private int catFiveStepsDelay;
   
   /**
    * Creates a fresh state manager.
    */
   public SmartMouseStateManager() {
      isInDebugMode = false;
      lifeCount = 3;
      catFiveStepsDelay = 0;
      cheeseCount = 0;
      cheeseTiles = new ArrayList<>();
      gameOver = false;
      victory = false;
      started = false;
      
      ticker = new Timer(200, e -> gameStep());
      ticker.setInitialDelay(10);
      elapsed = new Timer(1000, e -> tick());
      catPathFinding = new Timer(200, e -> pingCatPathFinder());
   }
   
   /**
    * Updates the cheese counter.
    * @param tile where the cheese was spawned
    */
   public void addCheese(MapTile tile) {
      new Cheese(tile, this);
      cheeseTiles.add(tile);
      cheeseCount++;
   }
   
   /**
    * Brings in references to display elements
    * @param lives  lives counter
    * @param cheese cheese counter
    * @param time   timer display
    */
   public void addReadouts(JLabel lives, JLabel cheese, JLabel time) {
      myCheeseDisplay = cheese;
      myLivesDisplay = lives;
      myTimeDisplay = time;
      resetLabels();
   }
   
   /**
    * Called when a cheese is eaten by the mouse.
    */
   public void cheeseEaten() {
      if (cheeseCount > 0) {
         cheeseCount--;
         updateCheeseDisplay();
      }
      if (cheeseCount == 0) youWin();
   }
   
   /**
    * Called for the first occurrence of pressing start since last reset.
    */
   public void gameStart() {
      started = true;
      secElapsed = 0;
      elapsed.start();
      if (CAT_5 != null) catPathFinding.start();
      if (isInDebugMode) System.out.println("Game timer Started.");
   }
   
   /**
    * Cycles the game's motion mechanics.
    */
   public void gameStep() {
      SmartMouseActors.getCats()
            .stream()
            .filter(Objects::nonNull)
            .forEach(AbstractCat::move);
   }
   
   /**
    * Answers whether debug mode is on.
    * @return the answer
    */
   public boolean getDebugMode() {
      return isInDebugMode;
   }
   
   /**
    * Accessor for the MouseMap this SmartMouseStateManager is managing.
    * @return the board
    */
   public MouseMap getBoard() {
      return theBoard;
   }
   
   /**
    * Called by the FPS slider to update the delay of the game step timer.
    * @param msPerFrame new delay for the timer
    */
   public void intervalChanged(int msPerFrame) {
      ticker.setDelay(msPerFrame);
   }
   
   /**
    * Answers whether the game is in the defeat state.
    * @return the answer
    */
   public boolean isGameOver() {
      return gameOver;
   }
   
   /**
    * Answers whether the game is in victory state.
    * @return the answer
    */
   public boolean isVictory() {
      return victory;
   }
   
   /**
    * Called when the mouse get pounced and loses a life.
    */
   public void mousePounced(int newCount) {
      lifeCount = newCount;
      myLivesDisplay.setText(": " + lifeCount);
      if (lifeCount < 1) gameOver();
   }
   
   /**
    * Attaches this state manager to a MouseMap game-board.
    * @param board the MouseMap to monitor
    */
   public void registerBoard(MouseMap board) {
      theBoard = board;
   }
   
   /**
    * adds a reference to the GameDisplayPanel in View, in order to command splash screens
    * and stat display updates.
    * @param mouseGamePanel the display panel for the current game
    */
   public void registerDisplayPanel(MouseGamePanel mouseGamePanel) {
      myDisplayPanel = mouseGamePanel;
   }
   
   /**
    * Resets the game to the initial state.
    */
   public void resetGame() {
      if (isVictory() || isGameOver()) myDisplayPanel.hideSplashScreen();
      secElapsed = 0;
      started  = false;
      victory  = false;
      gameOver = false;
      lifeCount = 3;
      theBoard.clearBoard();
      resetCheese();
      resetLabels();
      SmartMouseActors.reset();
      myDisplayPanel.repaint();
   }
   
   /**
    * Toggles debug mode on or off.
    * @param foo on or off?
    */
   public void setDebugMode(boolean foo) {
      isInDebugMode = foo;
   }
   
   /**
    * Starts the entities moving.
    */
   public void startTimer() {
      ticker.start();
      if (!started) gameStart();
      if (isVictory() || isGameOver()) resetGame();
      if (isInDebugMode) System.out.println("Ticker Timer Started.");
   }
   
   /**
    * Stops entity movement.
    */
   public void stopTimer() {
      ticker.stop();
      if (isInDebugMode) System.out.println("Ticker Timer Stopped.");
   }
   
   /**
    * Adds one second to the time display.
    */
   private void tick() {
      secElapsed++;
      int minutes = secElapsed / 60;
      int seconds = secElapsed % 60;
      String secondString = (seconds < 10 ? "0" : "") + seconds;
      String time = String.format(" Time Elapsed:  %d:%s", minutes, secondString);
      myTimeDisplay.setText(time);
   }
   
   /**
    * called when the mouse's life counter reaches 0.
    */
   private void gameOver() {
      elapsed.stop();
      ticker.stop();
      catPathFinding.stop();
      gameOver = true;
      myDisplayPanel.showSplashScreen();
   }
   
   /**
    * Initiates another pathfinding sweep.
    */
   private void pingCatPathFinder() {
      if (catFiveStepsDelay > 0) catFiveStepsDelay--;
      else if (SmartMouseActors.MOUSE.getTile().isPath() && CAT_5 != null) {
         CAT_5.refreshPath();
         catFiveStepsDelay = 5;
      }
   }
   
   private void resetCheese() {
      cheeseCount = 0;
      List<MapTile> temp = new LinkedList<>(cheeseTiles);
      cheeseTiles.clear();
      for (MapTile tile : temp) addCheese(tile);
   }
   
   private void resetLabels() {
      myCheeseDisplay.setText(" " + cheeseCount + " ");
      myLivesDisplay.setText(" " + lifeCount);
      myTimeDisplay.setText(" Time Elapsed:  0:00");
   }
   
   private void updateCheeseDisplay() {
      myCheeseDisplay.setText(" " + cheeseCount + " ");
   }
   
   private void youWin() {
      ticker.stop();
      elapsed.stop();
      myCheeseDisplay.setText(" " + cheeseCount + " ");
      victory = true;
      myDisplayPanel.showSplashScreen();
   }
}