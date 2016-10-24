/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;

/**
 *
 * @author esprit
 */
public class Menumezyen extends Form implements ItemStateListener {

    class MyButton extends CustomItem {

        private Image _image = null;
        private boolean _down = false;
        private int _clicks = 0;

        public MyButton(Image image) {
            super("");
            _image = image;
        }

        // Button's image
        public void setImage(Image image) {
            _image = image;
            repaint();
        }

        public Image getImage() {
            return _image;
        }

        // Has the button been clicked?
        public boolean isClicked() {
            if (_clicks > 0) {
                _clicks -= 1;
                return true;
            }
            return false;
        }

        // Is the button currently down?
        public boolean isDown() {
            return _down;
        }

        public void setDown(boolean down) {
            if (_down) {
                _clicks += 1;
            }
            if (down != _down) {
                _down = down;
                repaint();
                notifyStateChanged();
            }
        }

        public void setDown() {
            setDown(true);
        }

        public void setUp() {
            setDown(false);
        }

        // Minimal button size = image size
        protected int getMinContentHeight() {
            return getImage().getHeight();
        }

        protected int getMinContentWidth() {
            return getImage().getWidth();
        }

        // Preferred button size = image size + borders

        protected int getPrefContentHeight(int width) {
            return getImage().getHeight() + 2;
        }

        protected int getPrefContentWidth(int height) {
            return getImage().getWidth() + 2;
        }

        // Button painting procedure
        protected void paint(Graphics g, int w, int h) {
            // Fill the button with grey color - background 
            g.setColor(192, 192, 192);
            g.fillRect(0, 0, w, h);
            // Draw the image in the center of the button
            g.drawImage(getImage(), w / 2, h / 2, Graphics.HCENTER | Graphics.VCENTER);
            // Draw the borders
            g.setColor(isDown() ? 0x000000 : 0xffffff);
            g.drawLine(0, 0, w, 0);
            g.drawLine(0, 0, 0, h);
            g.setColor(isDown() ? 0xffffff : 0x000000);
            g.drawLine(0, h - 1, w, h - 1);
            g.drawLine(w - 1, 0, w - 1, h);
        }

        // If FIRE key is pressed, the button becomes pressed (down state)
        protected void keyPressed(int c) {
            if (getGameAction(c) == Canvas.FIRE) {
                setDown();
            }
        }

        // When FIRE key is released, the button becomes released (up state)

        protected void keyReleased(int c) {
            if (getGameAction(c) == Canvas.FIRE) {
                setUp();
            }
        }

        // The same for touchscreens

        protected void pointerPressed(int x, int y) {
            setDown();
        }

        protected void pointerReleased(int x, int y) {
            setUp();
        }
    }
    private int id;

    Menumezyen.MyButton buttonadd = null;
    Menumezyen.MyButton buttonadmin = null;
    Menumezyen.MyButton buttonprofil = null;
    Menumezyen.MyButton buttondesactiver = null;
    Menumezyen.MyButton buttonmail = null;
    Menumezyen.MyButton buttonsms = null;
    Menumezyen.MyButton buttonreception = null;
    Menumezyen.MyButton buttonmodifier = null;
    Menumezyen.MyButton buttonrechercher = null;
    Menumezyen.MyButton buttonaddphoto = null;
    Menumezyen.MyButton buttonconnexion = null;
    Menumezyen.MyButton buttondeconnexion = null;
    Menumezyen.MyButton buttonexit = null;

