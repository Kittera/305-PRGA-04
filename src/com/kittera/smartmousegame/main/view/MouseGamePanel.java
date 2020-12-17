package com.kittera.smartmousegame.main.view;

import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;
import com.kittera.smartmousegame.main.model.Cheese;
import com.kittera.smartmousegame.main.model.Directions;
import com.kittera.smartmousegame.main.model.SmartMouseActors;
import com.kittera.smartmousegame.main.model.SmartMouseEntity;
import guiResources.KButton;
import guiResources.KLabel;
import guiResources.KPanel;
import guiResources.KRadioButton;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static com.kittera.smartmousegame.main.view.MouseGamePanel.CONTROLS_HEIGHT;
import static com.kittera.smartmousegame.main.view.MouseGamePanel.MOVE_BUTTON_SIDES;
import static guiResources.KGUIRepo.*;

public class MouseGamePanel extends KPanel {
   private JPanel currentSplash;
   
   protected static final int CONTROLS_HEIGHT = 40;
   protected static final int MOVE_BUTTON_SIDES = 30;
   protected final SmartMouseStateManager GAME_STATE;
   private final GameDisplayPanel boardPanel;
   private final Image VICTORY_IMG;
   private final Image DEFEAT_IMG;
   private final Timer waitForReset = new Timer(30, e -> repaint());
   
   public MouseGamePanel(SmartMouseStateManager stateManager, MouseMap theBoard) {
      setLayout(new BorderLayout());
      setBackground(DARK_PURPLE);
      currentSplash = null;
      boardPanel = new GameDisplayPanel(theBoard);
      GAME_STATE = stateManager;
      GAME_STATE.registerDisplayPanel(this);
      add(new NorthInterfacePanel(GAME_STATE), BorderLayout.NORTH);
      add(boardPanel, BorderLayout.CENTER);
      add(new SouthInterfacePanel(GAME_STATE), BorderLayout.SOUTH);
      addAction("UP",    Directions.NORTH);
      addAction("LEFT",  Directions.WEST);
      addAction("DOWN",  Directions.SOUTH);
      addAction("RIGHT", Directions.EAST);
      addAction("W",     Directions.NORTH);
      addAction("A",     Directions.WEST);
      addAction("S",     Directions.SOUTH);
      addAction("D",     Directions.EAST);
   
      try {
         VICTORY_IMG = ImageIO.read(new File(IMAGEPATH + "Victory.png"));
         DEFEAT_IMG  = ImageIO.read(new File(IMAGEPATH + "GameOver.png"));
      } catch (IOException stop) {
         throw new IllegalArgumentException("Splash sprite not found at: " + IMAGEPATH);
      }
      
   }
   
   public void addAction(String name, Directions dir) {
      MoveAction action = new MoveAction(name, dir);
      
      KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
      InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      inputMap.put(pressedKeyStroke, name);
      this.getActionMap().put(name, action);
   }
   
   public void showSplashScreen() {
      waitForReset.start();
      remove(boardPanel);
      if (GAME_STATE.isVictory())  currentSplash = new SplashPanel(VICTORY_IMG);
      if (GAME_STATE.isGameOver()) currentSplash = new SplashPanel(DEFEAT_IMG);
      add(currentSplash);
      revalidate();
   }
   
   public void hideSplashScreen() {
      waitForReset.stop();
      remove(currentSplash);
      add(boardPanel);
      revalidate();
      repaint();
      currentSplash = null;
   }
   
   
   
   private static class MoveAction extends AbstractAction implements ActionListener {
      
      final Directions direction;
      public MoveAction(String name, Directions dir) {
         super(name);
         direction = dir;
      }
      
      @Override
      public void actionPerformed(ActionEvent e) {
         SmartMouseActors.MOUSE.move(direction);
      }
   }
   
   private static class SplashPanel extends KPanel {
      
      private final Image splash;
      
      public SplashPanel(Image img) {
         splash = img;
         setBackground(Color.BLACK);
      }
   
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         drawSplash(g, splash);
      }
   
      private void drawSplash(Graphics g, Image splash) {
         Graphics2D g2 = (Graphics2D) g;
         int startX =  (getWidth() / 2) - (splash.getWidth(null) / 2);
         int startY = (getHeight() / 2) - (splash.getHeight(null) / 2);
         g2.drawImage(splash, startX, startY, this);
      }
   }
}

///////////////////////////////////////////////////////

class NorthInterfacePanel extends KPanel {
   private final SmartMouseStateManager GAME_STATE;
   protected final SmartMouseEntity theMouse = SmartMouseActors.MOUSE;
   
