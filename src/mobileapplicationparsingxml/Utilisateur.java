/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplicationparsingxml;

/**
 *
 * @author esprit
 */
public class Utilisateur {
    
    private int id;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String username;
    private String image;
    private int age;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String password, String email, String username, int age) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.username = username;
        this.age = age;
    }
    
    public Utilisateur(int id, String nom, String prenom, String password, String email, String username, int age,String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.username = username;
        this.age = age;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }
    
    public String getAge1()
    {
    return String.valueOf(age);}
    
     public void setAge(int age) {
         this.age = age;
    }

    public void setAge(String age) {
         this.age = Integer.parseInt(age);
    }
    
      public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", username=" + username + ", password=" + password + ", age=" + age +'}';
    }

    public Utilisateur getUtilisateur() {
        return null;
    }
    
}
