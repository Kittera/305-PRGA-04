package com.kittera.smartmousegame.guiResources;

import java.awt.Color;
import java.util.Random;
import javax.swing.ImageIcon;

@SuppressWarnings("SpellCheckingInspection")
public class KGUIRepo {
   
   public static final ImageIcon FACE_PALM =
         new ImageIcon("src/com/kittera/smartmouse/guiResources/images/Kittera_facepalm.png");
   
   //color scheme
   public static final Color BUTTON_HILGHT    = new Color(0x474747);
   public static final Color BUTTON_SHADOW    = new Color(0x262626);
   public static final Color DARK_BTN         = new Color(0x333333);
   public static final Color DARK_PURPLE      = new Color(0x1D0037);
   public static final Color DARK_TXT_FLD_BG  = new Color(0x515159);
   public static final Color ALMOST_BLACK     = new Color(0x050505);
   public static final Color OFFWHT_TXT       = new Color(0xE0E0E0);
   public static final Color DISCORD_GRAY     = new Color(0x36393F);
//   public static final Color DARK_TXT_FLD_BRD = new Color(0x414147);
   
   
   //KRadioButton
   public static final ImageIcon IMG_KRADIO_SELECTED =
         new ImageIcon("src/com/kittera/smartmousegame/guiResources/images/radioselect.png");
   public static final ImageIcon IMG_KRADIO_IDLE =
         new ImageIcon("src/com/kittera/smartmousegame/guiResources/images/radioidle.png");
   public static final ImageIcon IMG_KRADIO_ROLLSELECTED =
         new ImageIcon("src/com/kittera/smartmousegame/guiResources/images/radioselectroll.png");
   public static final ImageIcon IMG_KRADIO_ROLLIDLE =
         new ImageIcon("src/com/kittera/smartmousegame/guiResources/images/radioidleroll.png");
   public static final ImageIcon IMG_KRADIO_PRSSD =
         new ImageIcon("src/com/kittera/smartmousegame/guiResources/images/radiopressed.png");
   
   //program-wide GUI helper method(s)
   public static String getRandomErrorMessage() {
      Random picker = new Random();
      return switch (picker.nextInt(14)) {
         default -> "Nope.";
         case 1  -> "Nada.";
         case 2  -> "Fail!";
         case 3  -> "Sorry.";
         case 4  -> "No way.";
         case 5  -> "Aw, come on!";
         case 6  -> "Nothing here.";
         case 7  -> "I'm sorry, Dave.";
         case 8  -> "Those aren't numbers!";
         case 9  -> "Why would you do this to me?";
         case 10 -> "That quite clearly did not work.";
         case 11 -> "I don't speak whatever's in there.";
         case 12 -> "And then I took an invalid input to the knee.";
         case 13 -> "NaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaNaN";
      };
   }
}
