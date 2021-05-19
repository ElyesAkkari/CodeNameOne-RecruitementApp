/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

/**
 *
 * @author 21628
 */
public class formateur {
      private int id ;
    private String nom ;
    private String prenom ;
    private int numtel ;
    private String email ;
    private String image ;

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public formateur(int id, String nom, String prenom, int numtel, String email, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.image = image;
    }

    public formateur(String nom, String prenom, int numtel, String email, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.image = image;
    }

    @Override
    public String toString() {
        return "formateur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", numtel=" + numtel + ", email=" + email + ", image=" + image + '}';
    }

    public formateur() {
    }
    
}
