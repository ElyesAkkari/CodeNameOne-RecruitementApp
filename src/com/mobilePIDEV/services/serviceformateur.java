/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import static com.codename1.io.Log.p;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mobilePIDEV.entites.formateur;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author 21628
 */
public class serviceformateur {
      boolean resultOK;
    ConnectionRequest req;
    static serviceformateur instance;
    ArrayList<formateur> post = new ArrayList<>();
    
     private serviceformateur()
    {
        req = new ConnectionRequest();
    }
     
       public static serviceformateur getInstance(){
        
        if (instance == null) {
            instance = new serviceformateur();
        }
        
        return instance;
    }
        //ADD formateur
    public boolean addformateurAction(formateur f){
        
        String url = Statics.BASE_URL+"formateur/addformateur/"+ "?nom="+f.getNom()+ "&prenom=" + f.getPrenom()+"&numtel="+f.getNumtel()+"&email="+f.getEmail()+"&image="+f.getImage();

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((evt) -> {
            resultOK = req.getResponseCode()==200;
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     //PARSE TASKS JSON : convert JSON to java objects
    public ArrayList<formateur> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> posteListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> postList = (ArrayList<Map<String,Object>>) posteListJson.get("root");
           
            for (Map<String, Object> obj : postList) {
                
                formateur t = new formateur();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                t.setNom(obj.get("nom").toString());
                t.setPrenom(obj.get("prenom").toString());
                float numtel = Float.parseFloat(obj.get("numtel").toString());

                t.setNumtel((int)numtel );
                t.setEmail(obj.get("email").toString());
                t.setImage(obj.get("image").toString());
                
                
                
                
                
                post.add(t);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return post;  
    }
  //GET TASKS
    public ArrayList<formateur> getformateur(){
        
         String url = Statics.BASE_URL+"formateur/displayformateur";

         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             post = parseJSONAction(new String(request.getResponseData()));
             request.removeResponseListener((ActionListener<NetworkEvent>) this);
             }
         });
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return post;
    }
     public ArrayList<formateur> DeleteformateurtAction(int id){
        String url =Statics.BASE_URL + "formateur/deleteformateur" + "?id=" + id;
         ConnectionRequest req = new ConnectionRequest(url);
         System.out.println(url);
         req.setUrl(url);
         req.setPost(false);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
            
                    req.removeResponseListener(this);
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return post;
    } 
     
     
      public boolean UpdateformateurAction(formateur f) {
        String url = Statics.BASE_URL+"formateur/updateformateur" + "?id=" + f.getId()+ "&nom="+f.getNom()+ "&prenom=" + f.getPrenom()+"&numtel="+f.getNumtel()+"&email="+f.getEmail()+"&image="+f.getImage();

        ConnectionRequest req = new ConnectionRequest(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;   }
    
}
