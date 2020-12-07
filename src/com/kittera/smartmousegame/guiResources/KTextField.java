package com.kittera.smartmousegame.guiResources;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import static com.kittera.smartmousegame.guiResources.KGUIRepo.*;

public class KTextField extends JTextField {
   private static final int RND_RAD = 27;
   private Shape shape;
   
   public KTextField(String text) {
      setText(text);
      setBackground(DARK_TXT_FLD_BG);
      setPreferredSize(new Dimension(200, 30));
      setForeground(Color.WHITE);
      setCaretColor(Color.WHITE);
      setOpaque(false);
   
      //noinspection SpellCheckingInspection
      setFont(new Font("Consolas", Font.BOLD, 15));
      
      //add margin for inner text
      setBorder(BorderFactory.createEmptyBorder(1,15,1,15));
   }
   
   public KTextField() {
      this("");
   }
   
   protected void paintComponent(Graphics g) {
      g.setColor(getBackground());
      ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      g.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, RND_RAD, RND_RAD);
      super.paintComponent(g);
   }
   
   protected void paintBorder(Graphics g) {
      g.setColor(Color.BLACK);
      ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      g.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 4, RND_RAD, RND_RAD);
   }
   
   public boolean contains(int x, int y) {
      if (shape == null || !shape.getBounds().equals(getBounds())) {
         shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, RND_RAD, RND_RAD);
      }
      return shape.contains(x, y);
   }
}
