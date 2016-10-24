/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Entity.Reponse;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author esprit
 */
public class ReponseHandler extends DefaultHandler{
    
     private Vector reponse;
    String idTag = "close";
    String contenuTag = "close";
    
    public ReponseHandler() {
        reponse = new Vector();
    }
    
    public Reponse[] getReponse() {
        Reponse[] reponses = new Reponse[reponse.size()];
        reponse.copyInto(reponses);
        return reponses;
    }
    
    private Reponse currentReponse;
    
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("reponse")) {
            currentReponse = new Reponse();
           
            currentReponse.setContenu(attributes.getValue("contenu"));
            
            /****/
            
        } else if (qName.equals("id")) {
            idTag = "open";
        } else if (qName.equals("contenu")) {
            contenuTag = "open";
        } 
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("reponse")) {
            // we are no longer processing a <reg.../> tag
            reponse.addElement(currentReponse);
            currentReponse = null;
        } else if (qName.equals("id")) {
            idTag = "close";
        } else if (qName.equals("contenu")) {
            contenuTag = "close";
        }
    }
    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentReponse != null) {
          
                if (contenuTag.equals("open")) {
                String contenu = new String(ch, start, length).trim();
                currentReponse.setContenu(contenu);
            } 
        }
    }
    
    
}
