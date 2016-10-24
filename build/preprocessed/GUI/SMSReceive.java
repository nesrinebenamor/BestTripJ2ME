/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author esprit
 */
public class SMSReceive extends Form implements CommandListener,Runnable{

   /** user interface command for indicating Exit request. */
    Command exitCommand = new Command("Exit", Command.EXIT, 2);

    /** user interface command for indicating Reply request */
    Command replyCommand = new Command("Reply", Command.OK, 1);

    /** user interface text box for the contents of the fetched URL. */
    Alert content;
    /** instance of a thread for asynchronous networking and user interface. */
    Thread thread;

    /** Connections detected at start up. */
    String[] connections;

    /** Flag to signal end of processing. */
    boolean done;

    /** The port on which we listen for SMS messages */
    String smsPort="50000";

    /** SMS message connection for inbound text messages. */
    MessageConnection smsconn;

    /** Current message read from the network. */
    Message msg;

    /** Address of the message's sender */
    String senderAddress;

    /** Alert that is displayed when replying */
    Alert sendingMessageAlert;

    /** Prompts for and sends the text reply */
  //  mobileapplicationparsingxml.SMSSender sender;
    
    GUI.SMSSender sender2;

    /** The screen to display when we return from being paused */
    Displayable resumeScreen;

    /**
     * Initialize the MIDlet with the current display object and
     * graphical components.
     */
    
    public SMSReceive() {
        super("Rececption");
        
        content = new Alert("SMS Receive");
        content.setTimeout(Alert.FOREVER);
        content.addCommand(exitCommand);
        content.setCommandListener(this);
        content.setString("Receiving...");
        
        addCommand(exitCommand);
        this.setCommandListener(this);
        content.setString("Receiving...");

        sendingMessageAlert = new Alert("SMS", null, null, AlertType.INFO);
        sendingMessageAlert.setTimeout(5000);
        sendingMessageAlert.setCommandListener(this);

        //sender = new mobileapplicationparsingxml.SMSSender(smsPort, utils.StaticMidlet.disp, content, sendingMessageAlert);
sender2=new GUI.SMSSender(smsPort, utils.StaticMidlet.disp, content, sendingMessageAlert);
        resumeScreen = content;
        
           String smsConnection = "sms://:" + smsPort;

        /** Open the message connection. */
        if (smsconn == null) {
            try {
                smsconn = (MessageConnection)Connector.open(smsConnection);
                smsconn.setMessageListener((MessageListener) this);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        /** Initialize the text if we were started manually. */
        connections = PushRegistry.listConnections(true);

        if ((connections == null) || (connections.length == 0)) {
            //content.setString("Waiting for SMS on port " + smsPort + "...");
            utils.StaticMidlet.disp.setCurrent(new Alert("Waiting for SMS on port " + smsPort + "...", "", null, AlertType.INFO));
        }

        done = false;
        thread = new Thread(this);
        thread.start();

       // utils.StaticMidlet.disp.setCurrent(resumeScreen);
        
    }
    
     /**
     * Notification that a message arrived.
     * @param conn the connection with messages available
     */
    public void notifyIncomingMessage(MessageConnection conn) {
        if (thread == null) {
            done = false;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    
      /**
     * Pause signals the thread to stop by clearing the thread field.
     * If stopped before done with the iterations it will
     * be restarted from scratch later.
     */
    public void pauseApp() {
        done = true;
        thread = null;
        resumeScreen = utils.StaticMidlet.disp.getCurrent();
    }

    /**
     * Destroy must cleanup everything.  The thread is signaled
     * to stop and no result is produced.
     * @param unconditional true if a forced shutdown was requested
     */
    public void destroyApp(boolean unconditional) {
        done = true;
        thread = null;

        if (smsconn != null) {
            try {
                smsconn.close();
            } catch (IOException e) {
                // Ignore any errors on shutdown
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
         try {
            if ((c == exitCommand) || (c == Alert.DISMISS_COMMAND)) {
           //       destroyApp(false);
                this.destroyApp(false);
                
                utils.StaticMidlet.disp.setCurrent(new Menumezyen());
            } else if (c == replyCommand) {
                reply();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
          /** Check for sms connection. */
        try {
            msg = smsconn.receive();

            if (msg != null) {
                senderAddress = msg.getAddress();
                content.setTitle("From: " + senderAddress);

                if (msg instanceof TextMessage) {
                    content.setString(((TextMessage)msg).getPayloadText());
                } else {
                    StringBuffer buf = new StringBuffer();
                    byte[] data = ((BinaryMessage)msg).getPayloadData();

                    for (int i = 0; i < data.length; i++) {
                        int intData = (int)data[i] & 0xFF;

                        if (intData < 0x10) {
                            buf.append("0");
                        }

                        buf.append(Integer.toHexString(intData));
                        buf.append(' ');
                    }

                   // content.setString(buf.toString());
                }

                addCommand(replyCommand);
                utils.StaticMidlet.disp.setCurrent(this);
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
    
    
      private void reply() {
        // remove the leading "sms://" for displaying the destination address
        String address = senderAddress.substring(6);
        String statusMessage = "Sending message to " + address + "...";
      //sendingMessageAlert.setString(statusMessage);
         Alert alert = new Alert("Sending message to " + address + "...", "", null, AlertType.INFO);
         alert.setTimeout(Alert.FOREVER);
         utils.StaticMidlet.disp.setCurrent(alert);
        sender2.promptAndSend(senderAddress);
    }
    
}
