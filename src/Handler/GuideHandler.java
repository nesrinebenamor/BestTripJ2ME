package Handler;




import Entity.Guide;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
public class GuideHandler extends DefaultHandler{
    private Vector guides;
    String idTag = "close";
    String nomTag = "close";
    String resumTag = "close";
    String prixTag = "close";
    String dateTag = "close";
    public GuideHandler() {
        guides = new Vector();
    }
    
    public Guide[] getGuide() {
        Guide[] guidess = new Guide[guides.size()];
        guides.copyInto(guidess);
        return guidess;
    }
    private Guide currentGuide;
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("guide")) {
            if(attributes.getValue("id") != null)
            {
            currentGuide = new Guide();
            //2ème methode pour parser les attributs
            currentGuide.setId(Integer.parseInt(attributes.getValue("id")));
            currentGuide.setNom(attributes.getValue("nom"));
            currentGuide.setResume(attributes.getValue("resume"));
             currentGuide.setPrix(Float.parseFloat(attributes.getValue("prix")));
            currentGuide.setDate(attributes.getValue("date"));
            /****/
            }
        } else if (qName.equals("id")) {
            idTag = "open";
        }
        else if (qName.equals("nom")) {
            nomTag = "open";
        }
        else if (qName.equals("resume")) {
            resumTag = "open";
        } else if (qName.equals("prix")) {
            prixTag = "open";
        }
        else if (qName.equals("date")) {
            dateTag = "open";
        }
    }
     public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("guide")) {
            // we are no longer processing a <reg.../> tag
            guides.addElement(currentGuide);
            currentGuide = null;
        } else if (qName.equals("id")) {
            idTag = "close";
        }
        else if (qName.equals("nom")) {
            nomTag = "close";
        } 
        else if (qName.equals("resume")) {
            resumTag = "close";
        } else if (qName.equals("prix")) {
            prixTag = "close";
        }
        else if (qName.equals("date")) {
            dateTag = "close";
        }
    }
      public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentGuide != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentGuide.setId(id);
            } else
                if (resumTag.equals("open")) {
                String resume = new String(ch, start, length).trim();
                currentGuide.setResume(resume);
            }else
                if (nomTag.equals("open")) {
                String nom = new String(ch, start, length).trim();
                currentGuide.setResume(nom);
            }
                else
                    if (prixTag.equals("open")) {
                String prix = new String(ch, start, length).trim();
                currentGuide.setPrix(prix);
            }
            else
                    if (dateTag.equals("open")) {
                String date = new String(ch, start, length).trim();
                currentGuide.setDate(date);
            }
        }
    }
}
