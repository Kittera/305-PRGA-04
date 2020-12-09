package com.kittera.smartmousegame.main.view;

import com.kittera.smartmousegame.guiResources.KButton;
import com.kittera.smartmousegame.guiResources.KLabel;
import com.kittera.smartmousegame.guiResources.KPanel;
import com.kittera.smartmousegame.guiResources.KRadioButton;
import com.kittera.smartmousegame.main.controller.SmartMouseStateManager;
import com.kittera.smartmousegame.main.model.Directions;
import com.kittera.smartmousegame.main.model.SmartMouseEntities;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.kittera.smartmousegame.guiResources.KGUIRepo.*;
import static com.kittera.smartmousegame.main.view.MouseGamePanel.MOVE_BUTTON_SIDELENGTH;

public class MouseGamePanel extends KPanel implements ActionListener, ChangeListener {
   
   protected static final int CONTROLS_HEIGHT = 40;
   protected static final int MOVE_BUTTON_SIDELENGTH = 30;
   protected final SmartMouseStateManager GAME_STATE;
   
   
   
   public MouseGamePanel(SmartMouseStateManager stateManager) {
      setLayout(new BorderLayout());
      GAME_STATE = stateManager;
      setPreferredSize(new Dimension(625, 475));
      
      
      
      add(new NorthInterfacePanel(GAME_STATE), BorderLayout.NORTH);
      add(new GameDisplayPanel(GAME_STATE), BorderLayout.CENTER);
      add(new SouthInterfacePanel(GAME_STATE), BorderLayout.SOUTH);
      
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
   
   }
   
   @Override
   public void stateChanged(ChangeEvent e) {
   
   }
}


///////////////////////////////////////////////////////

class NorthInterfacePanel extends KPanel {
   private final JButton bStart;
   private final JButton bStep;
   private final JButton bStop;
   private final JButton bReset;
   private final SmartMouseStateManager GAME_STATE;
   
   public NorthInterfacePanel(SmartMouseStateManager stateManager) {
      GAME_STATE = stateManager;
      setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
      setPreferredSize(new Dimension(10, MouseGamePanel.CONTROLS_HEIGHT));
      
      bStart = new KButton("Start");
      bStep  = new KButton("Step");
      bStop  = new KButton("Stop");
      bReset = new KButton("Reset");
      bStart.addActionListener(e -> GAME_STATE.startTimer());
      //bStep. addActionListener(e -> GAME_STATE.gameStep());
      bStop. addActionListener(e -> GAME_STATE.stopTimer());
      //bReset.addActionListener(e -> GAME_STATE.reset());
      JButton[] buttons = {bStart, bStep, bStop, bReset};
      var buttonSize = new Dimension(70, 20);
      for (JButton e : buttons) {
         e.setPreferredSize(buttonSize);
      }
   
      add(bStart);
      add(bStop);
      add(bStep);
      add(bReset);
      
      setBackground(DARK_PURPLE);
      
   }
}
///////////////////////////////////////////////////////////

class GameDisplayPanel extends KPanel {
   private final SmartMouseStateManager GAME_STATE;
   
   public GameDisplayPanel(SmartMouseStateManager stateManager/*, SmartMouseFileReader fR*/) {
      GAME_STATE = stateManager;
      
      int testXCells = 25;
      int testYCells = 15;
      setLayout(new GridLayout(testXCells, testYCells));
      setBorder(BorderFactory.createLineBorder(ALMOST_BLACK, 10));
      setBackground(DARK_PURPLE);
   }
}

//////////////////////////////////////////

class SouthInterfacePanel extends KPanel implements ActionListener, ChangeListener {
   
   private final SmartMouseStateManager GAME_STATE;
   
   private final JButton bDown;
   private final JButton bLeft;
   private final JButton bRight;
   private final JButton bUp;
   
   private final JSlider slideFPS;
   private final KRadioButton chbxDebugMode;
   
   private static class MoveBufferPanel extends KPanel {
      public MoveBufferPanel(Dimension size) {
         setPreferredSize(size);
      }
   }
   