   public NorthInterfacePanel(SmartMouseStateManager stateManager) {
      GAME_STATE = stateManager;
      int controlSize = CONTROLS_HEIGHT;
      setBackground(DARK_PURPLE);
      setPreferredSize(new Dimension(10, MouseGamePanel.CONTROLS_HEIGHT));
      Dimension buttonSize = new Dimension(70, 24);
   
      JPanel leftPane   = new KPanel(new GridLayout(1,1));
      JPanel middlePane = new KPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
      JPanel rightPane  = new KPanel(new GridLayout(1,2, 20, 0));
   
      JButton bStart = new KButton("Start");
      JButton bStep  = new KButton("Step");
      JButton bStop  = new KButton("Stop");
      JButton bReset = new KButton("Reset");
      bStart.addActionListener(e -> GAME_STATE.startTimer());
      bStep. addActionListener(e -> GAME_STATE.gameStep());
      bStop. addActionListener(e -> GAME_STATE.stopTimer());
      bReset.addActionListener(e -> GAME_STATE.resetGame());
      JButton[] buttons = {bStart, bStop, bStep, bReset};
      for (JButton e : buttons) {
         e.setPreferredSize(buttonSize);
         middlePane.add(e);
      }
      
      //init top right corner
      JLabel livesDisp = new KLabel("");
      JLabel cheeseDisp = new KLabel("");
      livesDisp.setIcon(new ImageIcon(theMouse.getSprite(controlSize, controlSize)));
      livesDisp.setVerticalAlignment(SwingConstants.CENTER);
      livesDisp.setHorizontalAlignment(SwingConstants.CENTER);
      livesDisp.setFont(GEN_FONT);
      cheeseDisp.setIcon(new ImageIcon(Cheese.getCheeseSprite(controlSize)));
      cheeseDisp.setVerticalAlignment(SwingConstants.CENTER);
      cheeseDisp.setHorizontalAlignment(SwingConstants.CENTER);
      cheeseDisp.setFont(GEN_FONT);
      rightPane.add(livesDisp);
      rightPane.add(cheeseDisp);
      
      JLabel timeDisp = new KLabel(" Time Elapsed:  0:00");
      timeDisp.setVerticalAlignment(SwingConstants.CENTER);
      timeDisp.setHorizontalAlignment(SwingConstants.CENTER);
      timeDisp.setFont(GEN_FONT);
      leftPane.add(timeDisp);
      
      GAME_STATE.addReadouts(livesDisp, cheeseDisp, timeDisp);
      
      add(leftPane, BorderLayout.WEST);
      add(middlePane, BorderLayout.CENTER);
      add(rightPane, BorderLayout.EAST);
      
   }
}

///////////////////////////////////////////////////////////

class GameDisplayPanel extends KPanel {
   
   public GameDisplayPanel(MouseMap theBoard) {
      int cellGap = 0;
      int cellSize    = 30;
      int cellCols    = theBoard.getCols();
      int cellRows    = theBoard.getRows();
      int panelWidth  = cellSize * cellCols + cellGap;
      int panelHeight = cellSize * cellRows + cellGap;
      Dimension boardSize = new Dimension(panelWidth, panelHeight);
      
      setBackground(Color.DARK_GRAY);
      setPreferredSize(boardSize);
   
      
      
   
      //populate panels here
      setLayout(new GridLayout(cellRows, cellCols, cellGap ,cellGap));
      for (int row = 0 ; row < cellRows ; row++) {
         for (int col = 0 ; col < cellCols ; col++) {
            add(theBoard.getTileAt(col, row));
         }
      }
   }
   
   
}

//////////////////////////////////////////

class SouthInterfacePanel extends KPanel {
   
   private final SmartMouseStateManager GAME_STATE;
   
   protected final JButton bDown;
   protected final JButton bLeft;
   protected final JButton bRight;
   protected final JButton bUp;
   
   private final JSlider slideFPS;
   private final KRadioButton chbxDebugMode;
   
   private static class MoveBufferPanel extends KPanel {
      public MoveBufferPanel(Dimension size) {
         setPreferredSize(size);
      }
   }
   
