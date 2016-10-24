package Handler;


import Entity.Video;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class VideoHandler extends DefaultHandler{
    private Vector videos;
    String idTag = "close";
    String exTag = "close";
    String imgTag = "close";


    public VideoHandler() {
        videos = new Vector();
    }

    public Video[] getVideo() {
        Video[] videoss = new Video[videos.size()];
        videos.copyInto(videoss);
        return videoss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Video currentVideo;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("video")) {
            currentVideo = new Video();
            //2ème methode pour parser les attributs
            currentVideo.setId(attributes.getValue("Id"));
            currentVideo.setVid(attributes.getValue("source"));
            currentVideo.setDescription(attributes.getValue("extension"));

            /****/
            
        } else if (qName.equals("Id")) {
            idTag = "open";
        } else if (qName.equals("extension")) {
            exTag = "open";
        } else if (qName.equals("source")) {
            imgTag = "open";
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("video")) {
            // we are no longer processing a <reg.../> tag
            videos.addElement(currentVideo);
            currentVideo = null;
        } else if (qName.equals("Id")) {
            idTag = "close";
        } else if (qName.equals("extension")) {
            exTag = "close";
        } else if (qName.equals("source")) {
            imgTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentVideo != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentVideo.setId(id);
            } else
                if (exTag.equals("open")) {
                String desc = new String(ch, start, length).trim();
                currentVideo.setDescription(desc);
            } else
                    if (imgTag.equals("open")) {
                String source = new String(ch, start, length).trim();
                currentVideo.setVid(source);
            }
        }
    }
    
}
