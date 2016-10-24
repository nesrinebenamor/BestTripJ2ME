/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Recommandation;
import Handler.RecommandationHandler;
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
public class RechercheRecom implements CommandListener, Runnable {
    
    Display disp ;
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Command cmdRecherche = new Command("Rechercher", Command.SCREEN, 0);
    Command cmdRech = new Command("Search", Command.SCREEN, 0);
    TextField myTextNom = new TextField("Tapez un username", "", 100, TextField.ANY);
    Recommandation[] recommandation;
    List lst = new List("Recommandation", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos Recommandation");
    Form rechForm = new Form("Formulaire de recherche");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();
    int id;
    String variable;
    DataInputStream dis;
    int ch;
    
     public RechercheRecom(){
        
    }
    
    public RechercheRecom(String variable)
    {
        this.variable=variable;
    }
    
    public RechercheRecom(Display disp)
    {
        this.disp=disp;
        this.startApp();
    }
 

    public void startApp() {
        f.addCommand(cmdRecherche);
        f.setCommandListener(this);
        lst.addCommand(cmdBack);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        form.setCommandListener(this);
        rechForm.append(myTextNom);
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
        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
        if (c == cmdRecherche) {
            form.deleteAll();
            disp.setCurrent(rechForm);
        }
        if (c == cmdRech) {
            variable = myTextNom.getString().trim();
            form.deleteAll();
            disp.setCurrent(form);
            Thread th1 = new Thread(this);
            th1.start();
        }
    }

     public void run() {
        try {
            // this will handle our XML
            RecommandationHandler recommandationHandler = new RecommandationHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
           
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingbesttrip/recherche.php?Nom=" + variable);
            System.out.println("http://localhost/parsingbesttrip/recherche.php?Nom=" + variable);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, recommandationHandler);
            // display the result
            recommandation = recommandationHandler.getRecommandation();
            if (recommandation.length > 0) {
                for (int i = 0; i < recommandation.length; i++) {
                    lst.append(recommandation[i].getNom() + " "
                            + recommandation[i].getContenu()+ " "
                            + recommandation[i].getAdresse()+ " "
                           , null);
                }
            }
             else{
        Alert error = new Alert("Utilisateur introuvable", "Entrez un username valid", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }
//    private String showPersonne(int i) {
//        String res = "";
//        if (recommandation.length > 0) {
//            sb.append("* ");
//            sb.append("\n");
//            sb.append("* ");
//            sb.append(recommandation[i].getNom());
//            sb.append("\n");
//            sb.append("* ");
//            sb.append(recommandation[i].getContenu());
//            sb.append("\n");
//            sb.append("* ");
//            sb.append(recommandation[i].getAdresse());
//            sb.append("\n");
//
//        }
//        res = sb.toString();
//        sb = new StringBuffer("");
//        return res;
//  }
    
}
     