   public SouthInterfacePanel(SmartMouseStateManager stateManager) {
      GAME_STATE = stateManager;
      int movePanelWidth  = (5 * MOVE_BUTTON_SIDES);
      int movePanelHeight = (2 * MOVE_BUTTON_SIDES);
      int movePanelGap    = (MOVE_BUTTON_SIDES / 2);
      Dimension moveButtonDim =
            new Dimension(MOVE_BUTTON_SIDES, MOVE_BUTTON_SIDES);
      Dimension bufferGap = new Dimension(movePanelGap, movePanelHeight);
   
      setBackground(DARK_PURPLE);
      setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
      setPreferredSize(new Dimension(10, movePanelHeight));
   
      //instantiate panels
      JPanel debugPanel         = new KPanel();
      JPanel moveControlButtons = new KPanel();
      JPanel moveControlsPanel  = new KPanel();
      JPanel fpsSliderPanel     = new KPanel();
   
      //set up debug mode radio button
      chbxDebugMode = new KRadioButton("Debug Mode");
      chbxDebugMode.addActionListener(this::debugToggle);
      debugPanel.add(chbxDebugMode);
   
      //set up move buttons and panel
   
      bUp = new KButton("^"); //UP MOVEMENT BUTTON
      bUp.setPreferredSize(moveButtonDim);
      bUp.addActionListener(this::upButton);
   
      bDown = new KButton("v"); //DOWN MOVEMENT BUTTON
      bDown.setPreferredSize(moveButtonDim);
      bDown.addActionListener(this::downButton);
   
      bLeft = new KButton("<"); //LEFT MOVEMENT BUTTON
      bLeft.setPreferredSize(moveButtonDim);
      bLeft.addActionListener(this::leftButton);
   
      bRight = new KButton(">"); //RIGHT MOVEMENT BUTTON
      bRight.setPreferredSize(moveButtonDim);
      bRight.addActionListener(this::riteButton);
      
      moveControlButtons.setLayout(new GridLayout(1, 4, movePanelGap - 10, movePanelGap));
      moveControlButtons.setFocusable(false);
      moveControlButtons.setBackground(DARK_PURPLE);
      moveControlButtons.add(bLeft);
      moveControlButtons.add(bUp);
      moveControlButtons.add(bDown);
      moveControlButtons.add(bRight);
   
      moveControlsPanel.setPreferredSize(new Dimension(movePanelWidth, movePanelHeight));
      moveControlsPanel.setFocusable(false);
      moveControlsPanel.add(new MoveBufferPanel(bufferGap), BorderLayout.WEST);
      moveControlsPanel.add(moveControlButtons);
      moveControlsPanel.add(new MoveBufferPanel(bufferGap), BorderLayout.EAST);
      
      JLabel fpsLabel = new KLabel("FPS: ");
      fpsLabel.setVerticalAlignment(SwingConstants.CENTER);
      fpsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
      fpsLabel.setOpaque(true);
      fpsLabel.setBackground(DARK_PURPLE);
      slideFPS = new JSlider(1, 10, 5);
      slideFPS.addChangeListener(this::rateChanged);
      slideFPS.setMajorTickSpacing(1);
      slideFPS.setMinorTickSpacing(1);
      slideFPS.setSnapToTicks(true);
      slideFPS.setFocusable(false);
      slideFPS.setPaintTicks(true);
      slideFPS.setPaintLabels(true);
      slideFPS.setBackground(DARK_PURPLE);
      slideFPS.setForeground(Color.WHITE);
      
      fpsSliderPanel.add(fpsLabel, BorderLayout.WEST);
      fpsSliderPanel.add(slideFPS);
      fpsSliderPanel.setFocusable(false);
      
      add(fpsSliderPanel);
      add(moveControlButtons);
      add(debugPanel);
   }
   
   private void debugToggle(ActionEvent actionEvent) {
      GAME_STATE.setDebugMode(chbxDebugMode.isSelected());
      if (GAME_STATE.getDebugMode()) System.out.println("Debug Button Toggled.");
   }
   
   private void upButton(ActionEvent e) {
      SmartMouseActors.MOUSE.move(Directions.NORTH);
      if (GAME_STATE.getDebugMode()) System.out.println("Up Button Clicked.");
   }
   
   private void downButton(ActionEvent e) {
      SmartMouseActors.MOUSE.move(Directions.SOUTH);
      if (GAME_STATE.getDebugMode()) System.out.println("Down Button Clicked.");
   }
   
   private void leftButton(ActionEvent e) {
      SmartMouseActors.MOUSE.move(Directions.WEST);
      if (GAME_STATE.getDebugMode()) System.out.println("Left Button Clicked.");
   }
   
   private void riteButton(ActionEvent e) {
      SmartMouseActors.MOUSE.move(Directions.EAST);
      if (GAME_STATE.getDebugMode()) System.out.println("Right Button Clicked.");
   }
   
   public void rateChanged(ChangeEvent e) {
      int val = slideFPS.getValue();
      int newMS = 1000 / val;
      GAME_STATE.intervalChanged(newMS);
      
      if (GAME_STATE.getDebugMode()) {
         System.out.println("Slider is now " + slideFPS.getValue());
      }
   }
}