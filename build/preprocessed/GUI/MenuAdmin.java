package GUI;




import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Ticker;
import GUI.AfficheSujet;
import GUI.AjoutSujet;
import GUI.RechercheSujet;
import net.sourceforge.mewt.button.ImageButton;
import net.sourceforge.mewt.button.TextButton;
import net.sourceforge.mewt.iform.IFormCanvas;
import net.sourceforge.mewt.iform.IFormListener;

/**
 *
 * @author esprit
 */
public class MenuAdmin implements CommandListener, IFormListener {
    
     private Display display;

    
    
     private Ticker tk;

    
  
    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton GestionUtilisateur = new TextButton("Ajouter", "/images/add.png");
    TextButton GestionExp = new TextButton("Afficher", "/images/lightbulb.png");
    TextButton GestionGuide = new TextButton("Rechercher","/images/search.png");
    TextButton GestionForum = new TextButton("Rechercher","/images/search.png");
    TextButton GestionRec = new TextButton("Rechercher","/images/search.png");
    
    
   // TextButton exit = new TextButton("Exit", "/image/exit.png");
    
    
    
    
     public MenuAdmin(Display display) {

          this.display = display;
     
        menuList.setTitle("Menu Forum");
        menuList.setTicker(tk);
        menuList.addItem(GestionUtilisateur);
        menuList.addItem(GestionExp);
        
        menuList.addItem(GestionGuide);
        menuList.addItem(GestionForum);
        menuList.addItem(GestionRec);
        
       
        //menuList.addItem(exit);
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
        if (ib == GestionUtilisateur) {
     // new ajouterSalleM(display);
            new AjoutSujet(display);
        }
        
         if (ib == GestionExp) {
         // new AfficherListeSalles(display);
             new AfficheSujet(display);
        }
           if (ib == GestionGuide) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           if (ib == GestionForum) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           if (ib == GestionRec) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           
            
            
           
           
           
    }

    private void startApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

 
}
    

