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
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mobileapplicationparsingxml.Utilisateur;
import mobileapplicationparsingxml.UtilisateurHandler;

/**
 *
 * @author esprit
 */
public class ParsingXml extends Form implements CommandListener, Runnable{

    Command cmdParse ;
    Command cmdBack ;
    Command cmdDelete ;
    TextField tfNom ;
    TextField tfPrenom ;
    TextField tfUsername;
    TextField tfPassword ;
    TextField tfAge ;
    TextField tfEmail ;
    Command cmdUpdate ;
    Command cmdModif ;
    Utilisateur[] personnes;
    List lst ;
    Form f ;
    Form form ;
    Form modifForm;
    Form loadingDialog ;
    StringBuffer sb ;
    int id;
    DataInputStream dis;
    int ch;
    public ParsingXml() {
        super("consultation");
        
        cmdParse = new Command("Personnes", Command.SCREEN, 0);
        cmdBack = new Command("Back", Command.BACK, 0);
        cmdDelete = new Command("Delete", Command.SCREEN, 0);
        tfNom = new TextField("nom", null, 100, TextField.ANY);
        tfPrenom = new TextField("prenom", null, 100, TextField.ANY);
        tfUsername = new TextField("username", null, 100, TextField.ANY);
        tfPassword = new TextField("password", null, 100, TextField.PASSWORD);
        tfAge = new TextField("age", null, 100, TextField.NUMERIC);
        tfEmail = new TextField("email", null, 100, TextField.EMAILADDR);
        cmdUpdate = new Command("Update", Command.SCREEN, 0);
        cmdModif = new Command("Modifier", Command.SCREEN, 0);
        lst = new List("Utilisateur", List.IMPLICIT);
        f = new Form("Accueil");
        form = new Form("Infos Personne");
        modifForm=new Form("Entrer les nouvelles infos");
        loadingDialog = new Form("Please Wait");
        sb = new StringBuffer();
        
        f.append("Click Utilisateurs to get your utilisateurs_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        form.setCommandListener(this);
        form.addCommand(cmdDelete);
        form.setCommandListener(this);
        form.addCommand(cmdUpdate);
        form.setCommandListener(this);
        modifForm.addCommand(cmdModif);
        modifForm.append(tfNom);
        modifForm.append(tfPrenom);
        modifForm.append(tfEmail);
        modifForm.append(tfUsername);
        modifForm.append(tfAge);
        modifForm.append(tfPassword);
        utils.StaticMidlet.disp.setCurrent(f);
        modifForm.setCommandListener(this);
        this.setCommandListener(this);
    
   
    }

    public void commandAction(Command c, Displayable d) {
         if (c == cmdParse) {
            utils.StaticMidlet.disp.setCurrent(loadingDialog);
            Thread th = new Thread(this);
            th.start();
        }
        if (c == List.SELECT_COMMAND) {
            form.append("Informations Personne: \n");
            form.append(showPersonne(lst.getSelectedIndex()));
             utils.StaticMidlet.disp.setCurrent(form);
        }
         if (c == cmdDelete) {
                try {
                    deletePersonne(id);
//                try {
//                    form.append(deletePersonne(lst.getSelectedIndex()));
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // disp.setCurrent(form);
       lst.deleteAll();
       utils.StaticMidlet.disp.setCurrent(lst);
       Thread th1=new Thread(this);
       th1.start();
            }

        if (c == cmdBack) {
            form.deleteAll();
             utils.StaticMidlet.disp.setCurrent(lst);
        }
        
        if (c == cmdUpdate) {
             form.deleteAll();
              utils.StaticMidlet.disp.setCurrent(modifForm);
            }
        if (c == cmdModif) {
            try {
                    updatePersonne(id);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
       lst.deleteAll();
        utils.StaticMidlet.disp.setCurrent(lst);
       Thread th1=new Thread(this);
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
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/getXmlPersons_Attributes.php");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            // display the result
            personnes = personnesHandler.getUtilisateur();

            if (personnes.length > 0) {
                for (int i = 0; i < personnes.length; i++) {
                    lst.append(personnes[i].getNom() + " "
                            + personnes[i].getPrenom() + " "
                            + personnes[i].getEmail() + " "
                            + personnes[i].getPassword() + " "
                            + personnes[i].getUsername() + " "
                            + personnes[i].getAge1() + " ", null);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        utils.StaticMidlet.disp.setCurrent(lst);
    }
    
    
      private String showPersonne(int i) {
        String res = "";
        if (personnes.length > 0) {
            id=personnes[i].getId();
            sb.append("* ");
            sb.append(personnes[i].getId());
            sb.append("\n");
            sb.append("* ");
            sb.append(personnes[i].getPrenom());
            tfPrenom.setString(personnes[i].getPrenom());
            sb.append("\n");
            sb.append("* ");
            sb.append(personnes[i].getNom());
            tfNom.setString(personnes[i].getNom());
            sb.append("\n");
            sb.append("* ");
            sb.append(personnes[i].getEmail());
            tfEmail.setString(personnes[i].getEmail());
            sb.append("\n");
            sb.append("* ");
            sb.append(personnes[i].getUsername());
            tfUsername.setString(personnes[i].getUsername());
            sb.append("\n");
            sb.append("* ");
            sb.append(personnes[i].getPassword());
            tfPassword.setString(personnes[i].getPassword());
            sb.append("\n");
            sb.append("* ");
            sb.append(personnes[i].getAge());
            tfAge.setString(personnes[i].getAge1());
            sb.append("\n");
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }

    private String deletePersonne(int i) throws IOException {
        String res = "";
        if (personnes.length > 0) {
          
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/delete.php?id="+id);
              dis = new DataInputStream(hc.openDataInputStream());
             // System.out.println("http://localhost/parsingBest/delete.php?id="+id);
            while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                utils.StaticMidlet.disp.setCurrent(form);
            }
        }
       // res = sb.toString();
        //sb = new StringBuffer("");
        return res;
    }
    
     private String updatePersonne(int i) throws IOException {
        String res = "";
        if (personnes.length > 0) {
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingBest/modifier.php?id="+id+"&nom=" + tfNom.getString().trim() + "&prenom=" + tfPrenom.getString().trim() + "&email=" + tfEmail.getString().trim() + "&username=" + tfUsername.getString().trim() + "&password=" + tfPassword.getString().trim() + "&age=" + tfAge.getString().trim());
              dis = new DataInputStream(hc.openDataInputStream());
           while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                utils.StaticMidlet.disp.setCurrent(form);
            } 
            sb = new StringBuffer("");
        }
        return res;
    }
    
}
