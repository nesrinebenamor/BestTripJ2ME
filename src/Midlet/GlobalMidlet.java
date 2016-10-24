/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Midlet;

import GUI.ClasseMenu;
import GUI.ClasseMenuRec;
import GUI.MenuFinal;
import GUI.Menumezyen;
import GUI.SplashScreen;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.midlet.*;

/**
 * @author esprit
 */
public class GlobalMidlet extends MIDlet {
    Display display = Display.getDisplay(this);
 public Form f1 = new Form("");
    public void startApp() {
         utils.StaticMidlet.mainMid = this;
        utils.StaticMidlet.disp = display;
        new MenuFinal(display);
       // display.setCurrent(new GUI.AjoutUtilisateur());
      //new ClasseMenuRec(display);
       display.setCurrent(new SplashScreen(this));
    
        try {
      Player player = Manager.createPlayer(getClass().getResourceAsStream("/media/test-wav.wav"), "audio/x-wav");
      player.start();
    } catch (Exception e)
    {
      e.printStackTrace();
    }
        
     
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
