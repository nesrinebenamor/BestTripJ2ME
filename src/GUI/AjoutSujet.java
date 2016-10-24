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

/**
 *
 * @author esprit
 */
public class AjoutSujet implements CommandListener, Runnable{
    
     Display disp;
    //Display disp1 = Display.getDisplay(this);
    //Form 1
    //Form S =new Form("slpash");
    Form FormImage = new Form("");
    Form f1 = new Form("Ajout Sujet");
    TextField tfTitre = new TextField("titre", null, 100, TextField.ANY);
    TextField tfText = new TextField("text", null, 100, TextField.ANY);
    TextField tfCategorie = new TextField("categorie", null, 100, TextField.ANY);
    Command cmdBack2 = new Command("Retour", Command.BACK, 0);
    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdBack = new Command("Retour", Command.BACK, 0);
    Command cmdAfficher = new Command("afficher", Command.SCREEN, 0);

    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);

    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/pars/ajout.php";
    StringBuffer sb = new StringBuffer();
    int ch;

    public AjoutSujet(Display disp) {
        this.disp = disp;
        this.startApp();
    }
    
    
    public void startApp() {
        
        
        /*disp.setCurrent(new SplashScreen(this));
        try {
      Player player = Manager.createPlayer(getClass().getResourceAsStream("/audio/test-wav.wav"), "audio/x-wav");
      player.start();
    } catch (Exception e)
    {
      e.printStackTrace();
    }*/
       
        
        f1.append(tfTitre);
        f1.append(tfText);
        f1.append(tfCategorie);
        f1.addCommand(cmdValider);
        f1.setCommandListener(this);
        f1.addCommand(cmdAfficher);
        f1.setCommandListener(this);
        f2.addCommand(cmdBack);
        f1.setCommandListener(this);
        f1.addCommand(cmdBack2);
        f2.setCommandListener(this);
        disp.setCurrent(f1);
        //disp.setCurrent(S);
        
    }

    public void commandAction(Command c, Displayable d) {
if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
            
        }
if(c == cmdAfficher) {
            new AfficheSujet(disp);
            
        }
if(c == cmdBack2) {
            new ClasseMenu(disp);
            
        }
        if (c == cmdBack) {
            
            disp.setCurrent(f1);
        }    }

    public void run() {
try {
                hc = (HttpConnection) Connector.open(url+"?titre="+tfTitre.getString().trim()+"&text="+tfText.getString().trim()+"&categorie="+tfCategorie.getString().trim());
                dis = new DataInputStream(hc.openDataInputStream());
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                }
                if ("OK".equals(sb.toString().trim())) {
                    disp.setCurrent(f2);
                }else{
                    disp.setCurrent(f3);
                }
                sb = new StringBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }    }

    
    
}
