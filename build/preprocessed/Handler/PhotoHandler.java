package Handler;


import Entity.Photo;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class PhotoHandler extends DefaultHandler{
    private Vector photos;
    String idTag = "close";
    String descTag = "close";
    String imgTag = "close";


    public PhotoHandler() {
        photos = new Vector();
    }

    public Photo[] getPhoto() {
        Photo[] photoss = new Photo[photos.size()];
        photos.copyInto(photoss);
        return photoss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Photo currentPhoto;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("photo")) {
            currentPhoto = new Photo();
            //2ème methode pour parser les attributs
            currentPhoto.setId(attributes.getValue("Id"));
            currentPhoto.setImage(attributes.getValue("source"));
            currentPhoto.setDescription(attributes.getValue("description"));

            /****/
            
        } else if (qName.equals("Id")) {
            idTag = "open";
        } else if (qName.equals("description")) {
            descTag = "open";
        } else if (qName.equals("source")) {
            imgTag = "open";
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("photo")) {
            // we are no longer processing a <reg.../> tag
            photos.addElement(currentPhoto);
            currentPhoto = null;
        } else if (qName.equals("Id")) {
            idTag = "close";
        } else if (qName.equals("description")) {
            descTag = "close";
        } else if (qName.equals("source")) {
            imgTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentPhoto != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentPhoto.setId(id);
            } else
                if (descTag.equals("open")) {
                String desc = new String(ch, start, length).trim();
                currentPhoto.setDescription(desc);
            } else
                    if (imgTag.equals("open")) {
                String source = new String(ch, start, length).trim();
                currentPhoto.setImage(source);
            }
        }
    }
    
}
