/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author esprit
 */
public class SendSms extends Form implements CommandListener,Runnable{
   
    private TextField toWhom;
    private TextField message;
    private Alert alert;
    private Command send, exit;
    MessageConnection clientConn;
    private Form compose;
    String login;

    public SendSms() {
        super("send Sms");
        compose = new Form("Compose Message");
        toWhom = new TextField("To", "", 10, TextField.PHONENUMBER);
        message = new TextField("Message", "", 600, TextField.ANY);
        send = new Command("Send", Command.BACK, 0);
        exit = new Command("Exit", Command.SCREEN, 5);
//        compose.append(toWhom);
//        compose.append(message);
//        compose.addCommand(send);
//        compose.addCommand(exit);
//        compose.setCommandListener(this);
//        this.setCommandListener(this);
        
       
        append(toWhom);
        append(message);
        addCommand(send);
        addCommand(exit);
        
         addCommand(send);
        this.setCommandListener(this);
        addCommand(exit);
        this.setCommandListener(this);
       
    }

    public void commandAction(Command c, Displayable d) {
         if (c == exit) {
             utils.StaticMidlet.disp.setCurrent(new Menumezyen());
        }
        if (c == send) {
            Thread th = new Thread(this);
            th.start();
        }
    }

    public void run() {
           String mno = toWhom.getString();
        String msg = message.getString();
        if (mno.equals("")) {
            alert = new Alert("Alert");
            alert.setString("Enter Mobile Number!!!");
            alert.setTimeout(2000);
            utils.StaticMidlet.disp.setCurrent(alert);
        } else {
            try {
                clientConn = (MessageConnection) Connector.open("sms://" + mno);
            } catch (Exception e) {
                alert = new Alert("Alert");
                alert.setString("Unable to connect to Station because of network problem");
                alert.setTimeout(2000);
                utils.StaticMidlet.disp.setCurrent(alert);
            }
            try {
                TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                textmessage.setAddress("sms://" + mno);
                textmessage.setPayloadText(msg);
                clientConn.send(textmessage);
                 Alert alert2 = new Alert("Votre message a été envoyé avec succés", "", null, AlertType.INFO);
                alert2.setTimeout(Alert.FOREVER);
                alert2.setString("Envoi avec succés");
                utils.StaticMidlet.disp.setCurrent(alert2);
            } catch (Exception e) {
                Alert alert = new Alert("Alert", "", null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                alert.setString("Unable to send");
                utils.StaticMidlet.disp.setCurrent(alert);
            }
        }
    }
    
}
