package GUI;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Ticker;
import net.sourceforge.mewt.button.ImageButton;
import net.sourceforge.mewt.button.TextButton;
import net.sourceforge.mewt.iform.IFormCanvas;
import net.sourceforge.mewt.iform.IFormListener;
import GUI.MidletAjout;

public class MenuExperience implements IFormListener, CommandListener {

    private Display display;
    private Ticker tk;

    IFormCanvas menuList = new IFormCanvas(3, 3);

    TextButton ajoutExp = new TextButton("Ajouter", "/image/add2.png");
    TextButton affichExp = new TextButton("Afficher", "/image/show.png");
    TextButton cherchExp = new TextButton("Rechercher", "/image/cherch1.png");
    
    Command cmdBack = new Command("Back", Command.BACK, 0);
    int id_ut=1;

    public MenuExperience(Display display, int id_ut) {

        this.id_ut = id_ut;
        this.display = display;
        menuList.setTitle("Menu Forum");
        menuList.setTicker(tk);
        menuList.addItem(ajoutExp);
        menuList.addItem(affichExp);
        menuList.addItem(cherchExp);

        //menuList.addItem(exit);
        menuList.addPopperListener(this);
        menuList.setCommandListener(this);
        menuList.addPopperListener(this);
      menuList.addCommand(cmdBack);
        display.setCurrent(menuList);
    }

    public void selected(ImageButton ib) {
    }

    public void clicked(ImageButton ib) {
        if (ib == ajoutExp) {
            new MidletAjout(display, id_ut);
        }

        if (ib == affichExp) {
         new experienceParsing(display, id_ut);
        }
        if (ib == cherchExp) {

        }

    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdBack) {
           new MenuFinal(display);
        }
    }

}
