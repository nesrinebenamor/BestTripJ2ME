/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

//import Entity.Utilisateur;
//import Handler.UtilisateurHandler;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
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
public class ConsulterProfil extends Form implements CommandListener, Runnable{
     Command exit;
     Command update;
      Command delete;
     Command valider;
    private TextField nom;
    private TextField prenom;
    private TextField age;
    private TextField username;
    private TextField password;
    private TextField email;
    private TextField tfNom;
    private TextField tfPrenom;
    private TextField tfAge;
    private TextField tfUsername;
    private TextField tfPassword;
    private TextField tfEmail;
    private Image image;
    private HttpConnection http;
    private DataInputStream dataStream;
    private int ch;
    private String url;
    String login;
    String taswira;
    private StringBuffer str = new StringBuffer("");
    private Utilisateur[] utilisateurs;
     int idUtilisateur ;
     StringBuffer sb = new StringBuffer();
    DataInputStream dis;
    
    
    Displayable nextDisp;

    public ConsulterProfil(int idUtilisateur,Displayable nextDisp) {
        super("Consulter Profil");
        
        this.idUtilisateur=idUtilisateur;
        this.nextDisp=nextDisp;
        
        setTicker(new Ticker("Espace Utilisateur"));

        exit = new Command("Back", Command.EXIT, 1);
        addCommand(exit);
        
         update = new Command("Update", Command.SCREEN, 1);
       addCommand(update);     
        this.setCommandListener(this);
        
        delete = new Command("Desactiver", Command.SCREEN, 1);
       addCommand(delete);     
        this.setCommandListener(this);
        
         valider = new Command("Valider", Command.SCREEN, 1);
     
        
        this.setCommandListener(this);
        
        new Thread(this).start();
    }

    public void commandAction(Command c, Displayable d) {
  if (c==exit)
        {
            utils.StaticMidlet.disp.setCurrent(new Menumezyen(idUtilisateur));
        }
        if (c==update)
        {this.deleteAll();
        removeCommand(update);
        removeCommand(delete);
        append(tfAge);
        append(tfNom);
        append(tfPrenom);
        append(tfUsername);
        append(tfEmail);
        addCommand(valider);
        }
       if (c==valider)
        {try {
            updatePersonne(idUtilisateur);
            this.deleteAll();
            utils.StaticMidlet.disp.setCurrent(new Menumezyen(idUtilisateur));
            
                    } catch (IOException ex) {
          ex.printStackTrace();
      }
        }
        if (c==delete)
        {try {
            deletePersonne(idUtilisateur);     
            this.deleteAll();
            utils.StaticMidlet.disp.setCurrent(new Authentification());
                    } catch (IOException ex) {
          ex.printStackTrace();
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
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/consulter.php?id="+this.idUtilisateur);
            System.out.println(idUtilisateur);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            // display the result
            //System.out.println(idUtilisateur);
       utilisateurs=personnesHandler.getUtilisateur();
//              for (int i = 0; i < conduc.length; i++) {
//                  System.out.println(conduc[i].getNom());
//                    
//
//                }
            
          //  System.out.println(utilisateurs[0].getId());
       
        nom = new TextField("nom", utilisateurs[0].getNom(), 30, TextField.UNEDITABLE);
        prenom = new TextField("prénom", utilisateurs[0].getPrenom(), 30, TextField.UNEDITABLE);
        age = new TextField("Age", utilisateurs[0].getAge1(), 30, TextField.UNEDITABLE);
        email = new TextField("Email", utilisateurs[0].getEmail(), 50, TextField.UNEDITABLE);
       // password = new TextField("Password", utilisateurs[0].getPassword(), 50, TextField.UNEDITABLE);
        username = new TextField("Username", utilisateurs[0].getUsername(), 50, TextField.UNEDITABLE);
         tfNom = new TextField("nom", null, 30, TextField.ANY);
        tfPrenom = new TextField("prénom", null, 30, TextField.ANY);
        tfAge = new TextField("Age", null, 30, TextField.NUMERIC);
        tfEmail = new TextField("Email", null, 50, TextField.EMAILADDR);
       // password = new TextField("Password", utilisateurs[0].getPassword(), 50, TextField.UNEDITABLE);
        tfUsername = new TextField("Username", null, 50, TextField.ANY);
        tfNom.setString(utilisateurs[0].getNom());
        tfPrenom.setString(utilisateurs[0].getPrenom());
        tfAge.setString(utilisateurs[0].getAge1());
        tfUsername.setString(utilisateurs[0].getUsername());
        tfEmail.setString(utilisateurs[0].getEmail());
        taswira=utilisateurs[0].getImage();
            System.out.println(taswira);        
          try {
            image = Image.createImage("/image/"+taswira);
            ImageItem img1=new ImageItem("image Profil", image, ImageItem.LAYOUT_RIGHT,null);
            append(img1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     
            
        
        append(nom);
        append(prenom);
        append(age);
        append(email);
        append(username);
        //append(password);
        
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }   
    }  
     private String updatePersonne(int i) throws IOException {
        String res = "";
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/modifier.php?id="+idUtilisateur+"&nom=" + tfNom.getString().trim() + "&prenom=" + tfPrenom.getString().trim() + "&email=" + tfEmail.getString().trim() + "&username=" + tfUsername.getString().trim() + "&age=" + tfAge.getString().trim());
            System.out.println("http://localhost/parsingBest/modifier.php?id="+idUtilisateur+"&nom=" + tfNom.getString().trim() + "&prenom=" + tfPrenom.getString().trim() + "&email=" + tfEmail.getString().trim() + "&username=" + tfUsername.getString().trim() + "&age=" + tfAge.getString().trim()); 
            dis = new DataInputStream(hc.openDataInputStream());
           while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                this.deleteAll();
                utils.StaticMidlet.disp.setCurrent(new Alert("Votre compte a été modifié avec succés", null, null, AlertType.INFO));
                utils.StaticMidlet.disp.setCurrent(this);
            } 
            sb = new StringBuffer("");
        return res;
    }
     
     private String deletePersonne(int i) throws IOException {
        String res = "";
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/delete.php?id="+idUtilisateur);
            System.out.println("http://localhost/parsingBest/delete.php?id="+idUtilisateur); 
            dis = new DataInputStream(hc.openDataInputStream());
           while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                this.deleteAll();
                 utils.StaticMidlet.disp.setCurrent(new Alert("Votre compte a bien été désactivé", null, null, AlertType.INFO));
                utils.StaticMidlet.disp.setCurrent(this);
            } 
            sb = new StringBuffer("");
        return res;
    }
    
}
