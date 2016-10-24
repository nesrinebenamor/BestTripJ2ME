/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class MenuFinal implements CommandListener, IFormListener {
    
     private Display display;

    
    
     private Ticker tk;

    
  
    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton profil = new TextButton("Profil", "/image/User.png");
    TextButton experience = new TextButton("Espace Experiences", "/image/Web.png");
    TextButton guide = new TextButton("Espace Guides","/image/Paste.png");
    TextButton recommandation = new TextButton("Espace recommandations","/image/Announcement.png");
    TextButton forum = new TextButton("Forum","/image/Chat.png");
    
    TextButton exit = new TextButton("Exit", "/image/exit.png");
    
    
     public MenuFinal(Display display) {

          this.display = display;
     
        menuList.setTitle("Menu Utilisateur");
        menuList.setTicker(tk);
        menuList.addItem(profil);
        menuList.addItem(experience);
        
        menuList.addItem(guide);
        menuList.addItem(recommandation);
        menuList.addItem(forum);
       
        menuList.addItem(exit);
        menuList.addPopperListener(this);
        menuList.setCommandListener(this);
        menuList.addPopperListener(this);
        
        utils.StaticMidlet.disp.setCurrent(menuList);
    }


    public void commandAction(Command c, Displayable d) {
      
    }

    public void selected(ImageButton ib) {
 
        
    }

public void clicked(ImageButton ib) {
        if (ib == profil) {
     // new ajouterSalleM(display);
            new MenuUser(utils.StaticMidlet.disp);
        }
        
         if (ib == experience) {
         new MenuExperience(display, 1);
        }
           if (ib == guide) {
        
          new MenuGuide(display);
           }
           
            if (ib == recommandation) {
          
              new ClasseMenuRec(display);
          // utils.StaticMidlet.disp.setCurrent(new AjoutUtilisateur());
            }
            
           if(ib==exit)
           {
               //new Authentification(display);
               utils.StaticMidlet.disp.setCurrent(new Authentification());
           }
            if (ib == forum) {
          
              new ClasseMenu(display);
         
            }
           
    }

   

 
}
    

