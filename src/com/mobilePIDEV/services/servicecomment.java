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
import com.mobilePIDEV.entites.commentaire;
import com.mobilePIDEV.entites.offre;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author malek
 */
public class servicecomment {
    
      public static serviceoffre instance = null;
   private ConnectionRequest req;
   public static serviceoffre getInstance(){
   
   if(instance == null)
       instance = new  serviceoffre();
    return instance ;
   }
   
   public servicecomment(){
   
   req = new ConnectionRequest();
   }
   
   
   
     
   public void ajoutoffre(commentaire commentaire ){
       //SimpleDateFormat    formatter = new SimpleDateFormat("dd-M-yyyy");  
       //String   datedebut = formatter.format(offre.getDatedeb());
       
       
    String url = Statics.BASE_URL+"addcommentaireJson?message="+commentaire.getMessage()+"&offre_id="+commentaire.getOffre_id()+"username1_id="+commentaire.getUsername1_id()+"&username="+commentaire.getUsername();
        req.setUrl(url);
        req.addResponseListener((e)-> {
           
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        
        });
       NetworkManager.getInstance().addToQueueAndWait(req);
 }
          
   public ArrayList<commentaire> getAllComments(int offre_id ){
       
   ArrayList<commentaire> result = new ArrayList<>();
   String url = Statics.BASE_URL+"affichercommentaire?offre_id="+offre_id;
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
               commentaire  of = new commentaire(); 
               float id = Float.parseFloat(obj.get("id").toString());
               String username = obj.get("username").toString();
               String message= obj.get("message").toString();
               //String noms = obj.get("noms").toString();
               //String imagename = obj.get("imageName").toString();
               
               of.setId((int)id);
               of.setUsername(username);
               of.setMessage(message);
               //of.setDescription(description);
               //of.setImage_name(imagename);
               
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

}
