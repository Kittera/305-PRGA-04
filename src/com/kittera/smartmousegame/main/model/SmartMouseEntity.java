package com.kittera.smartmousegame.main.model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SmartMouseEntity implements ActionListener {
   private Point myLoc = new Point();
   private Directions myDir = Directions.CENTER;
   
   public SmartMouseEntity() {
   
   }
   
   public abstract void actionPerformed(ActionEvent e);
}
