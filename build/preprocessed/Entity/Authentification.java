/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class Authentification extends Form implements CommandListener, Runnable {
    HttpConnection hc;
    DataInputStream ds;
    String url = "";
    Form myForm = new Form("Accueil");
    TextField myTextNom = new TextField("username", "", 100, TextField.ANY);
    TextField myTextPass = new TextField("password", "", 100, TextField.PASSWORD);
    StringBuffer sb = new StringBuffer("");
    Command ValidCmd = new Command("valider", Command.SCREEN, 0);
    mobileapplicationparsingxml.Utilisateur[] utilisateur;
     List services = new List("Choose one", Choice.EXCLUSIVE);
     String login;
     

    public Authentification(String title) {
        super(title);
        
         addCommand(ValidCmd);
         this.setCommandListener(this);
         append(myTextNom);
         append(myTextPass);
         this.setCommandListener(this);
   
      
    }
    
     public void menu() {
       
        append("Check Mail");
        append("Compose");
        append("Check Experiences");
        append("Check profile");
        append("Check recommandations");
        append("Check guides");
        append("Check Sms");
        append("Compose Sms");
        append("Sign Out");
        
        
    }

    public void tryAgain() {
        
        Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
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
{if(services.getSelectedIndex()==0)
{ append("vous avez choisit une liste implicite");
  
}
if(services.getSelectedIndex()==1)
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
           //  System.out.println(login);

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
         
         String reponse=sb.toString();
       //  if(reponse.equals("Bienvenue vous etes connectee"))
         if (utilisateur.length>0)
         {
            // System.out.println("Access allowed");
             this.deleteAll();
              menu();
             
         }
         else {
             //System.out.println("Access denied");
             this.deleteAll();
             tryAgain();
         }
         
          myForm.deleteAll();
          myForm.append(sb.toString());
       
    }
    }
    
