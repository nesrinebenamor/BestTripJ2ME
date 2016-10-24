/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Ticker;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.Utilisateur;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class SendMail extends Form implements CommandListener, Runnable {

    Form f_gps ;
    Command valider ;
    Command EnvoiMail ;
    Command back ;
    Command backform ;
    TextField tMail ;
    TextField tPWD ;
    TextField tSujet ;
    TextField tMessage ;
    String login="test";
    Utilisateur[] personnes;
    StringBuffer sb ;
    DataInputStream dis;
    int ch;
    String email;
    
    Alert alert;

    public SendMail() {
        super("Contactez nous!!");
        f_gps = new Form("Contactez nous !!");
        valider = new Command("Valider", Command.OK, 0);
        EnvoiMail = new Command("Valider", Command.OK, 0);
        back = new Command("Précédant", Command.EXIT, 0);
        backform = new Command("Précédant", Command.EXIT, 0);
        tMail = new TextField("Mail", null, 50, TextField.ANY);
        tPWD = new TextField("mot de passe", null, 50, TextField.PASSWORD);
        tSujet = new TextField("Sujet", null, 50, TextField.ANY);
        tMessage = new TextField("Message", null, 50, TextField.ANY);
        Ticker yTicker=new Ticker("Send Mail");
        
         append(tMail);
         append(tPWD);
         append(tSujet);
         append(tMessage);
         setTicker(yTicker);
       
         addCommand(EnvoiMail);
         this.setCommandListener(this);
         addCommand(back);
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
      if ( c==EnvoiMail){
            
           Thread myThread=new Thread(this);
           myThread.start();            
        }else if (c== back){
             utils.StaticMidlet.disp.setCurrent(new Menumezyen());
        }
    }
    
    public void recupererMotdepasse(String login)
    {    try {
            // this will handle our XML
            UtilisateurHandler personnesHandler = new UtilisateurHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/recuperer.php?"+"username="+login);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            personnes = personnesHandler.getUtilisateur();  
                System.out.println("login="+login);
             if (personnes.length > 0) {
                for (int i = 0; i < personnes.length; i++) {    
                    email=personnes[i].getEmail();  
                    System.out.println(email);
             utils.StaticMidlet.disp.setCurrent(new Alert("Connection correct", "Votre Mail a été envoyé avec succés", null, AlertType.INFO),new Menumezyen(personnes[0].getId()));
                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        } 
    }
    
    
     private String sendMail() throws IOException {
       
        String urlX="http://localhost/Aut/sendMail.php?pwd="+tPWD.getString()+"&user="+tMail.getString()+"&sub="+tSujet.getString()+"&msg="+tMessage.getString();
         System.out.println("http://localhost/Aut/sendMail.php?pwd="+tPWD.getString()+"&user="+tMail.getString()+"&sub="+tSujet.getString()+"&msg="+tMessage.getString());
        HttpConnection hm = (HttpConnection) Connector.open(urlX);
        DataInputStream dis = new DataInputStream(hm.openDataInputStream());
        System.out.println("ckldncdkl");
        
        return null;
       
    }
     

    public void run() {
       try {
             sendMail(); //To change body of generated methods, choose Tools | Templates.
             utils.StaticMidlet.disp.setCurrent(new Alert("Votre mail est en cours d'envoi.."));
         } catch (IOException ex) {
             ex.printStackTrace();
         }
    }

}
