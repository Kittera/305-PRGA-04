package guiResources;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static guiResources.KGUIRepo.*;

public class KButton extends JButton {
   
   public KButton(String label) {
      setVerticalTextPosition(JButton.CENTER);
      setHorizontalTextPosition(JButton.CENTER);
      setForeground(OFFWHT_TXT);
      setText(label);
      setFocusable(false);
   
      //create custom border
      Border bevel = BorderFactory.createRaisedSoftBevelBorder();
      Border gap = BorderFactory.createEmptyBorder(3, 6, 3, 6);
      setBorder(BorderFactory.createCompoundBorder(bevel, gap));
   
      setFont(new Font("ComicSans", Font.BOLD, 14));
      
      setForeground(OFFWHT_TXT);
      setBackground(DARK_BTN);
   }
}
