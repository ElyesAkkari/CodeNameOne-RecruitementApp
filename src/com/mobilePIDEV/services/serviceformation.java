/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mobilePIDEV.entites.formateur;
import com.mobilePIDEV.entites.formation;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author 21628
 */
public class serviceformation {
        boolean resultOK;
    ConnectionRequest req;
    static serviceformation instance;
    ArrayList<formation> post = new ArrayList<>();
    
     private serviceformation()
    {
        req = new ConnectionRequest();
    }
    public static serviceformation getInstance(){
        
        if (instance == null) {
            instance = new serviceformation();
        }
        
        return instance;
    }
    
    
     //ADD formateur
    public boolean addformationAction(formation f){
        
       
      // String url = Statics.BASE_URL + "/formation/addformation?prix="+ f.getPrix()+ "&datedeb=" + f.getDatedeb()+"&nbrparticipant="+f.getNbrparticipant()+"&nbrheures="+f.getNbrheures()+"&nom="+f.getNom()+"&image="+f.getImage();
       String url = Statics.BASE_URL+"addformation"+ "?prix="+f.getPrix()+ "&datedeb=" + f.getDatedeb()+"&nbrparticipant="+f.getNbrparticipant()+"&nbrheures="+f.getNbrheures()+"&nom="+f.getNom()+"&image="+f.getImage();

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((evt) -> {
            resultOK = req.getResponseCode()==200;
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
      public ArrayList<formation> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> posteListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> postList = (ArrayList<Map<String,Object>>) posteListJson.get("root");
           
            for (Map<String, Object> obj : postList) {
                
                formation t = new formation();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                float prix = Float.parseFloat(obj.get("prix").toString());

                t.setPrix((int)prix );
//                t.setDescription(obj.get("description").toString());
             //   float nbrheures = Float.parseFloat(obj.get("nbrheures").toString());

         //       t.setNbrheures((int)nbrheures );
                t.setNom(obj.get("nom").toString());
                t.setImage(obj.get("image").toString());
//                float nbrparticipant = Float.parseFloat(obj.get("nbrparticipant").toString());

            //    t.setNbrparticipant((int)nbrparticipant );
               // t.setDatedeb(obj.get("datedeb").toString());

                
                
                
                
                
                
                post.add(t);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return post;  
    }
      
         public ArrayList<formation> getformation(){
        
      //   String url = Statics.BASE_URL+"/displayformation/";
         String url = Statics.BASE_URL+"displayformation";
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
         
         public ArrayList<formation> DeleteformationtAction(int id){
        String url =Statics.BASE_URL + "formation/deleteformation" + "?id=" + id;
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
         
            public boolean UpdateformationrAction(formation f) {
       // String url = Statics.BASE_URL + "/addposteJson/new?prix="+ f.getPrix()+ "&datedeb=" + f.getDatedeb()+"&nbrparticipant="+f.getNbrparticipant()+"&nbrheures="+f.getNbrheures()+"&nom="+f.getNom()+"&image="+f.getImage();
        String url = Statics.BASE_URL+"formation/updateformation" + "?id=" + f.getId()+ "&prix="+f.getPrix()+ "&datedeb=" + f.getDatedeb()+"&nbrparticipant="+f.getNbrparticipant()+"&nbrheures="+f.getNbrheures()+"&nom="+f.getNom()+"&image="+f.getImage();

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
