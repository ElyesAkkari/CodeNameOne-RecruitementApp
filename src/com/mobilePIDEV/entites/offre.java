/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

import java.util.Date;

/**
 *
 * @author malek
 */
public class offre {
     private int id ;
    private String noms ;
    private String description;
    private String type ; 
    private String mailen ; 
    private String departement ;
    private Date datedeb ;
    private Date datefin ;
    private String image_name ;
    private Double rating;
  
    
    
    public offre (){}
    
    public offre(int id, String noms, String description, String type, String mailen, String departement, Date datedeb, Date datefin, String image_name, Double rating) {
        this.id = id;
        this.noms = noms;
        this.description = description;
        this.type = type;
        this.mailen = mailen;
        this.departement = departement;
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.image_name = image_name;
        this.rating = rating;
    }

    public offre(String type,String noms, String description , String image_name, String mailen, String departement,Date datedeb,Date datefin)
    
  
    {
        this.noms = noms;
        this.description = description;
        this.type = type;
        this.mailen = mailen;
        this.departement = departement;
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.image_name = image_name;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoms() {
        return noms;
    }

    public void setNoms(String noms) {
        this.noms = noms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMailen() {
        return mailen;
    }

    public void setMailen(String mailen) {
        this.mailen = mailen;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(Date datedeb) {
        this.datedeb = datedeb;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "offre{" + "id=" + id + ", noms=" + noms + ", description=" + description + ", type=" + type + ", mailen=" + mailen + ", departement=" + departement + ", datedeb=" + datedeb + ", datefin=" + datefin + ", image_name=" + image_name + ", rating=" + rating + '}';
    }
    
    
    
    
    
    
}
