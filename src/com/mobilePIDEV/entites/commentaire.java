/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

//import java..Timestamp;

import java.util.Date;


/**
 *
 * @author malek
 */
public class commentaire {
    
    private int id ;
    private String message ;
    private int offre_id;
   private  Date  datetime ;
    private int username1_id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   
    
    public commentaire (){} 

    public commentaire(int id, String message, int offre_id, Date datetime, int username1_id, String username) {
        this.id = id;
        this.message = message;
        this.offre_id = offre_id;
        this.datetime = datetime;
        this.username1_id = username1_id;
        this.username = username;
    }
      public commentaire( String username, String message, Date datetime) {
         this.username = username;
        this.message = message;
      
        this.datetime = datetime;
       
       
    } 
      



    public int getOffre_id() {
        return offre_id;
    }

    public void setOffre_id(int offre_id) {
        this.offre_id = offre_id;
    }

    public int getUsername1_id() {
        return username1_id;
    }

    public void setUsername1_id(int username_id) {
        this.username1_id = username_id;
    }

   

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


    @Override
    public String toString() {
        return "commentaire{" + "id=" + id + ", message=" + message + ", datetime=" + datetime + '}';
    }
    
    
    
    
    
}
