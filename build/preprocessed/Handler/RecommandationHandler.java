/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Handler;

import Entity.Recommandation;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author esprit
 */
public class RecommandationHandler extends DefaultHandler {
        private Vector recommandations;
    String idTag = "close";
    String nomTag = "close";
    String contenuTag = "close";
    String adresseTag = "close";
    
    public RecommandationHandler() {
        recommandations = new Vector();
    }
    public Recommandation[] getRecommandation() {
        Recommandation[] recommandationss = new Recommandation[recommandations.size()];
        recommandations.copyInto(recommandationss);
        return recommandationss;
    }
    private Recommandation currentRecommandation;
    
     public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("recommandation")) {
            
               
            currentRecommandation = new Recommandation();
            //2ème methode pour parser les attributs
            currentRecommandation.setId(Integer.parseInt(attributes.getValue("Id")));
            currentRecommandation.setNom(attributes.getValue("Nom"));
            currentRecommandation.setContenu(attributes.getValue("contenu"));
            currentRecommandation.setAdresse(attributes.getValue("Adresse"));
            
           
            /****/
            
        } else if (qName.equals("Id")) {
            idTag = "open";
        } else if (qName.equals("Nom")) {
            nomTag = "open";
        } else if (qName.equals("contenu")) {
            contenuTag = "open";
        }else if (qName.equals("Adresse")) {
            adresseTag = "open";
        }
    }
     
     public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("recommandation")) {
            // we are no longer processing a <reg.../> tag
            recommandations.addElement(currentRecommandation);
            currentRecommandation = null;
        } else if (qName.equals("Id")) {
            idTag = "close";
        } else if (qName.equals("Nom")) {
            nomTag = "close";
        } else if (qName.equals("contenu")) {
            contenuTag = "close";
        }else if (qName.equals("Adresse")) {
            adresseTag = "close";
        }
    }
     
      public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentRecommandation != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentRecommandation.setId(id);
            }else
                    if (nomTag.equals("open")) {
                String nom = new String(ch, start, length).trim();
                currentRecommandation.setNom(nom);
            } 
            else
                if (contenuTag.equals("open")) {
                String contenu = new String(ch, start, length).trim();
                currentRecommandation.setContenu(contenu);
            } else
                    if (adresseTag.equals("open")) {
                String adresse = new String(ch, start, length).trim();
                currentRecommandation.setAdresse(adresse);
            }
        }
    }
    
    
}
