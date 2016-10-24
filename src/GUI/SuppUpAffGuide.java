package GUI;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ConvertDate.MyDate;
import Entity.Guide;
import Handler.GuideHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.TimeZone;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

import javax.microedition.midlet.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 *
 * @author ACER
 */
public class SuppUpAffGuide implements CommandListener, Runnable{
    HttpConnection hc1;
    String url = "http://localhost/guide1/supp.php";
    String url1 = "http://localhost/guide1/modif.php";
     Display disp;
    Command cmdParse = new Command("Guides", Command.SCREEN, 0);
    Command cmdBack = new Command("Retour", Command.BACK, 0);
    Command cmdBack2 = new Command("Retour", Command.BACK, 0);
     Command cmdSupp = new Command("Supp", Command.EXIT, 0);
    Guide[] guides;
    List lst = new List("Guides", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos Guide");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();
    int id;
    Command cmdUpdate = new Command("Update", Command.SCREEN, 0);
    Command cmdModif = new Command("Modifier", Command.SCREEN, 0);

Form modifForm=new Form("Modifier les donnees");
 TextField tfNom = new TextField("nom", null, 100, TextField.ANY);
   TextField tfResume = new TextField("resume", null, 100, TextField.ANY);
    TextField tfPrix = new TextField("prix", null, 100, TextField.ANY);
    DateField calender = new DateField("date", DateField.DATE, TimeZone.getTimeZone("GMT"));
    private String nom;
    private float prix;
    private float resume;
    private String date;
public SuppUpAffGuide(Display disp){
        this.disp=disp;
        this.startApp();
    }
    public void startApp() {
         f.append("Click GUIDES to get your guides_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        lst.setCommandListener(this);
        lst.addCommand(cmdBack2);
        form.addCommand(cmdSupp);
        form.setCommandListener(this);
        disp.setCurrent(f);
        modifForm.append(tfNom);
         modifForm.append(tfResume);
        modifForm.append(tfPrix);
        modifForm.append(calender);
         modifForm.addCommand(cmdModif);
        modifForm.setCommandListener(this);
       form.addCommand(cmdUpdate);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdParse) {
            disp.setCurrent(loadingDialog);
            Thread th = new Thread(this);
            th.start();
        }

        if (c == List.SELECT_COMMAND) {
            form.append("Informations Guide: \n");
            form.append(showGuide(lst.getSelectedIndex()));
            disp.setCurrent(form);
        }

        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
        if (c == cmdBack2) {
            new MenuGuide(disp);
        }
         if (c == cmdSupp) {
          supprimer();
          lst.deleteAll();
          //form.deleteAll();
           disp.setCurrent(lst);
           Thread th1 = new Thread(this);
            th1.start();
           // disp.setCurrent(loadingDialog);
            
        }
         if (c == cmdUpdate) {
             form.deleteAll();
             disp.setCurrent(modifForm);
            }
        if (c == cmdModif) {
            
                   update();
       lst.deleteAll();
       disp.setCurrent(lst);
       Thread th1=new Thread(this);
       th1.start();
            }

    }
    public void supprimer()
    {
              try {
                hc1 = (HttpConnection) Connector.open(url+"?id="+id);

                //form.deleteAll();
                     
            
                 DataInputStream dis = new DataInputStream(hc1.openDataInputStream());
                 int ch;
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                }
           
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    private String update(){
        String res = "";
        //if (experiences.length > 0) {
            try {
                HttpConnection hc = (HttpConnection) Connector.open(url1+"?id="+id+"&nom="+tfNom.getString().trim()+"&resume="+tfResume.getString().trim()+"&prix="+tfPrix.getString().trim()+"&date="+MyDate.getMyDate(calender.getDate().toString()));
                System.out.println(id);
                //System.out.println(url1+"?id="+id+"&nom="+tfNom.getString().trim()+"&resume="+tfResume.getString().trim()+"&prix="+tfPrix.getString().trim()+"&date="+calender.getDate().trim());
                DataInputStream dis = new DataInputStream(hc.openDataInputStream());
                int ch;
                while ((ch = dis.read()) != -1) {
                    sb.append((char) ch);
                }
                if ("OK".equals(sb.toString().trim())) {
                    disp.setCurrent(form);
                }
                sb = new StringBuffer("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        //}
        return res;
    }

    public void run() {
        try {
            // this will handle our XML
            GuideHandler guideHandler = new GuideHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/guide1/getXmlPersons_Attributes.php");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, guideHandler);
            // display the result
            guides = guideHandler.getGuide();

            if (guides.length > 0) {
                for (int i = 0; i < guides.length; i++) {
                    lst.append(guides[i].getNom()+" "+guides[i].getResume()+" "+guides[i].getPrix()+" "+guides[i].getDate(), null);

                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }
    private String showGuide(int i) {
        String res = "";
        if (guides.length > 0) {
            sb.append("* ");
            sb.append(guides[i].getId());
            id=guides[i].getId();
            sb.append("\n");
            sb.append("* ");
            sb.append(guides[i].getNom());
            tfNom.setString(guides[i].getNom());
            sb.append("\n");
            sb.append("* ");
            sb.append(guides[i].getResume());
            tfPrix.setString(guides[i].getPrix1());
            sb.append("\n");
            sb.append("* ");
            sb.append(guides[i].getPrix());
            tfResume.setString(guides[i].getResume());
            sb.append("\n");
            sb.append("* ");
            sb.append(guides[i].getDate());
            sb.append("\n");
            sb.append("* ");
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }
}
