/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplicationparsingxml;

import GUI.*;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
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
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author esprit
 */
public class AjoutUtilisateur extends Form implements CommandListener,Runnable{
    
       //Form 1
    Form f1 ;
    TextField tfNom ;
    TextField tfPrenom;
    TextField tfUsername ;
    TextField tfPassword ;
    TextField tfAge ;
    TextField tfEmail ;
    Command cmdValider ;
    Command cmdConnexion ;
    Command cmdBack ;
    String login;  
    Form f2 ;
    Form f3;
    Alert alerta;
    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/parsingBest/ajout.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    int id;
   // private Resources r;
     String image="/default.png";

    public AjoutUtilisateur() {
        super("inscription");
           //Form 1
    f1 = new Form("Inscription");
    tfNom = new TextField("nom", null, 100, TextField.ANY);
    tfPrenom = new TextField("prenom", null, 100, TextField.ANY);
    tfUsername = new TextField("username", null, 100, TextField.ANY);
    tfPassword = new TextField("password", null, 100, TextField.PASSWORD);
    tfAge = new TextField("age", null, 100, TextField.NUMERIC);
    tfEmail = new TextField("email", null, 100, TextField.EMAILADDR);
    cmdValider = new Command("valider", Command.SCREEN, 0);
    cmdConnexion = new Command("connexion", Command.SCREEN, 0);
    cmdBack = new Command("Exit", Command.BACK, 0);
    f2 = new Form("Welcome");
    f3 = new Form("Erreur");
    alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);
    String image="default.png";
    
//     try {
//            r = Resources.open("/LWUITtheme.res");
//            UIManager.getInstance().addThemeProps(r.getTheme("LWUITDefault"));
//        } catch (IOException ex) {
//            ex.getMessage();
//        }
        append(tfNom);
        append(tfPrenom);
        append(tfAge);
        append(tfEmail);
        append(tfUsername);
        append(tfPassword);
        addCommand(cmdValider);
         addCommand(cmdConnexion);
        this.setCommandListener(this);
        addCommand(cmdBack);
        this.setCommandListener(this);
        
    }

    public void commandAction(Command c, Displayable d) {
         if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
        }
        if (c == cmdBack) {
            utils.StaticMidlet.disp.setCurrent(new Menu());
        }
         if (c == cmdConnexion) {
            utils.StaticMidlet.disp.setCurrent(new Authentification());
        }
    }

    public void run() {
          try {
            hc = (HttpConnection) Connector.open(url + "?nom=" + tfNom.getString().trim() + "&prenom=" + tfPrenom.getString().trim() + "&email=" + tfEmail.getString().trim() + "&username=" + tfUsername.getString().trim() + "&password=" + tfPassword.getString().trim() + "&age=" + tfAge.getString().trim()+"&image=" +image);
              System.out.println("?nom=" + tfNom.getString().trim() + "&prenom=" + tfPrenom.getString().trim() + "&email=" + tfEmail.getString().trim() + "&username=" + tfUsername.getString().trim() + "&password=" + tfPassword.getString().trim() + "&age=" + tfAge.getString().trim()+"&image=" +image);
            dis = new DataInputStream(hc.openDataInputStream());
            login=tfUsername.getString().trim();
            while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                //disp.setCurrent(f2); 
                //m.startApp();
                 utils.StaticMidlet.disp.setCurrent(new Alert("succes","Inscription effectuer avec succée",null, AlertType.INFO),new Authentification());
            } else {
                 utils.StaticMidlet.disp.setCurrent(new Alert("erreur","Inscription n'a pas été effectué",null, AlertType.ERROR));
            }
            sb = new StringBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
