/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

/**
 *
 * @author DELL
 */
public class Answer {
      private int id;
    private String reponse;
    private boolean valid;
    private int quest_id;
    private Question question;
   // ServiceQuestion sq =  new ServiceQuestion();

    public Answer() {
    }

    public Answer(int id, String reponse, boolean valid, int quest_id) {
        this.id = id;
        this.reponse = reponse;
        this.valid = valid;
        this.quest_id = quest_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    
    
    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", reponse=" + reponse + ", valid=" + valid + ", quest_id=" + quest_id + '}';
    }
    
}
