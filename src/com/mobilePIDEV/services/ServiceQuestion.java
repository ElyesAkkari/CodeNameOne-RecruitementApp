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
public class ServiceQuestion {

    public ArrayList<Question> questions;

    public static ServiceQuestion instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceQuestion() {
        req = new ConnectionRequest();
    }

    public static ServiceQuestion getInstance() {
        if (instance == null) {
            instance = new ServiceQuestion();
        }
        return instance;
    }

    public ArrayList<Question> parseQuestions(String jsonText) {
        try {
            questions = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Question q = new Question();
                float id = Float.parseFloat(obj.get("id").toString());
                q.setId((int) id);
                q.setBody(obj.get("body").toString());
                q.setNbr_rep((int) Float.parseFloat(obj.get("nbr_rep").toString()));
                if (obj.get("quiz") != null) {
                    Map<String, Object> listq = (Map<String, Object>) obj.get("quiz");
                    for (Object o : listq.values()) {
                        q.setQuiz(new Quiz((int) Float.parseFloat(listq.get("id").toString()), 0, listq.get("titre").toString(), listq.get("owner").toString()));
                        q.setQuiz_id((int) Float.parseFloat(listq.get("id").toString()));
                    }
                }
                questions.add(q);
            }
        } catch (IOException ex) {

        }
        return questions;
    }

    public ArrayList<Question> getAllQuestions() {
        String url = Statics.BASE_URL + "QuestionsJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                questions = parseQuestions(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return questions;
    }

    public boolean DeleteQuestion(Question q) {
        String url = Statics.BASE_URL + "deleteQuestionJSON/" + q.getId();
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

    public boolean addQuestion(Question q) {
        String url = Statics.BASE_URL + "addQuestionJSON?body=" + q.getBody() + "&quiz_id=" + q.getQuiz_id();
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

    public boolean EditQuestion(Question q) {
        String url = Statics.BASE_URL + "editQuestionJSON/" + q.getId() + "?body=" + q.getBody() + "&quiz_id=" + q.getQuiz_id();
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
