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
public class AjoutRecom implements CommandListener, Runnable  {
    
    
    Display disp;
    //Form 1
    Form f1 = new Form("ajout recommendation");
    TextField tfNom = new TextField("nom", null, 100, TextField.ANY);
    TextField tfContenu = new TextField("contenu", null, 100, TextField.ANY);
    TextField tfEstCompagnie = new TextField("estcompagnie", null, 100, TextField.NUMERIC);
    TextField tfNombre = new TextField("nombre", null, 100, TextField.NUMERIC);
    TextField tfAdresse = new TextField("adresse", null, 100, TextField.ANY);
    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdBack = new Command("cmdBack", Command.BACK, 0);
    Command cmdBack2 = new Command("Retour", Command.BACK, 0);
    Command cmdAffiche = new Command("affiche", Command.SCREEN, 0);

    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);

    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/parsingbesttrip/ajout.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    
    public AjoutRecom(Display disp)
    {
        this.disp=disp;
        this.startApp();
    }

    public void startApp() {
        f1.append(tfNom);
        f1.append(tfContenu);
        f1.append(tfAdresse);
        f1.addCommand(cmdValider);
        f1.setCommandListener(this);
        f1.addCommand(cmdAffiche);
        f1.setCommandListener(this);
        f1.addCommand(cmdBack2);
        f1.setCommandListener(this);
        f2.addCommand(cmdBack);
        f2.setCommandListener(this);
        disp.setCurrent(f1);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
        }
        if (c == cmdBack) {
            
            disp.setCurrent(f1);
        }
        if (c == cmdBack2) {
            
            new ClasseMenuRec(disp);
        }
        if (c == cmdAffiche) {
            
           new AfficherRecommandation(disp);
        }
    }

    public void run() {
        try {
                hc = (HttpConnection) Connector.open(url+"?nom="+tfNom.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&adresse="+tfAdresse.getString().trim());
              System.out.println(url+"?nom="+tfNom.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&adresse="+tfAdresse.getString().trim());
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
            }
    }
    
}


