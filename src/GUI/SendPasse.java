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
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.Utilisateur;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class SendPasse extends Form implements CommandListener,Runnable{

    private TextField toWhom;
    private TextField message;
    private TextField tfLogin;
    private Alert alert;
    private Command send, exit;
    MessageConnection clientConn;
    private Form compose;
    String login;
    Utilisateur[] personnes;
    StringBuffer sb ;
    DataInputStream dis;
    int ch;
    String password;
    String email;
    int id;
    private String nom;
    private String prenom;
    private String username;
    private String age;
    
    
    public SendPasse() {
        super("Recuperation mot de passe");
        compose = new Form("Compose Message");
        toWhom = new TextField("To", "", 10, TextField.PHONENUMBER);
        message = new TextField("Message", "", 600, TextField.ANY);
        tfLogin = new TextField("Login", "", 600, TextField.ANY);
        send = new Command("Send", Command.BACK, 0);
        exit = new Command("Exit", Command.SCREEN, 5);
        
        
        append(toWhom);
        append(tfLogin);
        //append(message);
        addCommand(send);
        addCommand(exit);
        
         addCommand(send);
        this.setCommandListener(this);
        addCommand(exit);
        this.setCommandListener(this);
        
    }

    public void commandAction(Command c, Displayable d) {
  if (c == exit) {
             utils.StaticMidlet.disp.setCurrent(new Menumezyen());
        }
        if (c == send) {
            System.out.println("send");
           Thread th = new Thread(this);
            th.start();
//           recupererMotdepasse(login);
            
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
                    password=personnes[i].getPassword();
                    id=personnes[i].getId();
                    email=personnes[i].getEmail();
                    nom=personnes[i].getNom();
                    prenom=personnes[i].getPrenom();
                    username=personnes[i].getUsername();
                    age=personnes[i].getAge1();
                    password=personnes[i].getPassword();
                        
//                    System.out.println(id);
//                    System.out.println(nom);
//                    System.out.println(prenom);
//                    System.out.println(email);
//                    System.out.println(username);
//                    System.out.println(age);
//                    System.out.println(password);
                    //System.out.println("email= "+email);
                   // this.deleteAll();
                    
             utils.StaticMidlet.disp.setCurrent(new Alert("Connection correct", "Vous allez recevoir votre passe par sms", null, AlertType.INFO),new Menumezyen(personnes[0].getId()));
                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
         
         //String reponse=sb.toString();
        // System.out.println(email);
     
        
    }

    public void run() {

          login=tfLogin.getString().trim();
          //System.out.println("ici le login recupere"+login);
          recupererMotdepasse(login);
        //  System.out.println("prob run");
         String mno = toWhom.getString();
        String msg = password;
        if (mno.equals("")) {
            alert = new Alert("Alert");
            alert.setString("Enter Mobile Number!!!");
            alert.setTimeout(2000);
            utils.StaticMidlet.disp.setCurrent(alert);
        } else {
            try {
                clientConn = (MessageConnection) Connector.open("sms://" + mno);
            } catch (Exception e) {
                alert = new Alert("Alert");
                alert.setString("Unable to connect to Station because of network problem");
                alert.setTimeout(2000);
                utils.StaticMidlet.disp.setCurrent(alert);
            }
            try {
                TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                textmessage.setAddress("sms://" + mno);
                textmessage.setPayloadText(msg);
                clientConn.send(textmessage);
                 Alert alert2 = new Alert("Votre message a été envoyé avec succés", "", null, AlertType.INFO);
                alert2.setTimeout(Alert.FOREVER);
                alert2.setString("Envoi avec succés");
                utils.StaticMidlet.disp.setCurrent(alert2);
            } catch (Exception e) {
                Alert alert = new Alert("Alert", "", null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                alert.setString("Unable to send");
                utils.StaticMidlet.disp.setCurrent(alert);
            }
        }
          
          
    }
    
}
