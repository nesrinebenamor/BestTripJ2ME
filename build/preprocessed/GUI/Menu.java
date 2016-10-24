/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

/**
 *
 * @author esprit
 */
public class Menu extends Form implements CommandListener,Runnable{

   
    private utils.StaticMidlet m;

    //add delete file functionality
    private Command delete ;
    private Command view ;
    private Command sendSms ;
    private Command reception ;
    private Command addPhoto ;
    private Command connexion ;
    private Command deconnexion ;
    private Command exit ;
    private Command sendMail;
    private Command add ;
    private Command search ;
    private Command espaceAdmin ;
    private int id;
    
    public Menu(int id)
    {super("Menu");
        this.id=id;
         view = new Command("View", Command.ITEM, 1);
    search = new Command("Search", Command.ITEM, 2);

    //add delete file functionality
    delete = new Command("Delete", Command.ITEM, 3);
    add = new Command("Add", Command.ITEM, 1);
    connexion = new Command("Connexion", Command.ITEM, 4);
    deconnexion = new Command("Deconnexion", Command.ITEM, 5);
    sendSms = new Command("Send SMS", Command.ITEM, 3);
    reception = new Command("Reception", Command.ITEM, 3);
    addPhoto = new Command("Add Photo", Command.OK, 2);
    exit = new Command("Exit", Command.EXIT, 3);
    sendMail=new Command("SendMail",Command.ITEM,2);
    espaceAdmin=new Command("Espace Admin",Command.ITEM,2);
    
    
    addCommand(view);
    this.setCommandListener(this);
    addCommand(add);
    this.setCommandListener(this);
     addCommand(addPhoto);
    this.setCommandListener(this);
    addCommand(sendSms);
    this.setCommandListener(this);
     addCommand(reception);
    this.setCommandListener(this);
    addCommand(exit);
    this.setCommandListener(this);
     addCommand(search);
    this.setCommandListener(this);
    addCommand(connexion);
    this.setCommandListener(this);
     addCommand(deconnexion);
    this.setCommandListener(this);
    addCommand(sendMail);
    this.setCommandListener(this);
     addCommand(espaceAdmin);
    this.setCommandListener(this);
    }
    
    public Menu() {
        super("Menu");
       //  this.id=id;
    view = new Command("View", Command.ITEM, 1);
    search = new Command("Search", Command.ITEM, 2);

    //add delete file functionality
    delete = new Command("Delete", Command.ITEM, 3);
    add = new Command("Add", Command.ITEM, 1);
    connexion = new Command("Connexion", Command.ITEM, 4);
    deconnexion = new Command("Deconnexion", Command.ITEM, 5);
    sendSms = new Command("Send SMS", Command.ITEM, 3);
    reception = new Command("Reception", Command.ITEM, 3);
    addPhoto = new Command("Add Photo", Command.OK, 2);
    exit = new Command("Exit", Command.EXIT, 3);
    sendMail=new Command("SendMail",Command.ITEM,2);
     espaceAdmin=new Command("Espace Admin",Command.ITEM,2);
    
    
    addCommand(view);
    this.setCommandListener(this);
    addCommand(add);
    this.setCommandListener(this);
     addCommand(addPhoto);
    this.setCommandListener(this);
    addCommand(sendSms);
    this.setCommandListener(this);
     addCommand(reception);
    this.setCommandListener(this);
    addCommand(exit);
    this.setCommandListener(this);
     addCommand(search);
    this.setCommandListener(this);
    addCommand(connexion);
    this.setCommandListener(this);
     addCommand(deconnexion);
    this.setCommandListener(this);
    addCommand(sendMail);
    this.setCommandListener(this);
    addCommand(espaceAdmin);
    this.setCommandListener(this);
    
    }

    public void commandAction(Command c, Displayable d) {
         if (c == exit) {
            utils.StaticMidlet.disp.setCurrent(this);
            
        }  if (c == espaceAdmin) {
            utils.StaticMidlet.disp.setCurrent(new ParsingAdministrateur());
            
        }
        if (c == add) {
            utils.StaticMidlet.disp.setCurrent(new AjoutUtilisateur());
        }
          if (c == addPhoto) {
              Thread myThread = new Thread(this);
              myThread.start();
              System.out.println("test 1");            
        }
        if (c == connexion) {
            utils.StaticMidlet.disp.setCurrent(new Authentification());
        }
        if (c == sendMail) {
            utils.StaticMidlet.disp.setCurrent(new SendMail());
        }
        if (c == search) {
            utils.StaticMidlet.disp.setCurrent(new RechercheUtilisateur());
        }
          if (c == delete) {
            utils.StaticMidlet.disp.setCurrent(new ParsingXml());
        }
        if (c == sendSms) {
            utils.StaticMidlet.disp.setCurrent(new SendSms());
        }
          if (c == reception) {
            utils.StaticMidlet.disp.setCurrent(new SMSReceive());
        }
       if (c == view) {
            utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, new SendSms()));
        }
         if (c == deconnexion) {
            utils.StaticMidlet.disp.setCurrent(new Logout());
        }
    }

    public void run() {
        System.out.println("id="+id);
         //utils.StaticMidlet.disp.setCurrent(new FileBrowserClasse(id, new SendSms()));
        utils.StaticMidlet.disp.setCurrent(new FileBrowserClasse(id,utils.StaticMidlet.disp));
    }
    
}
