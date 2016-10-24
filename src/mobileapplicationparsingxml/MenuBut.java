/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplicationparsingxml;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author esprit
 */
public class MenuBut extends Form implements CommandListener, ItemCommandListener{
    private static final Command CMD_GO = new Command("Go", Command.ITEM, 1);
    private static final Command CMD_PRESS = new Command("Press", Command.ITEM, 1);
    private static final Command CMD_EXIT = new Command("Exit", Command.EXIT, 1);
    private Form mainForm;

    public MenuBut() {
        super("Meni");
         mainForm = new Form("String Item Demo");
        mainForm.append("This is a simple label");

        StringItem item =
            new StringItem("This is a StringItem label: ", "This is the StringItems text");
        //mainForm.append(item);
        append(item);
        item = new StringItem("Short label: ", "text");
        append(item);
        item = new StringItem("Hyper-Link ", "hyperlink", Item.HYPERLINK);
        item.setDefaultCommand(CMD_GO);
        item.setItemCommandListener(this);
        append(item);
        item = new StringItem("Button ", "Button", Item.BUTTON);
        item.setDefaultCommand(CMD_PRESS);
        item.setItemCommandListener(this);
        append(item);
        addCommand(CMD_EXIT);
        setCommandListener(this);
        utils.StaticMidlet.disp.setCurrent(this);
    }

    public void commandAction(Command c, Displayable d) {
      
        notifyAll();
    }

    public void commandAction(Command c, Item item) {
      if (c == CMD_GO) {
            String text = "Go to the URL...";
            Alert a = new Alert("URL", text, null, AlertType.INFO);
            utils.StaticMidlet.disp.setCurrent(a);
        } else if (c == CMD_PRESS) {
            String text = "Do an action...";
            Alert a = new Alert("Action", text, null, AlertType.INFO);
            utils.StaticMidlet.disp.setCurrent(a);
        }
    }
    
}
