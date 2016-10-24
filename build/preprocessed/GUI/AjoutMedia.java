package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import java.util.TimeZone;



/**
 * @author Samsung
 */
public class AjoutMedia implements CommandListener, Runnable,ItemCommandListener{

    Display disp ;
  //  private DateField calender;  
  private static final int DATE = 0;
  
    //Form 1
    Form f1 = new Form("Ajout Media");
    TextField tfDescI = new TextField("Description Photo", null, 100, TextField.ANY);
    TextField tfDescV = new TextField("Description Video", null, 100, TextField.ANY);
    
         
     private static final Command CMD_PRESS = new Command("Press", Command.ITEM, 1); 
     private static final Command CMD_PRESS1 = new Command("Press", Command.ITEM, 1); 
     StringItem button = new StringItem("choisir une Photo", "", Item.BUTTON);
     StringItem button1 = new StringItem("choisir une Video", "", Item.BUTTON);
     
    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdBack = new Command("Terminé", Command.BACK, 0);
    
   
    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);

    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    //String url = "http://localhost/experience/ajout.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    int id=91;
    String id_exp;
    String description;
     String descriptionVid;
    public AjoutMedia(Display disp,String id_exp,int id) {
        this.disp = disp;
        this.id=id;
        this.id_exp=id_exp;
        this.startApp();
        
    }
    
    public void startApp() {
        f1.append(tfDescI);
        f1.append(button);
        f1.append(tfDescV);

        f1.append(button1);
        button.setDefaultCommand(CMD_PRESS);
        button.setItemCommandListener(this);
        
        button1.setDefaultCommand(CMD_PRESS1);
        button1.setItemCommandListener(this);
        //f1.addCommand(cmdValider);
        f1.setCommandListener(this);
        f1.addCommand(cmdBack);
        f2.setCommandListener(this);
        disp.setCurrent(f1);
        
        
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {

        if (c == cmdBack) {
            
         new Thread(
            new Runnable() {

                public void run() {
                    
                    new experienceParsing(disp, id);
                }
            }
            ).start();   
        }
    }

    public void run() {
        try {

               
            
                //System.out.println("date formatted :"+dateStr);
              
//                hc = (HttpConnection) Connector.open(
//                        url+"?titre="+tfTitre.getString().trim()+"&contenu="+tfContenu.getString().trim()
//                           +"&pays="+paysData[tfPays.getSelectedIndex()].trim()
//                           +"&type="+typeData[tfType.getSelectedIndex()].trim()
//                           +"&climat="+climatData[tfClimat.getSelectedIndex()].trim()
//                           +"&depense="+tfDepense.getString().trim()
//                           );
             
                //System.out.println("?titre="+tfTitre.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&pays="+tfPays.getString().trim()+"&type="+typeData[tfType.getSelectedIndex()].trim()+"&climat="+tfClimat.getString().trim()+"&depense="+tfDepense.getString().trim());
                dis = new DataInputStream(hc.openDataInputStream());
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                }
                if ("OK".equals(sb.toString().trim())) {
                    //disp.setCurrent(f2);
                    new experienceParsing(disp,id);
                }else{
                    disp.setCurrent(f3);
                }
                sb = new StringBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    public void commandAction(Command c, Item item) {
        if (c == CMD_PRESS) {
            String text = "Do an action...";
            Alert a = new Alert("Action", text, null, AlertType.INFO);
            description=tfDescI.getString().trim();
            //disp.setCurrent(a);
            new Thread(
            new Runnable() {

                public void run() {
                    System.out.println(description);
                    new FilePhoto(disp,id_exp,description);
                }
            }
            ).start();
            
        }
           if (c == CMD_PRESS1) {
            String text = "Do an action...";
            Alert a = new Alert("Action", text, null, AlertType.INFO);
            descriptionVid=tfDescV.getString().trim();
            //disp.setCurrent(a);
            new Thread(
            new Runnable() {

                public void run() {
                    System.out.println(descriptionVid);
                    new FileVideo(disp,id_exp,descriptionVid);
                }
            }
            ).start();
            
        }
    }
}
