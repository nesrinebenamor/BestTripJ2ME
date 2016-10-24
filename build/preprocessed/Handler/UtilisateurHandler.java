/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Handler;

import mobileapplicationparsingxml.*;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author esprit
 */
public class UtilisateurHandler extends DefaultHandler{
    private Vector personnes;
    String idTag = "close";
    String nomTag = "close";
    String prenTag = "close";
    String emailTag = "close";
    String passTag = "close";
    String usernameTag = "close";
    String ageTag = "close";
    String imageTag = "close";

     public UtilisateurHandler() {
        personnes = new Vector();
    }

    public Utilisateur[] getUtilisateur() {
        Utilisateur[] personness = new Utilisateur[personnes.size()];
        personnes.copyInto(personness);
        return personness;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Utilisateur currentPersonne;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("utilisateur")) {
            currentPersonne = new Utilisateur();
            //2ème methode pour parser les attributs
            currentPersonne.setId(attributes.getValue("id"));
            currentPersonne.setNom(attributes.getValue("nom"));
            currentPersonne.setPrenom(attributes.getValue("prenom"));
            currentPersonne.setEmail(attributes.getValue("email"));
            currentPersonne.setUsername(attributes.getValue("username"));
            currentPersonne.setPassword(attributes.getValue("password"));
            currentPersonne.setAge(attributes.getValue("age"));
             currentPersonne.setImage(attributes.getValue("image"));
            /****/
            
        } else if (qName.equals("id")) {
            idTag = "open";
        } else if (qName.equals("nom")) {
            nomTag = "open";
        } else if (qName.equals("prenom")) {
            prenTag = "open";
        }else if (qName.equals("username")) {
            usernameTag = "open";
        } else if (qName.equals("email")) {
            emailTag = "open";
        }else if (qName.equals("password")) {
            passTag = "open";
        }else if (qName.equals("age")) {
            ageTag = "open";
        }else if (qName.equals("image")) {
            imageTag = "open";
        }
        
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("utilisateur")) {
            // we are no longer processing a <reg.../> tag
            personnes.addElement(currentPersonne);
            currentPersonne = null;
        } else if (qName.equals("id")) {
            idTag = "close";
        } else if (qName.equals("nom")) {
            nomTag = "close";
        } else if (qName.equals("prenom")) {
            prenTag = "close";
        }else if (qName.equals("username")) {
            usernameTag = "close";
        }else if (qName.equals("email")) {
            emailTag = "close";
        }else if (qName.equals("password")) {
            passTag = "close";
        }else if (qName.equals("age")) {
            ageTag = "close";
        }else if (qName.equals("image")) {
            imageTag = "close";
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentPersonne != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (idTag.equals("open")) {
                String id = new String(ch, start, length).trim();
                currentPersonne.setId(id);
            } else
                if (nomTag.equals("open")) {
                String nom = new String(ch, start, length).trim();
                currentPersonne.setNom(nom);
            } else
                    if (prenTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentPersonne.setPrenom(pren);
            }
            else
                    if (passTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentPersonne.setPassword(pren);
            }else
                    if (usernameTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentPersonne.setUsername(pren);
            }else
                    if (emailTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentPersonne.setEmail(pren);
            }else
                    if (ageTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentPersonne.setAge(pren);
            }else
                    if (imageTag.equals("open")) {
                String pren = new String(ch, start, length).trim();
                currentPersonne.setImage(pren);
            }
        }
    }
    

}
