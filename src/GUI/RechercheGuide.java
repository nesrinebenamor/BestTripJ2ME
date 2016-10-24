package GUI;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Entity.Guide;
import Handler.GuideHandler;
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
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 *
 * @author ACER
 */
public class RechercheGuide implements CommandListener, Runnable{
    Display disp;
    Command cmdParse = new Command("guide", Command.SCREEN, 0);
    Command cmdBack = new Command("Retour", Command.BACK, 0);
    Command cmdBack2 = new Command("Retour", Command.BACK, 0);
    Command cmdRecherche = new Command("Rechercher", Command.SCREEN, 0);
    Command cmdRech = new Command("Rechercher", Command.SCREEN, 0);
    TextField myTextCategorie = new TextField("Tapez le nom de votre guide", "", 100, TextField.ANY);
    Guide[] guide;
    List lst = new List("Guide", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos Guide");
    Form rechForm = new Form("Formulaire de recherche");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();
    int id;
    
    String variable;
    DataInputStream dis;
    int ch;
    public RechercheGuide(Display disp){
        this.disp=disp;
        this.startApp();
    }
    public RechercheGuide(String variable)
    {
        this.disp=disp;
        this.startApp();
        this.variable=variable;
    }
    

    public void startApp() {
         f.append("Click Guide to get your guide_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        f.addCommand(cmdRecherche);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        lst.setCommandListener(this);
        lst.addCommand(cmdBack2);
        form.setCommandListener(this);
         form.addCommand(cmdBack);
        form.setCommandListener(this);
        rechForm.append(myTextCategorie);
        rechForm.addCommand(cmdBack);
        rechForm.setCommandListener(this);
        rechForm.addCommand(cmdRech);
        rechForm.setCommandListener(this);
        disp.setCurrent(f);
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
        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
        if (c == cmdBack2) {
            new MenuGuide(disp);
        }
        if (c == cmdRecherche) {
            form.deleteAll();
            disp.setCurrent(rechForm);
        }
        if (c == cmdRech) {
            variable = myTextCategorie.getString().trim();
            form.deleteAll();
            disp.setCurrent(form);
            Thread th1 = new Thread(this);
            th1.start();
        }
    }

    public void run() {
         try {
             
            // this will handle our XML
            GuideHandler guideHandler = new GuideHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/guide1/rechercheguide.php?nom="+variable);
             System.out.println("http://localhost/guide1/rechercheguide.php?nom="+variable);
             
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, guideHandler);
            // display the result
            guide = guideHandler.getGuide();
            if (guide.length > 0) {
                for (int i = 0; i < guide.length; i++) {
                    lst.append(guide[i].getNom()+ " "
                            + guide[i].getResume()+ " "
                            +guide[i].getPrix()+ " "
                            + guide[i].getDate()+ " ",null);
                }
            }
             else{
        Alert error = new Alert("Guide introuvable", "Entrez une categorie valid", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }
}
