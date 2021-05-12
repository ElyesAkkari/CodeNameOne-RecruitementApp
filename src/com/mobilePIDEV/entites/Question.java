/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

import java.util.List;

/**
 *
 * @author DELL
 */
public class Question {
        private int id;
    private String body;
    private int nbr_rep;
    private int quiz_id;
    private Quiz quiz;
    private List ansewers;
 /*   ServiceQuiz sq = new ServiceQuiz();
    ServiceAnswer sa = new ServiceAnswer();
*/

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Question() {
    }

    public Question(int id, String body, int nbr_rep, int quiz_id) {
        this.id = id;
        this.body = body;
        this.nbr_rep = nbr_rep;
        this.quiz_id = quiz_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getNbr_rep() {
        return nbr_rep;
    }

    public void setNbr_rep(int nbr_rep) {
        this.nbr_rep = nbr_rep;
    }

    @Override
    public String toString() {
        return this.body;
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
/*
    public List getAnsewers() {
        return sa.FindByQuestId(this.id);
    }*/
    
}
