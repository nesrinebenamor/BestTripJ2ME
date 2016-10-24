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

/**
 *
 * @author esprit
 */
public class MenuGuide implements CommandListener, IFormListener {
    
     private Display display;

    
    
     private Ticker tk;

    
  
    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton AjoutGuide = new TextButton("Ajouter Guide", "/image/add.png");
    TextButton AfficherGuide = new TextButton("Afficher Guide", "/image/lightbulb.png");
    TextButton RechGuide = new TextButton("Recherche Guides","/image/search.png");
    TextButton Map = new TextButton("Map","/image/apple.png");
    
    
     Command cmdBack = new Command("Back", Command.SCREEN, 0);
    
    
     public MenuGuide(Display display) {

          this.display = display;
     
        menuList.setTitle("Menu Utilisateur");
        menuList.setTicker(tk);
        menuList.addItem(AjoutGuide);
        menuList.addItem(AfficherGuide);
        
        menuList.addItem(RechGuide);
        menuList.addItem(Map);
        
       menuList.addCommand(cmdBack);
       menuList.setCommandListener(this);
        
        menuList.addPopperListener(this);
        menuList.setCommandListener(this);
        menuList.addPopperListener(this);
        
        display.setCurrent(menuList);
    }


    public void commandAction(Command c, Displayable d) {
      
    }

    public void selected(ImageButton ib) {
 
        
    }

public void clicked(ImageButton ib) {
        if (ib == AjoutGuide) {
     // new ajouterSalleM(display);
            new AjoutGuide(display);
        }
        
         if (ib == AfficherGuide) {
         // new AfficherListeSalles(display);
             new SuppUpAffGuide(display);
        }
           if (ib == RechGuide) {
        // new StatSalles(display);
          new RechercheGuide(display);
           }
           
            if (ib == Map) {
          // new AffectationSallesDeps(display);
          new Map(display);
            }
           
    }

}
    

