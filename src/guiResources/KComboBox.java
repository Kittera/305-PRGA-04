package guiResources;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.*;

import static guiResources.KGUIRepo.*;

public class KComboBox extends JComboBox<String> {
   public KComboBox(String[] choices) {
      super(choices);
      //noinspection SpellCheckingInspection
      setFont(new Font("Consolas", Font.BOLD, 16));
      setBackground(DARK_TXT_FLD_BG);
      setForeground(OFFWHT_TXT);
      setRenderer(new KListCellRenderer(getRenderer()));
      setUI(ColorArrowUI.createUI());
      setPreferredSize(new Dimension(120, 30));
   }
}
@SuppressWarnings("rawtypes")
class KListCellRenderer extends DefaultListCellRenderer {
   
   private final ListCellRenderer defaultRenderer;
   
   public KListCellRenderer(ListCellRenderer defaultRenderer) {
      this.defaultRenderer = defaultRenderer;
   }
   
   @Override
   public Component getListCellRendererComponent(JList list, Object value,
                                                 int index, boolean isSelected, boolean cellHasFocus) {
      //noinspection unchecked
      Component c = defaultRenderer.getListCellRendererComponent(list, value,
            index, isSelected, cellHasFocus);
      if (c instanceof JLabel) {
         c.setPreferredSize(new Dimension(90, 25));
         if (isSelected) {
            c.setBackground(OFFWHT_TXT);
            c.setForeground(DARK_BTN);
         } else {
            c.setBackground(DARK_BTN);
            c.setForeground(OFFWHT_TXT);
         }
      } else {
         c.setBackground(DARK_BTN);
         c = super.getListCellRendererComponent(list, value, index, isSelected,
               cellHasFocus);
      }
      return c;
   }
}

class ColorArrowUI extends BasicComboBoxUI {
   
   public static ComboBoxUI createUI() {
      return new ColorArrowUI();
   }
   
   @Override protected JButton createArrowButton() {
      return new BasicArrowButton(
            BasicArrowButton.SOUTH,
            DARK_BTN, BUTTON_HILGHT,
            new Color(0xAFAFAF), BUTTON_HILGHT);
   }
}