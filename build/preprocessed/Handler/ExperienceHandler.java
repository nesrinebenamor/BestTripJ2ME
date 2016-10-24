package Handler;


import Entity.Experience;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ExperienceHandler extends DefaultHandler{
    private Vector experiences;
    String idTag = "close";
    String titreTag = "close";
    String contTag = "close";
    String paysTag= "close";
    String typeTag="close";
    String climatTag="close";
    String depenseTag="close";

    public ExperienceHandler() {
        experiences = new Vector();
    }

    public Experience[] getExperience() {
        Experience[] experiencess = new Experience[experiences.size()];
        experiences.copyInto(experiencess);
        return experiencess;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Experience currentExperience;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("experience")) {
            currentExperience = new Experience();
            //2ème methode pour parser les attributs
            currentExperience.setId(attributes.getValue("Id"));
            currentExperience.setTitre(attributes.getValue("Titre"));
            currentExperience.setContenu(attributes.getValue("Contenu"));
            currentExperience.setPays(attributes.getValue("pays"));
            currentExperience.setType(attributes.getValue("type"));
            currentExperience.setClimat(attributes.getValue("climat"));
            currentExperience.setDepense(attributes.getValue("depense"));
            /****/
            
        } else if (qName.equals("Id")) {
            idTag = "open";
        } else if (qName.equals("titre")) {
            titreTag = "open";
        } else if (qName.equals("contenu")) {
            contTag = "open";
        }else if (qName.equals("pays")) {
            paysTag = "open";
        }else if (qName.equals("type")) {
            typeTag = "open";
        }else if (qName.equals("climat")) {
            climatTag = "open";
        }else if (qName.equals("depense")) {
            depenseTag = "open";
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("experience")) {
            // we are no longer processing a <reg.../> tag
            experiences.addElement(currentExperience);
            currentExperience = null;
        } else if (qName.equals("Id")) {
            idTag = "close";
        } else if (qName.equals("titre")) {
            titreTag = "close";
        } else if (qName.equals("contenu")) {
            contTag = "close";
        }else if (qName.equals("pays")) {
            paysTag = "close";
        }else if (qName.equals("type")) {
            typeTag = "close";
        }else if (qName.equals("climat")) {
            climatTag = "close";
        }else if (qName.equals("depense")) {
            depenseTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentExperience != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentExperience.setId(id);
            } else
                if (titreTag.equals("open")) {
                String titre = new String(ch, start, length).trim();
                currentExperience.setTitre(titre);
            } else
                    if (contTag.equals("open")) {
                String cont = new String(ch, start, length).trim();
                currentExperience.setContenu(cont);
            }else
                    if (paysTag.equals("open")) {
                String cont = new String(ch, start, length).trim();
                currentExperience.setPays(cont);
            }else
                    if (typeTag.equals("open")) {
                String cont = new String(ch, start, length).trim();
                currentExperience.setType(cont);
            }else
                    if (climatTag.equals("open")) {
                String cont = new String(ch, start, length).trim();
                currentExperience.setClimat(cont);
            }else
                    if (depenseTag.equals("open")) {
                String cont = new String(ch, start, length).trim();
                currentExperience.setDepense(cont);
            }
        }
    }
    
}
