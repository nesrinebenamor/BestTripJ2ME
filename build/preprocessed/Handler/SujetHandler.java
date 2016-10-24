/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Entity.Sujet;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author esprit
 */
public class SujetHandler extends DefaultHandler{
    private Vector sujet;
    String idTag = "close";
    String titreTag = "close";
    String textTag = "close";
    String catTag = "close";
    
    public SujetHandler() {
        sujet = new Vector();
    }
    
    public Sujet[] getSujet() {
        Sujet[] sujets = new Sujet[sujet.size()];
        sujet.copyInto(sujets);
        return sujets;
    }
    
    private Sujet currentSujet;
    
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("sujet")) {
            currentSujet = new Sujet();
            //2ème methode pour parser les attributs
            currentSujet.setId(attributes.getValue("Id"));
            currentSujet.setTitre(attributes.getValue("titre"));
            currentSujet.setText(attributes.getValue("text"));
            currentSujet.setCategorie(attributes.getValue("categorie"));
            /****/
            
        } else if (qName.equals("Id")) {
            idTag = "open";
        } else if (qName.equals("titre")) {
            titreTag = "open";
        } else if (qName.equals("text")) {
            textTag = "open";
        }
         else if (qName.equals("categorie")) {
            catTag = "open";
        }
    }
    
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("sujet")) {
            // we are no longer processing a <reg.../> tag
            sujet.addElement(currentSujet);
            currentSujet = null;
        } else if (qName.equals("Id")) {
            idTag = "close";
        } else if (qName.equals("titre")) {
            titreTag = "close";
        } else if (qName.equals("text")) {
            textTag = "close";
        }
        else if (qName.equals("categorie")) {
            catTag = "close";
        }
    }
    
    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentSujet != null) {
            // don't forget to trim excess spaces from the ends of the string
            /*if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentSujet.setId(id);
            } */ 
            //else
                if (titreTag.equals("open")) {
                String titre = new String(ch, start, length).trim();
                currentSujet.setTitre(titre);
            } else
                    if (textTag.equals("open")) {
                String text = new String(ch, start, length).trim();
                currentSujet.setText(text);
            }else
                    if (textTag.equals("open")) {
                String categorie = new String(ch, start, length).trim();
                currentSujet.setCategorie(categorie);
            }
        }
    }
    
}
