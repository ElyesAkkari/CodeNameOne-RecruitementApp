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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.EventForm;
import com.mobilePIDEV.entites.Event;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author hazem
 */
public class ServiceEvent {
    //singleton
    public ArrayList<Event> tasks;
    public static ServiceEvent instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    //----------------
    public ServiceEvent() {
         req = new ConnectionRequest();
    }
    public static ServiceEvent getInstance() {
        if (instance == null) {
            instance = new ServiceEvent();
        }
        return instance;
    }

    public boolean addEvent(Event t) {
        //String url = Statics.BASE_URL +"/new/"+ t.getNom() + "/" + t.getDescription()+ "/" + t.getAdresse()+ "/" + t.getDomaine(); //création de l'URL
        String url = Statics.BASE_URL+"json/ajouterEvent?nom="+t.getNom()+"&type="+t.getType()+"&description="+t.getDescription()+"&image="+t.getImage();
       
        
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Event> parseEvents(String jsonText) {
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

 
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
           
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Event t = new Event();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                String sDate1 = obj.get("date").toString();
                System.out.println(sDate1);
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                    t.setDate_inscription(date1);
                 
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
               
              //  t.setDate_inscription(obj.get("dateInscription").toString());
               // t.setFin_inscription(obj.get("finInscription").toString());
                t.setNom(obj.get("nom").toString());
                t.setType(obj.get("type").toString());
                t.setDescription(obj.get("description").toString());
                t.setImage(obj.get("image").toString());
                float prix=Float.parseFloat(obj.get("prix").toString());
                t.setPrix((int)prix);
                //Ajouter la tâche extraite de la réponse Json à la liste
               
          //Map<String, Object> date2  = (Map<String, Object>) obj.get("dateInscription");
                 //float da = Float.parseFloat(date2.get("timestamp").toString());
                 //Date d = new Date((long)(da-3600 )*1000);
                  //Map<String, Object> date3  = (Map<String, Object>) obj.get("finInscription");
                 //float da2 = Float.parseFloat(date3.get("timestamp").toString());
                 //Date d2 = new Date((long)(da2-3600)*1000);
                 //System.out.println(d);
                 
                 
                 
                 //t.setDate_inscription(d);
              
                //t.setFin_inscription(d2);
                
                
                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return tasks;
    }
//------------------------------------------------------------
    
    public ArrayList<Event>                getAllEvents(){
        String url = Statics.BASE_URL+"json/afficherEvents";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    
    public boolean deleteEvent(Event e) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://127.0.0.1:8000/json/supprimerEvent/" + e.getId();
        req.setUrl(Url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK=req.getResponseCode()==200;
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }
    
    public boolean editEvent(Event e){
        String url=Statics.BASE_URL+"json/modifierEvent/"+e.getId()
                +"?nom="+e.getNom()
                +"&type="+e.getType()
                +"&description="+e.getDescription();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 resultOK=req.getResponseCode()==200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Event> getEvent(Event e){
        String url = Statics.BASE_URL+"json/afficherEvent/"+e.getId();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
    
    public boolean addParticipation(Participation p) {
        //String url = Statics.BASE_URL +"/new/"+ t.getNom() + "/" + t.getDescription()+ "/" + t.getAdresse()+ "/" + t.getDomaine(); //création de l'URL
        String url = Statics.BASE_URL+"json/ajouterP";
       
        
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
