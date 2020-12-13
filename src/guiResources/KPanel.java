package guiResources;

import javax.swing.*;
import java.awt.*;

import static guiResources.KGUIRepo.*;

public class KPanel extends JPanel {
   
   public KPanel(LayoutManager layout) {
      setLayout(layout);
      setBackground(ALMOST_BLACK);
      setForeground(OFFWHT_TXT);
   }
   
   public KPanel() {
      this(new BorderLayout(1,1));
   }
}