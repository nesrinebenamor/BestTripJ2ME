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
public class MidletAjout implements CommandListener, Runnable{

    Display disp ;
  //  private DateField calender;  
  private static final int DATE = 0;
  
    //Form 1
    Form f1 = new Form("Inscription");
    TextField tfTitre = new TextField("titre", null, 100, TextField.ANY);
    TextField tfContenu = new TextField("contenu", null, 100, TextField.ANY);
    String[] paysData ={"Afghanistan","Afrique du Sud","Albanie","Algérie","Allemagne","Andorre","Angola","Antigua-et-Barbuda","Antigua-et-Barbuda"
            ,"Arabie saoudite","Argentine","Arménie","Australie","Autriche","Azerbaïdjan","Bahamas","Bahreïn","Bangladesh","Barbade","Belgique","Belize"
            ,"Bénin","Bhoutan","Belarus","Birmanie","Bolivie","Bosnie-Herzégovine","Botswana","Brésil","Brunei","Bulgarie","Burkina","Burundi","Cambodge"
            ,"Cameroun","Canada","îles du Cap-Vert","République centrafricaine","Chili","Chine","Chypre","Colombie","Comores","Congo"
            ,"République démocratique du Congo","Îles Cook","Corée du Nord","Corée du Sud","Costa Rica","Côte d'Ivoire","Croatie","Cuba","Danemark"
            ,"Djibouti","épublique dominicaine","Dominique","Égypte","Émirats arabes unis","Équateur","Érythrée","Espagne","Estonie","États-Unis"
            ,"Éthiopie","îles Fidji","Finlande","France","Gabon","Gambie","Géorgie","Ghana","Grèce","Grenade","Guatemala","Guinée-Bissau"
            ,"Guinée équatoriale","Guyana","Haïti","Honduras","Hongrie","Indonésie","Irak","Iran","Irlande","Islande","Italie","Jamaïque","Japon"
            ,"Jordanie","Kazakhstan","Kenya","Kirghizie","Kiribati","Koweït","Laos","Lesotho","Lettonie","Liban","Liberia","Libye","Liechtenstein"
            ,"Lituanie","Luxembourg","Macédoine","Madagascar","Malaisie","Malawi","îles Maldives","îles Maldives","Mali","Malte","Îles Marshall"
            ,"Maurice","Mauritanie","Mexique","Micronésie","Moldavie","Monaco","Mongolie","Monténégro","Mozambique","Namibie","Nauru","Népal","Nicaragua"
            ,"Niger","Nigeria","Niue","Nouvelle-Zélande","Oman","Ouganda","Ouzbékistan","Pakistan","Palaos","État de Palestine","Panama"
            ,"Papouasie-Nouvelle-Guinée","Paraguay","Pays-Bas","Pérou","Philippines","Pologne","Portugal","Qatar","Grande-Bretagne","Grande-Bretagne"
            ,"Russie","Rwanda","Saint-Christophe-et-Niévès","Sainte-Lucie","Saint-Marin","Saint-Vincent-et-les Grenadines","Îles Salomon","Salvador"
            ,"Sao Tomé-et-Principe","Sénégal","Serbie","Seychelles","Sierra Leone","Singapour","Slovaquie","Slovénie","Somalie","Soudan","Soudan du Sud"
            ,"Sri Lanka","Suède","Suisse","Suriname","Swaziland","Syrie","Tadjikistan","Tchad","Thaïlande","Timor oriental","Togo","Tonga"
            ,"Trinité-et-Tobago","Tunisie","Turkménistan","Turquie","Tuvalu","Ukraine","Uruguay","Vatican","Venezuela","Viêt Nam","Yémen","Zambie"
            ,"Zimbabwe"};
    
    String[] typeData={"Vacances","Expatriation","Déplacement professionel","Week end","Journée découverte","Chez moi","Voyage de noce"
            ,"Au pair, étude et stage","Humanitaire","Croisière","Trek","Tour du monde","Voyage scolaire","Trip sac a dos","Gros budget","Classique"
            ,"Ptit budget","Total impro","Hôtel Club","Vacances en famille","Séjours de golf","Vacances de luxe","Voyage expérimental","aventure"};
    
