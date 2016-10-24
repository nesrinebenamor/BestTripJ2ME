/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Recommandation;
import Handler.RecommandationHandler;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
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
public class AfficherRecommandation  implements CommandListener, Runnable{
    
    HttpConnection hc1;
    String url = "http://localhost/parsingbesttrip/supp.php";
    String url1="http://localhost/parsingbesttrip/modif.php";
    
    

    Display disp;
    Command cmdUpdate = new Command("Update", Command.SCREEN, 0);
    Command cmdModif = new Command("Modifier", Command.SCREEN, 0);
    
    
    
    
    Command cmdParse = new Command("Recommandations", Command.SCREEN, 0);
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Command cmdBack2 = new Command("Retour", Command.BACK, 0);
    Command cmdBack3 = new Command("Retour", Command.BACK, 0);
    
    Command cmdSupp = new Command("Supp", Command.EXIT, 0);

    Recommandation[] recommandations;
    
    List lst = new List("Recommandations", List.IMPLICIT);
    Form f = new Form("Accueil");
    
    Form modifForm=new Form("Modifier les donnees");
    TextField tfNom = new TextField("nom", null, 100, TextField.ANY);
    TextField tfContenu = new TextField("contenu", null, 100, TextField.ANY);
    TextField tfAdresse = new TextField("adresse", null, 100, TextField.ANY);

   
    
    
    Form form = new Form("Infos Recommandation");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer(); 
    int id;
    
    
    
    int ch;
    DataInputStream dis;
    
    public AfficherRecommandation(Display disp)
    {
        this.disp = disp;
        this.startApp();
    }
    
    
     public void startApp() {
        f.append("appuier sur le bouton recommendation pour voir la liste");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        lst.setCommandListener(this);
        lst.addCommand(cmdBack2);
        form.setCommandListener(this);
        form.addCommand(cmdSupp);
        form.setCommandListener(this);
        f.addCommand(cmdBack3);
        f.setCommandListener(this);

        
        modifForm.append(tfNom);
        modifForm.append(tfContenu);
        modifForm.append(tfAdresse);
        

        modifForm.addCommand(cmdModif);
        modifForm.setCommandListener(this);
        form.addCommand(cmdUpdate);
        
        
       
        
        
        form.setCommandListener((CommandListener) this);
        disp.setCurrent(f);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdParse) {
            disp.setCurrent(loadingDialog);
            Thread th = new Thread(this);
            th.start();
        }

        if (c == List.SELECT_COMMAND) {
            form.append("Informations Recommandation: \n");
            form.append(showRecommandation(lst.getSelectedIndex()));
            System.out.println(lst.getSelectedIndex()+"ahawa");
            disp.setCurrent(form);
        }
        

        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
        if (c == cmdBack2) {
            new ClasseMenuRec(disp);
        }
        
        if (c == cmdBack3) {
            new ClasseMenuRec(disp);
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
            new Thread(
            new Runnable() {

                public void run() {
   update();
                }
            }
            ).start();
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
                     
            //Thread th = new Thread(this);
            //th.start();
                 DataInputStream dis = new DataInputStream(hc1.openDataInputStream());
                 int ch;
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                }

                //sb = new StringBuffer();
           
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
      
      
      private String update(){
        String res = "";
        //if (experiences.length > 0) {
            try {
                HttpConnection hc = (HttpConnection) Connector.open(url1+"?id="+id+"&nom="+tfNom.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&adresse="+tfAdresse.getString().trim());
                System.out.println(id);
                System.out.println(url1+"?id="+id+"&nom="+tfNom.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&adresse="+tfAdresse.getString().trim());
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
            RecommandationHandler recommandationsHandler = new RecommandationHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingbesttrip/getXmlPersons_Attributes.php");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, recommandationsHandler);
            // display the result
            recommandations = recommandationsHandler.getRecommandation();
 
           
            if (recommandations.length > 0) {
                for (int i = 0; i < recommandations.length; i++) {
                    lst.append(recommandations[i].getNom()+" "
                            +recommandations[i].getContenu()+" "
                            +recommandations[i].getAdresse()+" ", null);

                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }
    
    private String showRecommandation(int i) {
        String res = "";
        if (recommandations.length > 0) {
            sb.append("* ");
            sb.append(recommandations[i].getId());
            id=recommandations[i].getId();
            sb.append("\n");
            sb.append("* ");
            sb.append(recommandations[i].getNom());
            tfNom.setString(recommandations[i].getNom());
            sb.append("\n");
            sb.append("* ");
            sb.append(recommandations[i].getContenu());
            tfContenu.setString(recommandations[i].getContenu());
            sb.append("\n");
            sb.append("* ");
            sb.append(recommandations[i].getAdresse());
            tfAdresse.setString(recommandations[i].getAdresse());
            sb.append("\n");
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }

}


