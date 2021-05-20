/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.services;

import com.codename1.io.Preferences;

/**
 *
 * @author Ahmed
 */
public class SessionManager {
    
    
    public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data 
    
    
    
    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int id ; 
    private static String username; 
   private static String prenom; 
      private static int cin; 
      private static int numtel; 

    private static String email; 
    private static String passowrd;
    private static String photo;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id",id);// kif nheb njib id user connecté apres njibha men pref 
    }

    public static void setId(int id) {
        pref.set("id",id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

    public static String getUsername() {
        return pref.get("username",username);
    }

    public static void setUsername(String username) {
         pref.set("username",username);
    }
     public static String getPrenom() {
        return pref.get("prenom",prenom);
    }

    public static void setPrenom(String prenom) {
         pref.set("prenom",prenom);
    }

    public static String getEmail() {
        return pref.get("email",email);
    }

    public static void setEmail(String email) {
         pref.set("email",email);
    }

    public static String getPassowrd() {
        return pref.get("passowrd",passowrd);
    }

    public static void setPassowrd(String passowrd) {
         pref.set("passowrd",passowrd);
    }

    public static String getPhoto() {
        return pref.get("photo",photo);
    }

    public static void setPhoto(String photo) {
         pref.set("photo",photo);
    }
    
        public static int getCin() {
        return pref.get("cin",cin);
    }

    public static void setCin(int cin) {
         pref.set("cin",cin);
    }
    
            public static int getNum() {
        return pref.get("numtel",numtel);
    }

    public static void setNum(int numtel) {
         pref.set("numtel",numtel);
    }


    
    
}
