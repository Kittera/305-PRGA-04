package guiResources;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

@SuppressWarnings("SpellCheckingInspection")
public class KGUIRepo {
   
   public static final String IMAGEPATH =
         "src/guiResources/images/";
   
   public static final ImageIcon FACE_PALM =
         new ImageIcon(IMAGEPATH + "Kittera_facepalm.png");
   
   
   //color scheme
   public static final Color BUTTON_HILGHT    = new Color(0x474747);
   public static final Color BUTTON_SHADOW    = new Color(0x262626);
   public static final Color DARK_BTN         = new Color(0x333333);
   public static final Color DARK_PURPLE      = new Color(0x1D0037);
   public static final Color DARK_TXT_FLD_BG  = new Color(0x515159);
   public static final Color ALMOST_BLACK     = new Color(0x050505);
   public static final Color OFFWHT_TXT       = new Color(0xE0E0E0);
   
   public static final Font GEN_FONT = new Font("Consolas", Font.BOLD, 16);
   
   
   //KRadioButton
   public static final ImageIcon IMG_KRADIO_SELECTED =
         new ImageIcon("src/guiResources/images/radioselect.png");
   public static final ImageIcon IMG_KRADIO_IDLE =
         new ImageIcon("src/guiResources/images/radioidle.png");
   public static final ImageIcon IMG_KRADIO_ROLLSELECTED =
         new ImageIcon("src/guiResources/images/radioselectroll.png");
   public static final ImageIcon IMG_KRADIO_ROLLIDLE =
         new ImageIcon("src/guiResources/images/radioidleroll.png");
   public static final ImageIcon IMG_KRADIO_PRSSD =
         new ImageIcon("src/guiResources/images/radiopressed.png");
}
