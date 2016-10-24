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
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Ticker;
import net.sourceforge.mewt.button.ImageButton;
import net.sourceforge.mewt.button.TextButton;
import net.sourceforge.mewt.iform.IFormCanvas;
import net.sourceforge.mewt.iform.IFormListener;

/**
 *
 * @author esprit
 */
public class MenuUser extends Form implements CommandListener, IFormListener {

    private Display display;

    private Ticker tk;

    IFormCanvas menuList = new IFormCanvas(3, 3);
    TextButton ajout = new TextButton("créer", "/image/add.png");
    TextButton consulter = new TextButton("Consulter profil", "/image/consulter.png");
    TextButton desactiver = new TextButton("Désactiver profil", "/image/desactiver.png");
    TextButton contact = new TextButton("Contactez-nous", "/image/mail.png");
    TextButton envoisms = new TextButton("Envoi Sms", "/image/sms.png");
    TextButton reception = new TextButton("Reception", "/image/reception.png");
    TextButton modifier = new TextButton("Modifier profil", "/image/modifier.png");
    TextButton rechercher = new TextButton("Rechercher user", "/image/rechercher.png");
    TextButton addphoto = new TextButton("Ajouter photo", "/image/addphoto.png");
    TextButton login = new TextButton("Se connecter", "/image/login.png");
    TextButton logout = new TextButton("Se déconnecter", "/image/logout.png");

    TextButton exit = new TextButton("Exit", "/image/exit.png");
        private int id;
        
         public MenuUser(Display display) {
             super("Menu utilisateur");
        this.display = display;

        menuList.setTitle("Menu Utilisateur");
        menuList.setTicker(tk);
        menuList.addItem(ajout);
        menuList.addItem(consulter);

        menuList.addItem(desactiver);
        menuList.addItem(contact);
        menuList.addItem(envoisms);
        menuList.addItem(reception);
        menuList.addItem(modifier);
        menuList.addItem(rechercher);
        menuList.addItem(addphoto);
        menuList.addItem(login);
        menuList.addItem(logout);
        menuList.addItem(exit);
        menuList.addPopperListener(this);
        menuList.setCommandListener(this);
        menuList.addPopperListener(this);

        utils.StaticMidlet.disp.setCurrent(menuList);
    }

    public MenuUser(Display display,int id) {
        super("Menu utilisateur");
        this.display = display;
        this.id=id;

        menuList.setTitle("Menu Utilisateur");
        menuList.setTicker(tk);
        menuList.addItem(ajout);
        menuList.addItem(consulter);

        menuList.addItem(desactiver);
        menuList.addItem(contact);
        menuList.addItem(envoisms);
        menuList.addItem(reception);
        menuList.addItem(modifier);
        menuList.addItem(rechercher);
        menuList.addItem(addphoto);
        menuList.addItem(login);
        menuList.addItem(logout);
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
        if (ib == ajout) {
            // new ajouterSalleM(display);
            utils.StaticMidlet.disp.setCurrent(new AjoutUtilisateur());
        }

        if (ib == consulter) {
            // new AfficherListeSalles(display);
            utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, new Menu()));
        }
        if (ib == envoisms) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new SendSms());
        }
         if (ib == desactiver) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, new Menu()));
        }
          if (ib == rechercher) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new RechercheUtilisateur());
        } if (ib == modifier) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, new Menu()));
        } if (ib == reception) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new SMSReceive());
        } if (ib == addphoto) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new FileBrowserClasse(id, utils.StaticMidlet.disp));
        } if (ib == login) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new Authentification());
        }
         if (ib == logout) {
            // new StatSalles(display);
            utils.StaticMidlet.disp.setCurrent(new Logout());
        }
        if (ib == contact) {
            // new AffectationSallesDeps(display);
            utils.StaticMidlet.disp.setCurrent(new SendMail());
        }

        if (ib == exit) {
            //new Authentification(display);
            utils.StaticMidlet.disp.setCurrent(new Authentification());
        }

    }

}
