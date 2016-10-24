/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Handler.statHandler;
import java.io.DataInputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 * @author esprit
 */
public class statistic  implements CommandListener, Runnable {

    Form f1 = new Form("dynamic");
    Display disp ;
    stat[] recommandations;
    Command cmddyn = new Command("Dynamic", Command.SCREEN, 0);
    
     Command cmdBack = new Command("Back", Command.EXIT, 0);

    public static int barcelone = 0;
    public static int paris = 0;
    public static int london = 0;
    public static int tunis = 0;

    public static int total = 0;
    public static int deb = 0;
    
    
    public statistic(Display disp)
    {
        this.disp=disp;
        this.startApp();
    }
    
    

    public void startApp() {
        f1.addCommand(cmddyn);
        f1.setCommandListener(this);
         f1.addCommand(cmdBack);
        f1.setCommandListener(this);
        disp.setCurrent(f1);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void run() {

        System.out.println("test");
        if (Thread.currentThread().getName().equals("Stat")) {
            //disp.setCurrent(new DrawCanvas());

            try {
                //this will handle our XML
                statHandler recommandationsHandler = new statHandler();

                // get a parser object
                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                // get an InputStream from somewhere (could be HttpConnection, for example)
                HttpConnection hc = (HttpConnection) Connector.open("http://localhost/parsingbesttrip/statistique.php");
                DataInputStream dis = new DataInputStream(hc.openDataInputStream());
                parser.parse(dis, recommandationsHandler);
                // display the result    
                System.out.println("test 1");

                recommandations = recommandationsHandler.getPersonne();
                for (int i = 0; i < recommandations.length; i++) {
                    System.out.println(recommandations[i].toString());
                }

                System.out.println("evenements : " + recommandationsHandler.getPersonne().toString());

                if (recommandations.length > 0) {
                    System.out.println("1");
                    for (int i = 0; i < recommandations.length; i++) {
                        System.out.println("2");
                        System.out.println(recommandations[i].getNom());
                        if (recommandations[i].getNom().equals("barcelone")) {
                            barcelone = barcelone + 1;
                            System.out.println("3");
                        }
                        if (recommandations[i].getNom().equals("paris")) {

                            paris = paris + 1;
                            System.out.println("4");
                        }

                        if (recommandations[i].getNom().equals("london")) {

                            london = london + 1;
                            System.out.println("5");
                        }
                        if (recommandations[i].getNom().equals("tunis")) {

                            tunis = tunis + 1;
                            System.out.println("6");
                        }

                        System.out.println("7");

                    }
                    total = barcelone + paris + london + tunis;
                    System.out.println(barcelone + " " + paris + " " + " " + london + " " + tunis + " " + total);
                    disp.setCurrent(new DrawCanvas());

                }
            } catch (Exception e) {
                System.out.println("Exception:" + e.toString());
            }
        }
    }

    public void commandAction(Command c, Displayable d) {

        if (c == cmddyn) {
            Thread th = new Thread(this, "Stat");
            th.start();
        }
          if (c == cmdBack) {
          new ClasseMenuRec(disp);
        }

    }

    public class DrawCanvas extends Canvas implements CommandListener {

        int admin = statistic.barcelone;
        int gerant = statistic.paris;
        int candidat = statistic.london;
        int moniteur = statistic.tunis;

        int total = statistic.total;
        int deb = statistic.deb;
        int w = getWidth();
        int h = getHeight();
        int x = w / 2 - 75;
        int y = h / 2 - 105;

        protected void paint(Graphics g) {
            System.out.println("this : " + barcelone + paris + london + tunis + total + deb);

            addCommand(cmddyn);
           

            g.setColor(0, 255, 0);
            g.fillArc(x, y, 150, 150, 0, (barcelone * 360) / total);
            deb = (barcelone * 360) / total;

            //sport
            g.setColor(255, 0, 0);
            g.fillArc(x, y, 150, 150, deb, (paris * 360) / total);
            deb = ((barcelone + paris) * 360) / total;
//            //loisir

            g.setColor(255, 255, 0);
            g.fillArc(x, y, 150, 150, deb, (london * 360) / total);
            deb = ((barcelone + paris + london) * 360) / total;

//           
//            
            g.setColor(0, 0, 255);
            g.fillArc(x, y, 150, 150, deb, ((tunis * 360) / total) + 1);

            g.setColor(0, 255, 0);
            g.fillRect(20, h - 70, 20, 20);
            g.drawString("barcelone", 0, h - 30, Graphics.BOTTOM | Graphics.LEFT);

            g.setColor(255, 255, 0);
            g.fillRect(80, h - 70, 20, 20);
            g.drawString("london", 70, h - 30, Graphics.BOTTOM | Graphics.LEFT);
            
            g.setColor(0, 0, 255);
            g.fillRect(140, h - 70, 20, 20);
            g.drawString("tunis", 130, h - 30, Graphics.BOTTOM | Graphics.LEFT);
            
            g.setColor(255, 0, 0);
            g.fillRect(180, h - 70, 20, 20);
            g.drawString("paris", 175, h - 30, Graphics.BOTTOM | Graphics.LEFT);

//        
        }

        public void commandAction(Command c, Displayable d) {

        }

    }

}


