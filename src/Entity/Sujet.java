/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author esprit
 */
public class Sujet {
    
    private int id;
    private String titre;
    private String text;
    private String categorie;

    public Sujet(int id, String titre, String text, String categorie) {
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.categorie = categorie;
    }
    
    public Sujet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String toString() {
        return "Sujet{" + "id=" + id + ", titre=" + titre + ", text=" + text + ", categorie=" + categorie + '}';
    }
    
    Sujet getSujet() {
        return null;
    }
    

}
