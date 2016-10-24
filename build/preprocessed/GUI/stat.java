/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author esprit
 */
public class stat {
    
    private String Nom;

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public stat(String Nom) {
        this.Nom = Nom;
    }

    public stat() {
    }

    public String toString() {
        return "recommandation{" + "Nom=" + Nom + '}';
    }
    

   

   
    
    
}
