/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

/**
 *
 * @author hazem
 */
public class Sponsor {
    private int id;
    private int event_id;
    private String nom;
    private String type;
    private String image;

    public Sponsor(int id, int event_id, String nom, String type, String image) {
        this.id = id;
        this.event_id = event_id;
        this.nom = nom;
        this.type = type;
        this.image = image;
    }

    public Sponsor(int event_id, String nom, String type, String image) {
        this.event_id = event_id;
        this.nom = nom;
        this.type = type;
        this.image = image;
    }

    public Sponsor(String nom, String type, String image) {
        this.nom = nom;
        this.type = type;
        this.image = image;
    }

    public Sponsor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Sponsor{" + "id=" + id + ", event_id=" + event_id + ", nom=" + nom + ", type=" + type + ", image=" + image + '}';
    }
    
}