   public SouthInterfacePanel(SmartMouseStateManager stateManager) {
      GAME_STATE = stateManager;
      int movePanelWidth  = (5 * MOVE_BUTTON_SIDELENGTH);
      int movePanelHeight = (2 * MOVE_BUTTON_SIDELENGTH);
      int movePanelGap    = (MOVE_BUTTON_SIDELENGTH / 2);
      Dimension moveButtonDim =
            new Dimension(MOVE_BUTTON_SIDELENGTH, MOVE_BUTTON_SIDELENGTH);
      Dimension bufferGap = new Dimension(movePanelGap, movePanelHeight);
   
      setBackground(DARK_PURPLE);
      setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
      setPreferredSize(new Dimension(10, movePanelHeight));
   
      //instantiate panels
      JPanel debugPanel         = new KPanel();
      JPanel moveControlButtons = new KPanel();
      JPanel moveControlsPanel  = new KPanel();
      JPanel fpsSliderPanel     = new KPanel();
   
      //set up dbug mode radio button
      chbxDebugMode = new KRadioButton("Debug Mode");
      chbxDebugMode.addActionListener(this);
      debugPanel.add(chbxDebugMode);
   
      //set up move buttons and panel
      
      bDown  = new KButton("v"); //DOWN MOVEMENT BUTTON
      bDown.addActionListener(event -> {
         //SmartMouseEntities.MOUSE.move(Directions.SOUTH);
         if (GAME_STATE.getDebugMode()) System.out.println("Down Button Clicked.");
      });
      bDown.setPreferredSize(moveButtonDim);
      
      bLeft  = new KButton("<"); //LEFT MOVEMENT BUTTON
      bLeft.addActionListener(event -> {
         //SmartMouseEntities.MOUSE.move(Directions.WEST);
         if (GAME_STATE.getDebugMode()) System.out.println("Left Button Clicked.");
      });
      bLeft.setPreferredSize(moveButtonDim);
      
      bRight = new KButton(">"); //RIGHT MOVEMENT BUTTON
      bRight.addActionListener(event -> {
         //SmartMouseEntities.MOUSE.move(Directions.EAST);
         if (GAME_STATE.getDebugMode()) System.out.println("Right Button Clicked.");
      });
      bRight.setPreferredSize(moveButtonDim);
      
      bUp    = new KButton("^"); //UP MOVEMENT BUTTON
      bUp.addActionListener(event -> {
         //SmartMouseEntities.MOUSE.move(Directions.NORTH);
         if (GAME_STATE.getDebugMode()) System.out.println("Up Button Clicked.");
      });
      bUp.setPreferredSize(moveButtonDim);
   
      moveControlButtons.setLayout(new GridLayout(1, 4, movePanelGap - 10, movePanelGap));
      moveControlButtons.setBackground(DARK_PURPLE);
      moveControlButtons.add(bLeft);
      moveControlButtons.add(bUp);
      moveControlButtons.add(bDown);
      moveControlButtons.add(bRight);
   
      moveControlsPanel.setPreferredSize(new Dimension(movePanelWidth, movePanelHeight));
      moveControlsPanel.add(new MoveBufferPanel(bufferGap), BorderLayout.WEST);
      moveControlsPanel.add(moveControlButtons);
      moveControlsPanel.add(new MoveBufferPanel(bufferGap), BorderLayout.EAST);
      
      JLabel fpsLabel = new KLabel("FPS: ");
      fpsLabel.setVerticalAlignment(SwingConstants.CENTER);
      fpsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
      fpsLabel.setOpaque(true);
      fpsLabel.setBackground(DARK_PURPLE);
      slideFPS = new JSlider(1, 10, 1);
      slideFPS.addChangeListener(this);
      slideFPS.setMajorTickSpacing(1);
      slideFPS.setMinorTickSpacing(1);
      slideFPS.setPaintTicks(true);
      slideFPS.setPaintLabels(true);
      slideFPS.setBackground(DARK_PURPLE);
      slideFPS.setForeground(Color.WHITE);
      
      fpsSliderPanel.add(fpsLabel, BorderLayout.WEST);
      fpsSliderPanel.add(slideFPS);
      
      add(fpsSliderPanel);
      add(moveControlButtons);
      add(debugPanel);
   }
   
   
   
   @Override
   public void actionPerformed(ActionEvent e) {
      Object source = e.getSource();
      if (source == chbxDebugMode) {
         GAME_STATE.setDebugMode(chbxDebugMode.isSelected());
         if (GAME_STATE.getDebugMode()) System.out.println("Debug Button Toggled.");
      }
      else if (source == bDown) {
         //SmartMouseEntities.MOUSE.move(Directions.SOUTH);
         if (GAME_STATE.getDebugMode()) System.out.println("Down Button Clicked.");
      }
      else if (source == bLeft) {
         //SmartMouseEntities.MOUSE.move(Directions.WEST);
         if (GAME_STATE.getDebugMode()) System.out.println("Left Button Clicked.");
      }
      else if (source == bRight) {
         //SmartMouseEntities.MOUSE.move(Directions.EAST);
         if (GAME_STATE.getDebugMode()) System.out.println("Right Button Clicked.");
      }
      else if (source == bUp) {
         //SmartMouseEntities.MOUSE.move(Directions.NORTH);
         if (GAME_STATE.getDebugMode()) System.out.println("Up Button Clicked.");
      }
      
   }
   
   @Override
   public void stateChanged(ChangeEvent e) {
      int val = slideFPS.getValue();
      int newMS = 1000 / val;
      
      String debugMSG = "Slider is now " + slideFPS.getValue();
      if (GAME_STATE.getDebugMode()) System.out.println(debugMSG);
   }
}
