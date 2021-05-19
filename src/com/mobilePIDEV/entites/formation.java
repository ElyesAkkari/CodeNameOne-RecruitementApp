/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;
import java.util.Date;

/**
 *
 * @author 21628
 */
public class formation {
     public  int id;
    private int prix;
    private Date datedeb;
    private int nbrparticipant ;
    private int nbrheures ;
    private String nom;
    private String image;
private String description;

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Date getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(Date datedeb) {
        this.datedeb = datedeb;
    }

    public int getNbrparticipant() {
        return nbrparticipant;
    }

    public void setNbrparticipant(int nbrparticipant) {
        this.nbrparticipant = nbrparticipant;
    }

    public int getNbrheures() {
        return nbrheures;
    }

    public void setNbrheures(int nbrheures) {
        this.nbrheures = nbrheures;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public formation(int prix, Date datedeb, int nbrparticipant, int nbrheures, String nom, String image, String description) {
        this.prix = prix;
        this.datedeb = datedeb;
        this.nbrparticipant = nbrparticipant;
        this.nbrheures = nbrheures;
        this.nom = nom;
        this.image = image;
        this.description = description;
    }

    public formation(int id, int prix, Date datedeb, int nbrparticipant, int nbrheures, String nom, String image, String description) {
        this.id = id;
        this.prix = prix;
        this.datedeb = datedeb;
        this.nbrparticipant = nbrparticipant;
        this.nbrheures = nbrheures;
        this.nom = nom;
        this.image = image;
        this.description = description;
    }

    @Override
    public String toString() {
        return "formation{" + "id=" + id + ", prix=" + prix + ", datedeb=" + datedeb + ", nbrparticipant=" + nbrparticipant + ", nbrheures=" + nbrheures + ", nom=" + nom + ", image=" + image + ", description=" + description + '}';
    }

    public formation() {
    }

    public formation(String nom, String description, String image) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        
    }

    public formation( String nom, String image, String description,Date datedeb,int prix,int nbrparticipant,int nbrheures) {
        this.datedeb = datedeb;
        this.nom = nom;
        this.image = image;
        this.description = description;
         this.prix = prix;
this.nbrparticipant = nbrparticipant;
        this.nbrheures = nbrheures;

    }

  
    
}
