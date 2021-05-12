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
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.mobilePIDEV.entites.Answer;
import com.mobilePIDEV.entites.Question;
import com.mobilePIDEV.entites.Quiz;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class ServiceReponse {

    public ArrayList<Answer> reponses;

    public static ServiceReponse instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceReponse() {
        req = new ConnectionRequest();
    }

    public static ServiceReponse getInstance() {
        if (instance == null) {
            instance = new ServiceReponse();
        }
        return instance;
    }

    public ArrayList<Answer> parseReponses(String jsonText) {
        try {
            reponses = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Answer a = new Answer();
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setReponse(obj.get("reponse").toString());
                if (obj.get("valid").toString().equals("true")) {
                    a.setValid(true);
                } else {
                    a.setValid(false);
                }
                if (obj.get("quest") != null) {
                    Map<String, Object> listq = (Map<String, Object>) obj.get("quest");
                    for (Object o : listq.values()) {
                        a.setQuestion(new Question((int) Float.parseFloat(listq.get("id").toString()), listq.get("body").toString(), 0, 0));
                        a.setQuest_id((int) Float.parseFloat(listq.get("id").toString()));
                    }
                }

                reponses.add(a);
            }
        } catch (IOException ex) {

        }
        return reponses;
    }

    public ArrayList<Answer> getAllReponses() {
        String url = Statics.BASE_URL + "ReponseJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reponses = parseReponses(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reponses;
    }

    public boolean DeleteReponse(Answer a) {
        String url = Statics.BASE_URL + "deleteReponseJSON/" + a.getId();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean addReponse(Answer a) {
        String url = Statics.BASE_URL + "addReponseJSON?reponse=" + a.getReponse() + "&valid=" + a.isValid() + "&quest_id=" + a.getQuest_id();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean EditReponse(Answer a) {
        String url = Statics.BASE_URL + "editReponseJSON/" + a.getId() + "?reponse=" + a.getReponse() + "&valid=" + a.isValid() + "&quest_id=" + a.getQuest_id();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
