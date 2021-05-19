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
import com.mobilePIDEV.entites.commentaire;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 21628
 */
public class servicecommentaire {
        public boolean resultOK;
    public ArrayList<commentaire> commentaires ;
  
        public static servicecommentaire instance=null;
        
        public servicecommentaire() {
    }
        
            public static servicecommentaire getInstance() {
        if (instance == null) {
            instance = new servicecommentaire();
        }
        return instance;
    }
                //ADD : Insert
     public boolean addCommentaire(commentaire c,int idformation_id){
         String url = Statics.BASE_URL+"addComment"+ "?id="+idformation_id+"body="+c.getBody();
                 ConnectionRequest req1 = new ConnectionRequest(url);
                 req1.setPost(false);
                  req1.addResponseListener((evt)->{
             
                  resultOK= req1.getResponseCode() == 200; // code http 200 OK
              
        });
        NetworkManager.getInstance().addToQueueAndWait(req1);
         return resultOK;
     }
          //Parsing     
    public ArrayList<commentaire> parseJsonAction(String jsonText){
        try {
            commentaires=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> productsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
 
            List<Map<String,Object>> list = (List<Map<String,Object>>) productsListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
          
          commentaire c = new commentaire();
                float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);
                c.setBody(obj.get("contenu").toString());
              
                //Ajouter la tâche extraite de la réponse Json à la liste
                commentaires.add(c);
            }
            
            
        } catch (IOException ex) {
            
        }
        return commentaires;
   
        }
    
    
    
    public ArrayList<commentaire> getAllCommentsAction(int idformation_id){
   //      ArrayList<Product> result = new  ArrayList<> ;
        String url = Statics.BASE_URL+"displayComments"+"?id="+idformation_id;
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                commentaires = parseJsonAction(new String(req.getResponseData())); // Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commentaires;
    }
    
}
