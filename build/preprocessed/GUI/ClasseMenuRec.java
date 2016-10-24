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
public class ClasseMenuRec implements CommandListener, IFormListener {
    
     private Display display;

    
    
     private Ticker tk;

    
  
    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton ajoutrecommandation = new TextButton("Ajouter", "/image/add2.png");
    TextButton affichrecommandation = new TextButton("Afficher", "/image/show.png");
    
    TextButton rechercherecommandation = new TextButton("Rechercher","/image/cherch1.png");
    TextButton stat = new TextButton("Statistic","/image/chart.png");
    
    
    
    
    
     public ClasseMenuRec(Display display) {

          this.display = display;
     
        menuList.setTitle("recommandation");
        menuList.setTicker(tk);
        menuList.addItem(ajoutrecommandation);
        menuList.addItem(affichrecommandation);
        
        menuList.addItem(rechercherecommandation);
        menuList.addItem(stat);
        
       
        
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
        if (ib == ajoutrecommandation) {
     // new ajouterSalleM(display);
            new AjoutRecom(display);
        }
        
         if (ib == affichrecommandation) {
         // new AfficherListeSalles(display);
             new AfficherRecommandation(display);
        }
           if (ib == rechercherecommandation) {
        // new StatSalles(display);
          new RechercheRecom(display);
           }
           
            if (ib == stat) {
          // new AffectationSallesDeps(display);
          new statistic(display);
            }
            
           
           
           
    }

   

 
}
    

