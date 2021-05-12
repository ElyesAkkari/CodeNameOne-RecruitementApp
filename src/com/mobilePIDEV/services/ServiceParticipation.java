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
import com.codename1.l10n.DateFormat;
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
public class ServiceParticipation {

    public ArrayList<Participation> participations;

    public static ServiceParticipation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceParticipation() {
        req = new ConnectionRequest();
    }

    public static ServiceParticipation getInstance() {
        if (instance == null) {
            instance = new ServiceParticipation();
        }
        return instance;
    }

    public ArrayList<Participation> getAllParts() {
        String url = Statics.BASE_URL + "participationJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                participations = parseParts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return participations;
    }

    public ArrayList<Participation> parseParts(String jsonText) {
        try {
            participations = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Participation p = new Participation();
                float id = Float.parseFloat(obj.get("id").toString());
                p.setId((int) id);
                p.setNote(((int) Float.parseFloat(obj.get("note").toString())));
                if(obj.get("quiz") != null){
                Map<String, Object> listq = (Map<String, Object>) obj.get("quiz");
                for (Object o : listq.values())
                {
                 p.setQuiz(new Quiz((int) Float.parseFloat(listq.get("id").toString()), 0, listq.get("titre").toString(), listq.get("owner").toString()));
                 p.setQuiz_id((int) Float.parseFloat(listq.get("id").toString()));
                }
                }
                //System.out.println(listq);
                String sDate1 = obj.get("added").toString();
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                    p.setAdded(date1);
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                if ((obj.get("archive").toString())=="false") 
                    p.setArchive(false);
                 else     
                {   p.setArchive(true);}
                participations.add(p);
            }
        } catch (IOException ex) {

        }
        return participations;
    }
}
