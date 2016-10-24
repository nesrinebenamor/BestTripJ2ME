/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.*;
import java.io.DataInputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class Authentification extends Form implements CommandListener,ItemCommandListener,Runnable {
    HttpConnection hc;
    DataInputStream ds;
    String url = "";
    Form myForm;
    TextField myTextNom;
    TextField myTextPass ;
    StringBuffer sb ;
    Command ValidCmd ;
    mobileapplicationparsingxml.Utilisateur[] utilisateur;
    List services;
    String login;
    private Command exit ;
    private StringItem MotDePassOublier ;
    private StringItem inscription;
     

    public Authentification() {
        super("Authentification");
        
        myForm = new Form("Accueil");
        myTextNom = new TextField("username", "", 100, TextField.ANY);
        myTextPass = new TextField("password", "", 100, TextField.PASSWORD);
        sb = new StringBuffer("");
        exit = new Command("exit", Command.EXIT, 1);
        ValidCmd = new Command("valider", Command.SCREEN, 0);
        services = new List("Choose one", Choice.EXCLUSIVE);
        MotDePassOublier = new StringItem("", "mot de passe oublier ?", Item.HYPERLINK);
        MotDePassOublier.setDefaultCommand(new Command("Mot de passe oublier", Command.ITEM, 1));
        inscription = new StringItem("", "Inscription", Item.HYPERLINK);
        inscription.setDefaultCommand(new Command("Mot de passe oublier", Command.ITEM, 1));
        addCommand(ValidCmd);
        this.setCommandListener(this);
        append(myTextNom);
        append(myTextPass);
        append(MotDePassOublier);
        append(inscription);
        this.setCommandListener(this);
        MotDePassOublier.setItemCommandListener(this);
        inscription.setItemCommandListener(this);
      
    }

    
     public void menu() {
       
        
        services.append("Check Mail",null);
        services.append("Compose",null);
        services.append("Check Experiences",null);
        services.append("Check profile",null);
        services.append("Check recommandations",null);
        services.append("Check guides",null);
        services.append("Check Sms",null);
        services.append("Compose Sms",null);
        services.append("Sign Out",null);
        utils.StaticMidlet.disp.setCurrent(services);
        
    }

    public void tryAgain() {
        
        Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
        utils.StaticMidlet.disp.setCurrent(error);
        myTextNom.setString("");
        myTextPass.setString("");
        
    }

    public void commandAction(Command c, Displayable d) {
      if(c==ValidCmd)
{
    Thread myThread=new Thread(this);
    myThread.start();
}
        if(c==List.SELECT_COMMAND)
{if(services.getSelectedIndex()==1)
{ //append("vous avez choisit une liste implicite");
  utils.StaticMidlet.disp.setCurrent(new RechercheUtilisateur());
}
if(services.getSelectedIndex()==0)
{ append("vous avez choisit une liste multiple");
}
if(services.getSelectedIndex()==2)
{ append("vous avez choisit une liste exclusive");
}
if(services.getSelectedIndex()==3)
{ append("vous avez choisit une liste implicite");

}
if(services.getSelectedIndex()==4)
{ append("vous avez choisit une liste implicite");
  
}
if(services.getSelectedIndex()==5)
{ append("vous avez choisit une liste implicite");
 
}
if(services.getSelectedIndex()==6)
{ append("vous avez choisit une liste implicite");
  
}
if(services.getSelectedIndex()==7)
{ append("vous avez choisit une liste implicite");
  
}   
    }
         if (c==exit)
       {
           utils.StaticMidlet.mainMid.notifyDestroyed();
       }
    }

    public void run() {
            try {
            // this will handle our XML
            UtilisateurHandler personnesHandler = new UtilisateurHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            login=myTextNom.getString().trim();
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/login1.php?"+"username="+myTextNom.getString().trim()+"&password="+myTextPass.getString().trim());
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            utilisateur=personnesHandler.getUtilisateur();
             System.out.println(login);
                System.out.println(utilisateur[0].getId());

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
         
         String reponse=sb.toString();
       //  if(reponse.equals("Bienvenue vous etes connectee"))
         if (utilisateur.length>0)
         {
            // System.out.println("Access allowed");
             this.deleteAll();
             utils.StaticMidlet.disp.setCurrent(new Alert("Login correct", "Vous etes connecte", null, AlertType.INFO),new Menumezyen(utilisateur[0].getId()));
           //utils.StaticMidlet.disp.setCurrent(new MenuUser(utils.StaticMidlet.disp,utilisateur[0].getId()));  
          
              //menu();
             
         }
         else {
             //System.out.println("Access denied");
             this.deleteAll();
             tryAgain();
             
         }
         
          myForm.deleteAll();
          myForm.append(sb.toString());
       
    }

    public void commandAction(Command c, Item item) {
      if (item==inscription)
        {
            utils.StaticMidlet.disp.setCurrent(new AjoutUtilisateur());
        }
       if (item==MotDePassOublier)
        {
            utils.StaticMidlet.disp.setCurrent(new SendPasse());
        }
    }
    }
    
