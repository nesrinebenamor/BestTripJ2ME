package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Experience;
import Entity.Photo;
import Entity.Video;
import Handler.ExperienceHandler;
import Handler.PhotoHandler;
import Handler.VideoHandler;
import javax.microedition.midlet.*;
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
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
/**
 * @author Samsung
 */
public class experienceParsing implements CommandListener, Runnable,ItemCommandListener{
   
    private static final Command CMD_GO = new Command("Go", Command.ITEM, 1);
    
    HttpConnection hc1;
    
    String url = "http://localhost/experience/supp.php";
    String url1="http://localhost/experience/modif.php";
    
    TextField tfTitre = new TextField("titre", null, 100, TextField.ANY);
    TextField tfContenu = new TextField("contenu", null, 100, TextField.ANY);
    TextField tfPays = new TextField("pays", null, 100, TextField.ANY);
    TextField tfType = new TextField("type", null, 100, TextField.ANY);
    TextField tfClimat = new TextField("climat", null, 100, TextField.ANY);
    TextField tfDepense = new TextField("depense", null, 100, TextField.DECIMAL);
    DateField calender = new DateField("Date:", DateField.DATE, TimeZone.getTimeZone("GMT"));
    
    Command cmdUpdate = new Command("Update", Command.SCREEN, 0);
    Command cmdModif = new Command("Modifier", Command.SCREEN, 0);
    
    
    Display disp ;
    Command cmdParse = new Command("Experiences", Command.SCREEN, 0);
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Command cmdMenu = new Command("Back", Command.BACK, 0);
    Command cmdSupp=new Command("Supp",Command.EXIT,0);
    
    
    Experience[] experiences;
    List lst = new List("Experiences", List.IMPLICIT);
    
    Form f = new Form("Accueil");
    Form form = new Form("Infos Experience");
    Form loadingDialog = new Form("Please Wait");
    Form modifForm=new Form("Modifier les donnees");
    Form imTest=new Form("Test Affichage image");
  
    private Image img;
  
    StringBuffer sb = new StringBuffer();
    int id;
    int id_ut=91;
    double depense=0;
    Photo[] photo;
    Video[] video;
   String[] elements;
    
    StringItem vid = new StringItem("Videos", "cliquer pour voir", Item.HYPERLINK);
    
