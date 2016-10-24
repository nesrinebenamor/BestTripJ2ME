/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Reponse;
import Entity.Sujet;
import Handler.ReponseHandler;
import Handler.SujetHandler;
import java.io.DataInputStream;
import java.io.IOException;
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
public class AfficheSujet implements CommandListener, Runnable{
    
    HttpConnection hc1;
    String url = "http://localhost/pars/supp.php";
    String url1 = "http://localhost/pars/ajoutReponse.php";
    int id;
    int ch;
    DataInputStream dis;
    
   
    
   int idSujet;
    
    //Sujet currentSujet;
    
    Display disp;
    TextField ttitre = new TextField("titre", null, 50, TextField.ANY);
    TextField ttext = new TextField("text", null, 50, TextField.ANY);
    TextField tcategorie = new TextField("categorie", null, 50, TextField.ANY);
    TextField tfContenu = new TextField("contenu", null, 100, TextField.ANY);
    Form modifForm = new Form("Modifier les ...");
    Form repondreForm = new Form("Ajouter une reponse");
    Form repondre2Form = new Form("Ajouter une reponse");
    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");
    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);
    Command cmdParse = new Command("Sujet", Command.SCREEN, 0);
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Command cmdBack2 = new Command("Back", Command.BACK, 0);
    Command cmdBack3 = new Command("Retour", Command.BACK, 0);
    Command cmdBack4 = new Command("Retour", Command.BACK, 0);
    Command cmdUpdate = new Command("UP", Command.SCREEN, 0);
    Command cmdModif = new Command("Modifier", Command.SCREEN, 0);
    Command cmdRep = new Command("Repondre", Command.SCREEN, 0);
    Command cmdRepp = new Command("Repp", Command.SCREEN, 0);
    //Command cmdModif = new Command("Modifier", Command.OK, 0);
    Command cmdSupp = new Command("Supprimer", Command.OK, 0);
    Command cmdValider = new Command("valider", Command.SCREEN, 0);

    
    Sujet[] sujet;
    Reponse[] reponses;
    List lst = new List("Sujet", List.IMPLICIT);
    List lstR = new List("Reponse", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos Sujet");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();
    
    public AfficheSujet(Display disp) {
        this.disp = disp;
        this.startApp();
    }
    
    public void startApp() {
        
        f.append("Click su Sujet pour voir la liste des Sujets");
        f.addCommand(cmdParse);
        f.setCommandListener((CommandListener) this);
        lst.setCommandListener((CommandListener) this);
        lstR.setCommandListener((CommandListener) this);
        form.addCommand(cmdBack);
        form.setCommandListener((CommandListener) this);
        f.addCommand(cmdBack3);
        lst.addCommand(cmdBack4);
        lst.setCommandListener((CommandListener) this);
        f.setCommandListener((CommandListener) this);
        form.addCommand(cmdSupp);
        form.setCommandListener((CommandListener) this);
        form.addCommand(cmdUpdate);
        form.setCommandListener((CommandListener) this);
        form.addCommand(cmdRepp);
        form.setCommandListener((CommandListener) this);
        modifForm.addCommand(cmdModif);
        modifForm.setCommandListener((CommandListener) this);
        repondreForm.addCommand(cmdRep);
        repondreForm.setCommandListener((CommandListener) this);
        repondre2Form.append(tfContenu);
        repondre2Form.setCommandListener((CommandListener) this);
        repondre2Form.addCommand(cmdValider);
        f2.addCommand(cmdBack2);
        f2.setCommandListener(this);
        
        modifForm.append(ttitre);
        modifForm.append(ttext);
        modifForm.append(tcategorie);
        
        lstR.addCommand(cmdRep);
        form.setCommandListener((CommandListener) this);
       
        disp.setCurrent(f);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdValider) {
           AjoutReponse();
        }
        
        if (c == cmdBack3) {
           new ClasseMenu(disp);
        }
        if (c == cmdBack4) {
           new ClasseMenu(disp);
        }
       if (c == cmdBack2) {
            
            disp.setCurrent(repondre2Form);
        }

        if(c==cmdRep && d==lstR)
       {disp.setCurrent(repondre2Form);}
        if (c == cmdParse) {
            disp.setCurrent(loadingDialog);
            Thread th = new Thread(this);
            th.start();
        }
        
        if (c == List.SELECT_COMMAND) {
            form.append("Informations Sujet: \n");
            form.append(showSujet(lst.getSelectedIndex()));
            disp.setCurrent(form);
        }

        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
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
            try {
                    ModifierSujet(id);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            
            
        }
        if (c == cmdRepp) {
             form.deleteAll();
             AffiheReponse();
             //disp.setCurrent(repondre2Form);
             //disp.setCurrent(repondreForm);
            }
    }

    public void run() {
        try {
            // this will handle our XML
            SujetHandler personnesHandler = new SujetHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pars/getXmlSujet_Attributes.php");
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            // display the result
            sujet = personnesHandler.getSujet();

            if (sujet.length > 0) {
                for (int i = 0; i < sujet.length; i++) {
                    lst.append("Titre:      "+sujet[i].getTitre()+"TEXT: "
                            +sujet[i].getText()+" CATEGORIE :   "+sujet[i].getCategorie(), null );

                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }
    
    private String showSujet(int i) {
        String res = "";
        if (sujet.length > 0) { 
            idSujet = sujet[i].getId();        
            sb.append("* ");
            sb.append(sujet[i].getId());
            id=sujet[i].getId();
            sb.append("\n");
            sb.append("* ");
            sb.append(sujet[i].getTitre());
            ttitre.setString(sujet[i].getTitre());
            sb.append("\n");
            sb.append("* ");
            sb.append(sujet[i].getText());
            ttext.setString(sujet[i].getText());
            sb.append("\n");
            sb.append("* ");
            sb.append(sujet[i].getCategorie());
            tcategorie.setString(sujet[i].getCategorie());
            sb.append("\n");
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }
    
       private String ModifierSujet(int i) throws IOException {
        String res = "";
        if (sujet.length > 0) {
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pars/modifier.php?id="+id+"&titre=" + ttitre.getString().trim() + "&text=" + ttext.getString().trim() + "&categorie=" + tcategorie.getString().trim());
            //System.out.println("http://localhost/pars/modifier.php?id="+id+"&titre=" + ttitre.getString().trim() + "&text=" + ttext.getString().trim() + "&categorie=" + tcategorie.getString().trim());
              dis = new DataInputStream(hc.openDataInputStream());
           while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            if ("OK".equals(sb.toString().trim())) {
                disp.setCurrent(form);
            } 
            sb = new StringBuffer("");
        }
        return res;
    }
    
    public void AjoutReponse()
    {
        try {
                hc1 = (HttpConnection) Connector.open(url1+"?Id_Sujet="+idSujet+"&contenu="+tfContenu.getString());
                //System.out.println("?contenu="+tfContenu.getString().trim());
                dis = new DataInputStream(hc1.openDataInputStream());
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
    
    public void AffiheReponse()
    {
        try {
            
            // this will handle our XML
            ReponseHandler personnesHandler = new ReponseHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/pars/getXmlReponse_Attributes.php?id="+id);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, personnesHandler);
            // display the result
            reponses = personnesHandler.getReponse();

            if (reponses.length > 0) {
                for (int i = 0; i < reponses.length; i++) {
                    lstR.append("Contenue:      "+reponses[i].getContenu(), null );

                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lstR);
    }
    
}
