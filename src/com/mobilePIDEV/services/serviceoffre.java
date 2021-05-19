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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mobilePIDEV.entites.offre;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author malek
 */
public class serviceoffre {
    
     public static serviceoffre instance = null;
   private ConnectionRequest req;
   public static serviceoffre getInstance(){
   
   if(instance == null)
       instance = new  serviceoffre();
    return instance ;
   }
   
   public serviceoffre(){
   
   req = new ConnectionRequest();
   }
   
   
   
   public void ajoutoffre(offre offre){
       SimpleDateFormat    formatter = new SimpleDateFormat("dd-M-yyyy");  
       String   datedebut = formatter.format(offre.getDatedeb());
       
       
    String url = Statics.BASE_URL+"addoffreJson?type="+offre.getType()+"&noms="+offre.getNoms()+"&description="+offre.getDescription()+"&image_name="+offre.getImage_name()+"&mailen="+offre.getMailen()+"&departement="+offre.getDepartement()+"&datedeb="+datedebut;
        req.setUrl(url);
        req.addResponseListener((e)-> {
           
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        
        });
       NetworkManager.getInstance().addToQueueAndWait(req);
 }
   
   
   public ArrayList<offre>affichageoffre(){
   ArrayList<offre> result = new ArrayList<>();
   String url = Statics.BASE_URL+"listoffre";
   req.setUrl(url);
   req.addResponseListener(new ActionListener<NetworkEvent>() {
       @Override
       public void actionPerformed(NetworkEvent evt) {
           JSONParser jsonp;
           jsonp = new JSONParser();
           try {
             Map<String,Object>mapoffre = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
           
             List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapoffre.get("root");
             for (Map<String,Object> obj : listOfMaps)
             {
               offre of = new offre(); 
               float id = Float.parseFloat(obj.get("id").toString());
               String type = obj.get("type").toString();
               String description = obj.get("description").toString();
               String noms = obj.get("noms").toString();
               String imagename = obj.get("imageName").toString();
               
               of.setId((int)id);
               of.setType(type);
               of.setNoms(noms);
               of.setDescription(description);
               of.setImage_name(imagename);
               
               result.add(of);
               
             }
           }catch (Exception ex){
           ex.printStackTrace();
           }
       }
   });
   NetworkManager.getInstance().addToQueueAndWait(req);
     return  result;
   }
       
   public offre affichoffre(int id ){
        offre e = new offre(); 
        String url = Statics.BASE_URL+"afficherUneSeuleoffreJson?id="+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
       
         String json = new String(req.getResponseData());
          System.out.println(json);
                 try {
          JSONParser j = new JSONParser();
         
            Map<String, Object>  events = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
                float id = Float.parseFloat(events.get("id").toString());
               e.setId((int) id);
                e.setNoms(events.get("noms").toString());
              
               e.setDepartement(events.get("departement").toString());
                e.setDescription(events.get("description").toString());
                e.setType(events.get("type").toString());
                e.setMailen(events.get("mailen").toString());
            
               
                e.setImage_name(events.get("imageName").toString());
               
               
                   
                 }
              catch (IOException ex) {
        }
                 }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return e;
    }
   
   
   
    

    
}
