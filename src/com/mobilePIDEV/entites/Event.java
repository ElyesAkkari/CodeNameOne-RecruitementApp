/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

import java.util.Date;

/**
 *
 * @author hazem
 */
public class Event {
    private int id;
    private String nom;
    private String type;
    private float prix;
    private String description;
    private Date date_inscription;
    private String image;

    public Event(int id, String nom, String type, float prix, String description, Date date_inscription, String image) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.description = description;
        this.date_inscription = date_inscription;
        this.image = image;
    }

    public Event(String nom, String type, float prix, String description, Date date_inscription, String image) {
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.description = description;
        this.date_inscription = date_inscription;
        this.image = image;
    }

    public Event() {
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", nom=" + nom + ", type=" + type + ", prix=" + prix + ", description=" + description + ", date_inscription=" + date_inscription + ", image=" + image + '}';
    }
    
}
