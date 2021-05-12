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
import com.codename1.ui.events.ActionListener;
import com.mobilePIDEV.entites.Participation;
import com.mobilePIDEV.entites.Quiz;
import com.mobilePIDEV.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class ServiceQuiz {

    public ArrayList<Quiz> quizs;

    public static ServiceQuiz instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceQuiz() {
        req = new ConnectionRequest();
    }

    public static ServiceQuiz getInstance() {
        if (instance == null) {
            instance = new ServiceQuiz();
        }
        return instance;
    }


    public ArrayList<Quiz> parseQuizs(String jsonText) {
        try {
            quizs = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Quiz q = new Quiz();
                float id = Float.parseFloat(obj.get("id").toString());
                q.setId((int) id);
                q.setTitre(obj.get("titre").toString());
                q.setOwner(obj.get("owner").toString());
                q.setNbr_quest((int) Float.parseFloat(obj.get("nbr_quest").toString()));
                quizs.add(q);
            }
        } catch (IOException ex) {

        }
        return quizs;
    }

    public ArrayList<Quiz> getAllQuizs() {
        String url = Statics.BASE_URL + "quizJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                quizs = parseQuizs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return quizs;
    }

    public boolean addQuiz(Quiz q) {
        String url = Statics.BASE_URL + "addQuizJSON?titre=" + q.getTitre() + "&owner=" + q.getOwner();
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

    public boolean DeleteQuiz(Quiz q) {
        String url = Statics.BASE_URL + "deleteQuizJSON/" + q.getId();
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

    public boolean EditQuiz(Quiz q) {
        String url = Statics.BASE_URL + "editQuizJSON/" + q.getId() 
                + "?titre=" + q.getTitre()
                + "&owner=" + q.getOwner();
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
