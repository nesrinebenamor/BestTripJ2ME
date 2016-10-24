package Entity;




import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACER
 */
public class Guide {
    private int id ;
    private String nom;
    private String resume;
    private float prix;
 private String date;

   
 
 public Guide(){
     
 }

    public Guide(int id,String nom, String resume, float prix, String date) {
        this.id = id;
        this.nom = nom;
        this.resume = resume;
        this.prix = prix;
        this.date = date;
    }

    public String toString() {
        return "Guide{" + "id=" + id + ", nom=" + nom + ", resume=" + resume + ", prix=" + prix + ", date=" + date + '}';
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
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public float getPrix() {
        return prix;
    }
    
    public String getPrix1() {
        return String.valueOf(prix);
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    public void setPrix(String prix) {
        this.prix = Integer.parseInt(prix);
    }
    public String getDate() {
        return date;
    }

     public void setDate(String date) {
        this.date = date;
    }
   
            
}
