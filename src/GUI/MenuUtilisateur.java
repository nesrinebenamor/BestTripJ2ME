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
public class MenuUtilisateur implements CommandListener, IFormListener {
    
     private Display display;

    
    
     private Ticker tk;

    
  
    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton GestionProfil = new TextButton("Ajouter", "/images/add.png");
    TextButton GestionExpU = new TextButton("Afficher", "/images/lightbulb.png");
    TextButton GestionGuideU = new TextButton("Rechercher","/images/search.png");
    TextButton GestionForumU = new TextButton("Rechercher","/images/search.png");
    TextButton GestionRecU = new TextButton("Rechercher","/images/search.png");
    
    
   // TextButton exit = new TextButton("Exit", "/image/exit.png");
    
    
    
    
     public MenuUtilisateur(Display display) {

          this.display = display;
     
        menuList.setTitle("Menu Forum");
        menuList.setTicker(tk);
        menuList.addItem(GestionProfil);
        menuList.addItem(GestionExpU);
        
        menuList.addItem(GestionGuideU);
        menuList.addItem(GestionForumU);
        menuList.addItem(GestionRecU);
        
       
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
        if (ib == GestionProfil) {
     // new ajouterSalleM(display);
            new AjoutSujet(display);
        }
        
         if (ib == GestionExpU) {
         // new AfficherListeSalles(display);
             new AfficheSujet(display);
        }
           if (ib == GestionGuideU) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           if (ib == GestionForumU) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           if (ib == GestionRecU) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           
            
            
           
           
           
    }

    private void startApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

 
}
    

