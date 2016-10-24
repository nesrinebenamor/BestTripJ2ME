package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Entity.Photo;
import Handler.PhotoHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Houssem Eddine
 */
public class PhotoParsing implements CommandListener, Runnable {

    Display disp;
    Command cmdParse = new Command("Photo", Command.SCREEN, 0);
    Command cmdBack = new Command("Back", Command.BACK, 0);
    Photo[] photo;
    List lst = new List("Photo", List.IMPLICIT);
    Form f = new Form("Accueil");
    Form form = new Form("Infos Photo");
    Form loadingDialog = new Form("Please Wait");
    StringBuffer sb = new StringBuffer();
    Form imTest=new Form("Test Affichage image");
    
    private Image img;
    private String copyFile;
    String id_exp;
    
    public PhotoParsing(Display disp,String id_exp) {
        this.disp = disp;
        this.id_exp=id_exp;
        this.startApp();
    }
    
    
    public void startApp() {
        f.append("Click Photo to get your photo_list");
        f.addCommand(cmdParse);
        f.setCommandListener(this);
        lst.setCommandListener(this);
        form.addCommand(cmdBack);
        form.setCommandListener(this);
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
            form.append("Informations Photo: \n");
            form.append(showPersonne(lst.getSelectedIndex()));
            disp.setCurrent(form);
        }

        if (c == cmdBack) {
            form.deleteAll();
            disp.setCurrent(lst);
        }

    }

    public void run() {
        try {
            // this will handle our XML
            PhotoHandler photoHandler = new PhotoHandler();
            System.out.println("here1");
            // get a parser object
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // get an InputStream from somewhere (could be HttpConnection, for example)
            HttpConnection hc = (HttpConnection) Connector.open("http://localhost/experience/getXmlPhoto_Attributes.php?id_exp="+id_exp);
            DataInputStream dis = new DataInputStream(hc.openDataInputStream());
            System.out.println("here2");
            parser.parse(dis, photoHandler);
            // display the result
            photo = photoHandler.getPhoto();
           System.out.println("here3");
            if (photo.length > 0) {
                
              //img=Image.createImage("/a7.jpg");
                
                for (int i = 0; i < photo.length; i++) {
                    // System.out.println(photo[i].getId());
                    
            
               lst.append(photo[i].getDescription()+" "+photo[i].getImage(),null);
                  //img = Image.createImage("/image/"+photo[i].getImage());
                  ImageItem img1=new ImageItem("image Profil", img, ImageItem.LAYOUT_RIGHT,null);
         
                  img = Image.createImage("/image/"+photo[i].getImage());
                  imTest.append(img);
                  imTest.append(photo[i].getDescription()+ " "+photo[i].getImage());
                 // lst.append(null, img);
                }
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
        
        disp.setCurrent(imTest);
    }

    private String showPersonne(int i) {
        String res = "";
        if (photo.length > 0) {
            sb.append("* ");
            //System.out.println(photo[i].getId());
            sb.append(photo[i].getId());
            sb.append("\n");
            sb.append("* ");
            sb.append(photo[i].getDescription());
            sb.append("\n");
            sb.append("* ");
            sb.append(photo[i].getImage());
            sb.append("\n");
        }
        res = sb.toString();
        sb = new StringBuffer("");
        return res;
    }
    
    
     public void copy(String srcFile,String newFileName)throws IOException{
         
        String currentVersion = System.getProperty("microedition.io.file.FileConnection.version" );
      if (currentVersion==null)
      {
          System.out.println("does not support");
      }
      else
      {
         String srcFileUrl = srcFile;
        System.out.println("src:"+srcFileUrl);
        FileConnection srcFc = (FileConnection)Connector.open(srcFileUrl);

        if(!srcFc.exists()||!srcFc.canRead()){
            throw new IOException("sd：\""+srcFile+"\"ds");
        }
        if(srcFc.isDirectory()){
            throw new IOException("sd：\""+srcFile+"\"eeer");
        }
 
        String desFileUrl = "/"+newFileName;
        System.out.println("des: "+desFileUrl);
        FileConnection desFc = (FileConnection)Connector.open(desFileUrl);
        if(desFc.exists()){
            throw new IOException("errrrrrrrr");
        }

        
        desFc.create();
        InputStream is = srcFc.openInputStream();
        OutputStream os = desFc.openOutputStream();
        byte[] buf = new byte[200];
        int n = -1;
        while((n = is.read(buf,0,buf.length))!= -1){
            os.write(buf, 0, n);
        }
        is.close();
        os.close();
        srcFc.close();
        desFc.close();
      } 
        
    }
}