    public Menumezyen() {
        super("Menu Utilisateur");
        try {
            buttonadd = new Menumezyen.MyButton(Image.createImage("/image/add.png"));
            buttonadmin = new Menumezyen.MyButton(Image.createImage("/image/admin.png"));
            buttonprofil = new Menumezyen.MyButton(Image.createImage("/image/consulter.png"));
            buttondesactiver = new Menumezyen.MyButton(Image.createImage("/image/desactiver.png"));
            buttonmail = new Menumezyen.MyButton(Image.createImage("/image/mail.png"));
            buttonsms = new Menumezyen.MyButton(Image.createImage("/image/sms.png"));
            buttonreception = new Menumezyen.MyButton(Image.createImage("/image/reception.png"));
            buttonmodifier = new Menumezyen.MyButton(Image.createImage("/image/modifier.png"));
            buttonrechercher = new Menumezyen.MyButton(Image.createImage("/image/rechercher.png"));
            buttonaddphoto = new Menumezyen.MyButton(Image.createImage("/image/addphoto.png"));
            buttonconnexion = new Menumezyen.MyButton(Image.createImage("/image/login.png"));
            buttondeconnexion = new Menumezyen.MyButton(Image.createImage("/image/logout.png"));
            buttonexit = new Menumezyen.MyButton(Image.createImage("/image/exit.png"));
            append(buttonadd);
            append(buttonadmin);
            append(buttonprofil);
            append(buttonmodifier);
            append(buttondesactiver);
            append(buttonmail);
            append(buttonsms);
            append(buttonreception);
            append(buttonrechercher);
            append(buttonaddphoto);
            append(buttonconnexion);
            append(buttondeconnexion);
            append(buttonexit);
            this.setItemStateListener(this);
            utils.StaticMidlet.disp.setCurrent(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Menumezyen(int id) {
        super("Menu Utilisateur");
        this.id = id;
        try {
            buttonadd = new Menumezyen.MyButton(Image.createImage("/image/add.png"));
            buttonadmin = new Menumezyen.MyButton(Image.createImage("/image/admin.png"));
            buttonprofil = new Menumezyen.MyButton(Image.createImage("/image/consulter.png"));
            buttondesactiver = new Menumezyen.MyButton(Image.createImage("/image/desactiver.png"));
            buttonmail = new Menumezyen.MyButton(Image.createImage("/image/mail.png"));
            buttonsms = new Menumezyen.MyButton(Image.createImage("/image/sms.png"));
            buttonreception = new Menumezyen.MyButton(Image.createImage("/image/reception.png"));
            buttonmodifier = new Menumezyen.MyButton(Image.createImage("/image/modifier.png"));
            buttonrechercher = new Menumezyen.MyButton(Image.createImage("/image/rechercher.png"));
            buttonaddphoto = new Menumezyen.MyButton(Image.createImage("/image/addphoto.png"));
            buttonconnexion = new Menumezyen.MyButton(Image.createImage("/image/login.png"));
            buttondeconnexion = new Menumezyen.MyButton(Image.createImage("/image/logout.png"));
            buttonexit = new Menumezyen.MyButton(Image.createImage("/image/exit.png"));
            append(buttonadd);
            append(buttonadmin);
            append(buttonprofil);
            append(buttonmodifier);
            append(buttondesactiver);
            append(buttonmail);
            append(buttonsms);
            append(buttonreception);
            append(buttonrechercher);
            append(buttonaddphoto);
            append(buttonconnexion);
            append(buttondeconnexion);
            append(buttonexit);
            this.setItemStateListener(this);
            utils.StaticMidlet.disp.setCurrent(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemStateChanged(Item item) {
        if (item == buttonadd) {
            if (buttonadd.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new AjoutUtilisateur());
            }
        }
        if (item == buttonadmin) {
            if (buttonadmin.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new ParsingAdministrateur());
            }
        }
        if (item == buttonmail) {
            if (buttonmail.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new SendMail());
            }
        }
         if (item == buttonexit) {
            if (buttonexit.isClicked()) {
      new MenuFinal(utils.StaticMidlet.disp);
            }
        }
        if (item == buttonsms) {
            if (buttonsms.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new SendSms());
            }
        }
        if (item == buttonreception) {
            if (buttonreception.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new SMSReceive());
            }
        }
        if (item == buttonaddphoto) {
            if (buttonaddphoto.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new FileBrowserClasse(id, utils.StaticMidlet.disp));
            }
        }
        if (item == buttondesactiver) {
            if (buttondesactiver.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, this));
            }
        }
        if (item == buttonmodifier) {
            if (buttonmodifier.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, this));
            }
        }
        if (item == buttonconnexion) {
            if (buttonconnexion.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new Authentification());
            }
        }
        if (item == buttondeconnexion) {
            if (buttondeconnexion.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new Logout());
            }
        }
        if (item == buttonrechercher) {
            if (buttonrechercher.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new RechercheUtilisateur());
            }
        }
        if (item == buttonprofil) {
            if (buttonprofil.isClicked()) {
                utils.StaticMidlet.disp.setCurrent(new ConsulterProfil(id, this));
            }
        }
    }

}
