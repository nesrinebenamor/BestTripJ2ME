/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Entity.Sujet;
import Handler.SujetHandler;
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
 * @author esprit
 */
public class RechercheSujet implements CommandListener, Runnable{
     Display disp ;
    Command cmdBack2 = new Command("Retour", Command.SCREEN, 0);
    Command cmdBack = new Command("Retour", Command.BACK, 0);
    Command cmdBack3 = new Command("Retour", Command.BACK, 0);
    Command cmdRecherche = new Command("Rechercher", Command.SCREEN, 0);
    Command cmdRech = new Command("Rechercher", Command.SCREEN, 0);
    TextField myTextCategorie = new TextField("Tapez une categorie", "", 100, TextField.ANY);
    Sujet[] sujet;
    List lst = new List("Sujet", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos Sujet");
    Form rechForm = new Form("Formulaire de recherche");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();
    int id;
    String variable;
    DataInputStream dis;
    int ch;
    
     public RechercheSujet(){
        
    }
     
      public RechercheSujet(String variable)
    {
        this.variable=variable;
    }
      
      public RechercheSujet(Display disp) {
        this.disp = disp;
        this.startApp();
    }
      
      public void startApp() {
        
         f.append("Click Sujet to get your sujet_list");
        f.addCommand(cmdBack2);
        f.setCommandListener(this);
        f.addCommand(cmdRecherche);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        form.setCommandListener(this);
        rechForm.append(myTextCategorie);
        rechForm.addCommand(cmdBack);
        rechForm.setCommandListener(this);
        rechForm.addCommand(cmdRech);
        rechForm.setCommandListener(this);
        lst.setCommandListener(this);
        lst.addCommand(cmdBack3);
        disp.setCurrent(f);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdBack2) {
            new ClasseMenu(disp);
        }
        if (c == cmdBack3) {
            new ClasseMenu(disp);
        }
        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
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
            SujetHandler sujetHandler = new SujetHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pars/rechercheSujet.php?categorie="+variable);
            System.out.println("http://localhost/pars/rechercheSujet.php?categorie="+variable);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            
            parser.parse(dis, sujetHandler);
            // display the result
            sujet = sujetHandler.getSujet();
            System.out.println("testrecherche");
            if (sujet.length > 0) {
                for (int i = 0; i < sujet.length; i++) {
                    lst.append(sujet[i].getTitre()+ " "
                            + sujet[i].getText()+ " "
                            + sujet[i].getCategorie()+ " ", null);
                }
            }
             else{
        Alert error = new Alert("Sujet introuvable", "Entrez une categorie valid", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }
    
}
