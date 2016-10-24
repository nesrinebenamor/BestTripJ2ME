package GUI;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ConvertDate.MyDate;
import javax.microedition.midlet.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
/**
 *
 * @author ACER
 */
public class AjoutGuide implements CommandListener, Runnable{
    Display disp;
    //Form 1
    Form f1 = new Form("Guide");
     TextField tfNom = new TextField("nom", null, 100, TextField.ANY);
    TextField tfResume = new TextField("resume", null, 100, TextField.ANY);
    TextField tfPrix = new TextField("prix", null, 100, TextField.ANY);
    DateField calender = new DateField("date", DateField.DATE, TimeZone.getTimeZone("GMT"));
    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdAfficher = new Command("afficher", Command.SCREEN, 0);
    Command cmdRecherche = new Command("chercher guide", Command.SCREEN, 0);
    Command cmdBack = new Command("cmdBack", Command.BACK, 0);

    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);

    
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/guide1/ajout.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    public AjoutGuide(Display disp){
        this.disp=disp;
        this.startApp();
    }
    public void startApp() {
        f1.append(tfNom);
        f1.append(tfResume);
        f1.append(tfPrix);
        f1.append(calender);
        f1.addCommand(cmdValider);
        f1.addCommand(cmdAfficher);
        f1.addCommand(cmdRecherche);
        f2.addCommand(cmdBack);
        f1.setCommandListener(this);
        
        f2.setCommandListener(this);
        disp.setCurrent(f1);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
        }
        if (c == cmdAfficher) {
            new SuppUpAffGuide(disp);
        }
        if (c == cmdRecherche) {
            new RechercheGuide(disp);
        }
        if (c == cmdBack) {   
            new MenuGuide(disp);
        }
    }

    public void run() {
        try {
                hc = (HttpConnection) Connector.open(url+"?nom="+tfNom.getString().trim()+"&resume="+tfResume.getString().trim()+"&prix="+tfPrix.getString().trim()+"&date="+MyDate.getMyDate(calender.getDate().toString()));
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
