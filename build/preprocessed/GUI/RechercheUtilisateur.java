/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.DataInputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Ticker;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.Utilisateur;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class RechercheUtilisateur extends Form implements CommandListener,Runnable{
    
    Command cmdParse ;
    Command cmdBack ;
    Command cmdRecherche ;
    Command cmdRech ;
    TextField myTextNom ;
    
    private TextField nom;
    private TextField prenom;
    private TextField age;
    private TextField username;
    private TextField password;
    private TextField email;

    Utilisateur[] personnes;
    List lst ;
    Form f ;
    Form form ;
    Form rechForm ;
    Form loadingDialog ;
    StringBuffer sb ;
    int id;
    String variable;
    DataInputStream dis;
    int ch;
    
    public RechercheUtilisateur() {
        super("afficher utilisateur");
        
        setTicker(new Ticker("Consulter Profil Utilisateur"));
        
    //cmdParse = new Command("Personnes", Command.SCREEN, 0);
    cmdBack = new Command("Back", Command.BACK, 0);
   // cmdRecherche = new Command("Rechercher", Command.SCREEN, 0);
    cmdRech = new Command("Search", Command.SCREEN, 0);
    myTextNom = new TextField("Tapez un username", "", 100, TextField.ANY);    
    lst = new List("Utilisateur", List.IMPLICIT);
    f = new Form("Accueil");
    form = new Form("Infos Personne");
    rechForm = new Form("Formulaire de recherche");
    loadingDialog = new Form("Please Wait");
    sb = new StringBuffer();
    
    
    
    
    
    
        //append("Click Utilisateurs to get your utilisateurs_list");
//        addCommand(cmdParse);
//        setCommandListener(this);
     //   addCommand(cmdRecherche);
       // setCommandListener(this);
        setCommandListener(this);
        addCommand(cmdBack);
        setCommandListener(this);
        append(myTextNom);
        addCommand(cmdBack);
        setCommandListener(this);
        addCommand(cmdRech);
        setCommandListener(this);
        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
//         if (c == cmdParse) {
//            utils.StaticMidlet.disp.setCurrent(loadingDialog);
//            Thread th = new Thread(this);
//            th.start();
//        }
        if (c == cmdBack) {
            form.deleteAll();
            utils.StaticMidlet.disp.setCurrent(new Menumezyen());
        }
//        if (c == cmdRecherche) {
//            form.deleteAll();
//            utils.StaticMidlet.disp.setCurrent(rechForm);
//
//        }
        if (c == cmdRech) {
            variable = myTextNom.getString().trim();
            form.deleteAll();
            //utils.StaticMidlet.disp.setCurrent(form);
            this.deleteAll();
            Thread th1 = new Thread(this);
            th1.start();
        }
    }

    public void run() {
           try {
            // this will handle our XML
            UtilisateurHandler personnesHandler = new UtilisateurHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/rechercher.php?username=" + variable);
               System.out.println("http://localhost/parsingBest/rechercher.php?username=" + variable);
               System.out.println(variable);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            // display the result
            personnes = personnesHandler.getUtilisateur();

            if (personnes.length > 0) {
                for (int i = 0; i < personnes.length; i++) {
                    
                    
        nom = new TextField("Nom", personnes[i].getNom(), 30, TextField.UNEDITABLE);
        prenom = new TextField("Prénom", personnes[i].getPrenom(), 30, TextField.UNEDITABLE);
        age = new TextField("Age", personnes[i].getAge1(), 30, TextField.UNEDITABLE);
        email = new TextField("Email", personnes[i].getEmail(), 50, TextField.UNEDITABLE);
       // password = new TextField("Password", personnes[i].getPassword(), 50, TextField.UNEDITABLE);
        username = new TextField("Username", personnes[i].getUsername(), 50, TextField.UNEDITABLE);
                    
                    lst.append(personnes[i].getNom() + " "
                            + personnes[i].getPrenom() + " "
                            + personnes[i].getEmail() + " "
                            + personnes[i].getPassword() + " "
                          //  + personnes[i].getUsername() + " "
                            + personnes[i].getAge1() + " ", null);
                }
        append(nom);
        append(prenom);
        append(age);
        append(email);
        append(username);
       // append(password);
            }
             else{
        Alert error = new Alert("Utilisateur introuvable", "Entrez un username valid", null, AlertType.ERROR);
        error.setTimeout(Alert.FOREVER);
        utils.StaticMidlet.disp.setCurrent(new Alert("Utilisateur introuvable", "Entrez un username valid", null, AlertType.ERROR));
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        utils.StaticMidlet.disp.setCurrent(this);
    }
    
}
