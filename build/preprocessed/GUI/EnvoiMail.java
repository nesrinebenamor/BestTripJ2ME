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
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author esprit
 */
public class EnvoiMail extends Form implements CommandListener,Runnable{
     Form f1 ;
    TextField tfFrom ;
    TextField tfTo;
    TextField tfObject;
    TextField tfMessage;
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/parsingBest/sendmailghaya.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    int id;
   
    Command cmdValider ;
    Command cmdQuitter ;

    public EnvoiMail() {
        super("Send Mail");
        f1 = new Form("Inscription");
    tfFrom = new TextField("From", null, 100, TextField.ANY);
    tfTo = new TextField("To", null, 100, TextField.ANY);
    tfObject = new TextField("Object", null, 100, TextField.ANY);
    tfMessage = new TextField("Message", null, 100, TextField.ANY);
    cmdValider = new Command("Envoyer", Command.SCREEN, 0);
    cmdQuitter = new Command("Exit", Command.BACK, 0);
    
     append(tfFrom);
     append(tfTo);
     append(tfObject);
     append(tfMessage);
     addCommand(cmdValider);
     addCommand(cmdQuitter);
     this.setCommandListener(this);
     addCommand(cmdQuitter);
     this.setCommandListener(this);
        
   
    }

    public void commandAction(Command c, Displayable d) {
          if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
        }
         if (c == cmdQuitter) {
            utils.StaticMidlet.disp.setCurrent(new Menumezyen(id));
        }
    }

    public void run() {
         try {
            hc = (HttpConnection) Connector.open(url);
             System.out.println(url);
            dis = new DataInputStream(hc.openDataInputStream());
            while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("Message has been sent.".equals(sb.toString().trim())) {
                 utils.StaticMidlet.disp.setCurrent(new Alert("succes","Mail envoyé",null, AlertType.INFO),new Authentification());
            } else {
                 utils.StaticMidlet.disp.setCurrent(new Alert("erreur","Mail non envoyé",null, AlertType.ERROR));
            }
            sb = new StringBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
