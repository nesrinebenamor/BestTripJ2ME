/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;
import Midlet.GlobalMidlet;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


/**
 *
 * @author Toch
 */
public class SplashScreen extends Canvas implements Runnable{
    
    private Image mImage;
    private GlobalMidlet MidletGlob;
    Form FormImage = new Form("");
    
    
    public SplashScreen(GlobalMidlet MidletGlob){
        this.MidletGlob = MidletGlob;
        try{
        mImage = Image.createImage("/image/splash.jpg");
        Thread t = new Thread(this);
        t.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void paint(Graphics g) {
       int width = getWidth();
        int height = getHeight();
        
        g.setColor(0x000000);
        g.fillRect(0,0, width, height);
        g.drawImage(mImage, width / 2, height / 2,
                Graphics.HCENTER | Graphics.VCENTER);
    }
    public void dismiss() {
        if (isShown())
           // Display.getDisplay(MidletGlob).setCurrent(new Menumezyen());
         new MenuFinal(utils.StaticMidlet.disp);
    }

    public void run() {
        try {
            Thread.sleep(14000);
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }
        dismiss();
    }
    }


