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
public class ClasseMenu implements CommandListener, IFormListener {
    
     private Display display;

    
    
     private Ticker tk;

    
  
    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton AjouterSujet = new TextButton("Ajouter", "/image/add2.png");
    TextButton AfficherSujet = new TextButton("Afficher", "/image/show.png");
    TextButton RechercherSujet = new TextButton("Rechercher","/image/cherch1.png");
    
    
   // TextButton exit = new TextButton("Exit", "/image/exit.png");
    
    
    
    
     public ClasseMenu(Display display) {

          this.display = display;
     
        menuList.setTitle("Menu Forum");
        menuList.setTicker(tk);
        menuList.addItem(AjouterSujet);
        menuList.addItem(AfficherSujet);
        
        menuList.addItem(RechercherSujet);
        
       
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
        if (ib == AjouterSujet) {
     // new ajouterSalleM(display);
            new AjoutSujet(display);
        }
        
         if (ib == AfficherSujet) {
         // new AfficherListeSalles(display);
             new AfficheSujet(display);
        }
           if (ib == RechercherSujet) {
        // new StatSalles(display);
          new RechercheSujet(display);
           }
           
            
            
           
           
           
    }

    private void startApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

 
}
    

