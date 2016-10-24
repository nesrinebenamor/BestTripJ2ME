/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;
import mobileapplicationparsingxml.*;
import java.io.IOException;

import javax.microedition.io.*;
import javax.microedition.lcdui.*;

import javax.wireless.messaging.*;


/**
 * Prompts for text and sends it via an SMS MessageConnection
 */
public class SMSSender implements CommandListener, Runnable {
    /** user interface command for indicating Send request */
    Command sendCommand = new Command("Send", Command.OK, 1);

    /** user interface command for going back to the previous screen */
    Command backCommand = new Command("Back", Command.BACK, 2);

    /** Display to use. */
    Display display;

    /** The port on which we send SMS messages */
    String smsPort="50000";

    /** The URL to send the message to */
    String destinationAddress;

    /** Area where the user enters a message to send */
    TextBox messageBox;

    /** Where to return if the user hits "Back" */
    Displayable backScreen;

    /** Displayed when a message is being sent */
    Displayable sendingScreen;

    /**
     * Initialize the MIDlet with the current display object and
     * graphical components.
     */
    public SMSSender(String smsPort, Display display, Displayable backScreen,
        Displayable sendingScreen) {
        this.smsPort = smsPort;
        this.display = display;
        this.destinationAddress = null;
        this.backScreen = backScreen;
        this.sendingScreen = sendingScreen;

        messageBox = new TextBox("Enter Message", null, 65535, TextField.ANY);
        messageBox.addCommand(backCommand);
        messageBox.addCommand(sendCommand);
        messageBox.setCommandListener(this);
    }

    /**
     * Prompt for message and send it
     */
    public void promptAndSend(String destinationAddress) {
        this.destinationAddress = destinationAddress;
        display.setCurrent(messageBox);
    }

    /**
     * Respond to commands, including exit
     * @param c user interface command requested
     * @param s screen object initiating the request
     */
    public void commandAction(Command c, Displayable s) {
        try {
            if (c == backCommand) {
                display.setCurrent(backScreen);
            } else if (c == sendCommand) {
                display.setCurrent(sendingScreen);
                new Thread(this).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send the message. Called on a separate thread so we don't have
     * contention for the display
     */
    public void run() {
        String address = destinationAddress + ":" + smsPort;

        MessageConnection smsconn = null;

        try {
            /** Open the message connection. */
            smsconn = (MessageConnection)Connector.open(address);

            TextMessage txtmessage =
                (TextMessage)smsconn.newMessage(MessageConnection.TEXT_MESSAGE);
            txtmessage.setAddress(address);
            txtmessage.setPayloadText(messageBox.getString());
            smsconn.send(txtmessage);
        } catch (Throwable t) {
            System.out.println("Send caught: ");
            t.printStackTrace();
        }

        if (smsconn != null) {
            try {
                smsconn.close();
            } catch (IOException ioe) {
                System.out.println("Closing connection caught: ");
                ioe.printStackTrace();
            }
        }
    }
}