    String[] climatData={"Polaire","froid","tempéré","douce","océanique","humide","tres humide","sec","frais","océanique dégradé","continental","neige"
            ,"méditerranéen","tres chaude","montagnard","équatoriaux","tropical","subtropical"};

    TextField tfDepense = new TextField("depense", null, 100, TextField.DECIMAL);
    DateField calender = new DateField("Date:", DateField.DATE, TimeZone.getTimeZone("GMT"));
    ChoiceGroup tfType = new ChoiceGroup("Type", Choice.POPUP, typeData, null);
    ChoiceGroup tfPays = new ChoiceGroup("Pays", Choice.POPUP, paysData, null);
    ChoiceGroup tfClimat = new ChoiceGroup("Climat", Choice.POPUP, climatData, null);
         
     private static final Command CMD_PRESS = new Command("Press", Command.ITEM, 1); 
     StringItem button = new StringItem("choisir une photo ", "Button", Item.BUTTON);
        
    
    Command cmdValider = new Command("valider", Command.SCREEN, 0);
    Command cmdBack = new Command("cmdBack", Command.BACK, 0);
    
   
    Form f2 = new Form("Welcome");
    Form f3 = new Form("Erreur");

    Alert alerta = new Alert("Error", "Sorry", null, AlertType.ERROR);

    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/experience/ajout.php";
    StringBuffer sb = new StringBuffer();
    int ch;
    int id=91;
    String id_exp;
    
    public MidletAjout(Display disp,int id) {
        this.disp = disp;
        this.id=id;
        this.startApp();
        
    }
    
    public void startApp() {
        f1.append(tfTitre);
        f1.append(tfContenu);
        f1.append(calender);
        f1.append(tfPays);
        f1.append(tfType);
        f1.append(tfClimat);
        f1.append(tfDepense);
        
       
        f1.addCommand(cmdValider);
        f1.setCommandListener(this);
        f2.addCommand(cmdBack);
        f2.setCommandListener(this);
        disp.setCurrent(f1);
        
     System.out.println(paysData[tfPays.getSelectedIndex()]);   
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdValider) {
            Thread th = new Thread(this);
            th.start();
            
        }
        if (c == cmdBack) {
            
            disp.setCurrent(f1);
        }
    }

    public void run() {
        try {
               String tp=paysData[tfPays.getSelectedIndex()].replace(' ','_');
               String tt=typeData[tfType.getSelectedIndex()].replace(' ','_');
               
               String tc=climatData[tfPays.getSelectedIndex()].replace(' ','_');
               String date;
               
             Calendar cal = Calendar.getInstance();
                  cal.setTime(calender.getDate());
                 int year = cal.get(Calendar.YEAR);
                 int month = cal.get(Calendar.MONTH) + 1;
                 int day = cal.get(Calendar.DAY_OF_MONTH);
                 date=year+"-"+month+"-"+day;
                //System.out.println("date formatted :"+dateStr);
               System.out.println("date :"+ date);
               hc = (HttpConnection) Connector.open(
                        url+"?titre="+tfTitre.getString().trim()+"&contenu="+tfContenu.getString().trim()
                           +"&pays="+tp
                           +"&type="+tt
                           +"&climat="+tc
                           +"&depense="+tfDepense.getString().trim()
                           +"&id_ut="+String.valueOf(id)
                           +"&date="+date
                            );
                //System.out.println("?titre="+tfTitre.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&pays="+tfPays.getString().trim()+"&type="+typeData[tfType.getSelectedIndex()].trim()+"&climat="+tfClimat.getString().trim()+"&depense="+tfDepense.getString().trim());
                dis = new DataInputStream(hc.openDataInputStream());
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                }
                id_exp=sb.toString();
                System.out.println(id_exp);
                
                 System.out.println("9865");
                if (id_exp!=null) {
                    //disp.setCurrent(f2);
                    //new experienceParsing(disp,id);
                    new AjoutMedia(disp, id_exp,id);
                    //new AjoutMedia(disp, id);
                }else{
                    disp.setCurrent(f3);
                }
                //sb = new StringBuffer();
                 dis.close();
                 hc.close();
                 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
    }


}
