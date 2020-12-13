package guiResources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static guiResources.KGUIRepo.*;

public class KRadioButton extends JRadioButton {
   private static final int RND_RAD = 7;
   private Shape shape;
   
   
   @SuppressWarnings("SpellCheckingInspection")
   public KRadioButton(String label) {
      super(label);
      
      //stdconfig
      setBackground(BUTTON_SHADOW);
      setBorder(BorderFactory.createRaisedBevelBorder());
      setFocusable(false);
      setIconTextGap(8);
      setFont(new Font(getFont().getFontName(), Font.BOLD, 13));
      setForeground(OFFWHT_TXT);
      setPreferredSize(new Dimension(125,40));
      setOpaque(false);
      
      //set up image resources
      setIcon(IMG_KRADIO_IDLE);
      setSelectedIcon(IMG_KRADIO_SELECTED);
      setRolloverSelectedIcon(IMG_KRADIO_ROLLSELECTED);
      setRolloverIcon(IMG_KRADIO_ROLLIDLE);
      setPressedIcon(IMG_KRADIO_PRSSD);
   }
   
   protected void paintComponent(Graphics g) {
      g.setColor(getBackground());
      ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      g.fillRoundRect(0, 0, getWidth(), getHeight(), RND_RAD + 2, RND_RAD + 2);
      super.paintComponent(g);
   }
   
   protected void paintBorder(Graphics g) {
      g.setColor(ALMOST_BLACK);
      ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, RND_RAD, RND_RAD);
   }
   
   public boolean contains(int x, int y) {
      if (shape == null || !shape.getBounds().equals(getBounds())) {
         shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, RND_RAD, RND_RAD);
      }
      return shape.contains(x, y);
   }
}