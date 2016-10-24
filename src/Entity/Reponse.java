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
public class Reponse {
    
    private int id;
    private String contenu;

    public Reponse(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
    }
    
    public Reponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String toString() {
        return "Reponse{" + "id=" + id + ", contenu=" + contenu + '}';
    }
    
    Sujet getReponse() {
        return null;
    }

}
