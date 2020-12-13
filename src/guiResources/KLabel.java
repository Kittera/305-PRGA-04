package guiResources;

import javax.swing.*;
import java.awt.*;

public class KLabel extends JLabel {
   public KLabel(String text) {
      setText(text);
      setForeground((Color.WHITE));
      //noinspection SpellCheckingInspection
      setFont(new Font("Consolas", Font.BOLD, 13));
   }
}
