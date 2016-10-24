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
public class Recommandation {
    private int id;
    private String contenu;
    private String nom;
    private String adresse;
    private int nombre;
    private int estcompagnie;

    public Recommandation(int id, String contenu, String nom, String adresse, int nombre, int estcompagnie) {
        this.id = id;
        this.contenu = contenu;
        this.nom = nom;
        this.adresse = adresse;
        this.nombre = nombre;
        this.estcompagnie = estcompagnie;
    }

    public Recommandation() {
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNombre() {
        return nombre;
    }
    

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
     public void setNombre(String nombre) {
        this.nombre = Integer.parseInt(nombre);
    }

    public int getEstcompagnie() {
        return estcompagnie;
    }
    

    public void setEstcompagnie(int estcompagnie) {
        this.estcompagnie = estcompagnie;
    }
     public void setEstcompagnie(String estcompagnie) {
        this.estcompagnie = Integer.parseInt(estcompagnie);
    }
    public Recommandation getRecommandation() {
        return null;
    }
}
