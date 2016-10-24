/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import GUI.stat;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author esprit
 */
public class statHandler extends DefaultHandler {

    private Vector recommandations;
    String Nom = "close";
    
             


    public statHandler() {
        recommandations = new Vector();
    }

    public stat[] getPersonne() {
        stat[] userss = new stat[recommandations.size()];
       recommandations.copyInto(userss);
        return userss;
    }
    
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private stat currentrecommandations;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("hos")) {
            currentrecommandations= new stat();
            //2ème methode pour parser les attributs
            currentrecommandations.setNom(attributes.getValue("Nom"));
            
           
         } else if (qName.equals("Nom")) {
            Nom = "open";
        }
    }
 public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("hos")) {
            // we are no longer processing a <reg.../> tag
            recommandations.addElement(currentrecommandations);
            currentrecommandations = null;
        } else if (qName.equals("Nom")) {
            Nom = "close";
        }
 }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
       if (currentrecommandations != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (Nom.equals("open")) {
                String type = new String(ch, start, length).trim();
                currentrecommandations.setNom(type);
            } 
        }
    }
}