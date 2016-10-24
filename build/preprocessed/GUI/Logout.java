/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.DataInputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.Utilisateur;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class Logout extends Form implements CommandListener,Runnable{

    HttpConnection hc;
    DataInputStream ds;
    String url = "";
    Form myForm ;
    Form nouvForm ;
    StringBuffer sb ;
    Command DeconnCmd ;
    Utilisateur[] utilisateur;
    String login;
    
    public Logout() {
        super("inscription");
        
    myForm = new Form("Accueil");
    nouvForm = new Form("Veuillez vous connecter");
    sb = new StringBuffer("");
    DeconnCmd = new Command("deconnexion", Command.SCREEN, 0);
    addCommand(DeconnCmd);
    setCommandListener(this);
    utils.StaticMidlet.disp.setCurrent(this);
  
    }

    public void commandAction(Command c, Displayable d) {
            if(c==DeconnCmd)
{
    Thread myThread=new Thread(this);
    myThread.start();
}
    }

    public void run() {
     try {
            // this will handle our XML
            UtilisateurHandler personnesHandler = new UtilisateurHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/deconnexion.php");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            utilisateur=personnesHandler.getUtilisateur();

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
         
         String reponse=sb.toString();
       //  if(reponse.equals("Bienvenue vous etes connectee"))
          this.deleteAll();
          this.append(sb.toString());
          
        this.deleteAll();
             utils.StaticMidlet.disp.setCurrent(new Alert("Logout correct", "Vous etes Deconnecte", null, AlertType.INFO),new Authentification());  
    }
    
}