    public experienceParsing(Display disp,int id_ut) {
        this.disp = disp;
        this.id_ut=id_ut;
        this.startApp();
       
    }
    
    
    public void startApp() {
        f.append("Click Experience to get your experiences_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        lst.addCommand(cmdMenu);
        form.addCommand(cmdBack);
        
        modifForm.append(tfTitre);
        modifForm.append(tfContenu);
        modifForm.append(calender);
        modifForm.append(tfPays);
        modifForm.append(tfType);
        modifForm.append(tfClimat);
        modifForm.append(tfDepense);

        modifForm.addCommand(cmdModif);
        modifForm.setCommandListener(this);
        
        form.addCommand(cmdSupp);
        form.addCommand(cmdUpdate);
        form.setCommandListener(this);
        
        vid.setDefaultCommand(CMD_GO);
        vid.setItemCommandListener(this);
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

        if (c == List.SELECT_COMMAND) {
            form.append("Informations Experience: \n");
            form.append(showExperience(lst.getSelectedIndex()));
            
                 if (photo.length > 0) 
                 {
                form.append("Images : \n");
                for (int j = 0; j < photo.length; j++) {
                    try {
                        // System.out.println(photo[i].getId());
                        //sb.append(photo[j].getDescription()+" "+photo[j].getImage());
                        //img = Image.createImage("/image/"+photo[i].getImage());
                        ImageItem img1=new ImageItem("image Profil", img, ImageItem.LAYOUT_RIGHT,null);
                        System.out.println("/image/"+photo[j].getImage());
                        img = Image.createImage("/image/"+photo[j].getImage());
                        
                        form.append("\n");
                        form.append("Description : \n"+photo[j].getDescription()); 
                        form.append("\n");
                        form.append(img);
                        form.append("************");
                        // lst.append(null, img);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }              
            }
              if (video.length > 0) 
                 {
                 for(int k=0;k<video.length;k++)
                {
                   System.out.println("video"+video[k].getVid());  
                  elements[k]=video[k].getVid();
                }

                 form.append(vid);
                 }
            
                 
            disp.setCurrent(form);
        }

        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }
        if (c == cmdSupp) {
          supprimer();
          lst.deleteAll();
          form.deleteAll();
           disp.setCurrent(lst);
           Thread th1 = new Thread(this);
            th1.start();
           // disp.setCurrent(loadingDialog);           
        } 
        if (c == cmdMenu) {
            
            new MenuExperience(disp, id_ut);
           
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
                HttpConnection hc = (HttpConnection) Connector.open(url1+"?id="+id+"&titre="+tfTitre.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&pays="+tfPays.getString().trim()+"&type="+tfType.getString().trim()+"&climat="+tfClimat.getString().trim()+"&depense="+tfDepense.getString().trim());
                System.out.println(id);
                //System.out.println(url1+"?id="+id+"&titre="+tfTitre.getString().trim()+"&contenu="+tfContenu.getString().trim()+"&pays="+tfPays.getString().trim()+"&type="+tfType.getString().trim()+"&climat="+tfClimat.getString().trim()+"&depense="+tfDepense.getString().trim());
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
            ExperienceHandler experiencesHandler = new ExperienceHandler();
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/experience/getXmlPersons_Attributes.php?id_ut="+String.valueOf(id_ut));
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            parser.parse(dis, experiencesHandler);
            // display the result
            
            experiences = experiencesHandler.getExperience();
    
            if (experiences.length > 0) {
                for (int i = 0; i < experiences.length; i++) {
                    lst.append("Titre : " +experiences[i].getTitre()+"\n "+"Pays : "+experiences[i].getPays()+"\n "+"Type : "+experiences[i].getType()+"\n "+"Depense : "+experiences[i].getDepense(), null);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        disp.setCurrent(lst);
    }

    private String showExperience(int i) {
        String res = "";
        
        if (experiences.length > 0) {
            try {
                sb.append("* ");
                sb.append(experiences[i].getId());
                id=experiences[i].getId();
                sb.append("\n");
                sb.append("* ");
                sb.append(experiences[i].getTitre());
                tfTitre.setString(experiences[i].getTitre());
                sb.append("\n");
                sb.append("* ");
                sb.append(experiences[i].getContenu());
                tfContenu.setString(experiences[i].getContenu());
                sb.append("\n");
                sb.append("* ");
                sb.append(experiences[i].getPays());
                tfPays.setString(experiences[i].getPays());
                sb.append("\n");
                sb.append("* ");
                sb.append(experiences[i].getType());
                tfType.setString(experiences[i].getType());
                sb.append("\n");
                sb.append("* ");
                sb.append(experiences[i].getClimat());
                tfClimat.setString(experiences[i].getClimat());
                sb.append("\n");
                sb.append("* ");
                sb.append(experiences[i].getDepense());
                
                tfDepense.setString(String.valueOf(experiences[i].getDepense()));

                sb.append("\n");
                
                PhotoHandler photoHandler = new PhotoHandler();
                System.out.println("here1");
                // get a parser object
                SAXParser parser1 = SAXParserFactory.newInstance().newSAXParser();
                // get an InputStream from somewhere (could be HttpConnection, for example)
                String id_exp=String.valueOf(id);
                System.out.println(id);
                System.out.println(id_exp);
                HttpConnection hc1 = (HttpConnection) Connector.open("http://localhost/experience/getXmlPhoto_Attributes.php?id_exp="+id_exp);
                DataInputStream dis1 = new DataInputStream(hc1.openDataInputStream());
                System.out.println("here2");
                parser1.parse(dis1, photoHandler);
                photo = photoHandler.getPhoto();
                
                VideoHandler videoHandler = new VideoHandler();
                System.out.println("here3");
                // get a parser object
                SAXParser parser2 = SAXParserFactory.newInstance().newSAXParser();
                // get an InputStream from somewhere (could be HttpConnection, for example)
               
                System.out.println(id);
                System.out.println(id_exp);
                HttpConnection hc2 = (HttpConnection) Connector.open("http://localhost/experience/getXmlVideo_Attributes.php?id_exp="+id_exp);
                DataInputStream dis2 = new DataInputStream(hc2.openDataInputStream());
                System.out.println("here9854");
                parser2.parse(dis2, videoHandler);
                video = videoHandler.getVideo();
                elements=new String[video.length];
              
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SAXException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
            
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }

    public void commandAction(Command c, Item item) {
         if (c == CMD_GO) {
             System.out.println("id: "+id);
             for(int i=0;i<elements.length;i++)
             {
                 System.out.println(elements[i]);
             }
            new Thread(
            new Runnable() {
                public void run() {
                    
              new VideoPlayer(disp,String.valueOf(id), elements,id_ut);
                  //  new VideoPlayer(disp);
                }
            }
            ).start();
            
        }
    }
}
