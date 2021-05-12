/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class Participation {
    private int id;
    private int user_id;
    private int quiz_id;
    private double note;
    private Date added;
    private boolean archive;
    private int offre_id;
    private Quiz quiz;
//    private Utilisateur utilisateur;
//    private offre offre;
        public Participation() {
    }

    public Participation(int id, int user_id, int quiz_id, double note, Date added, boolean archive, int offre_id) {
        this.id = id;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.note = note;
        this.added = added;
        this.archive = archive;
        this.offre_id = offre_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public int getOffre_id() {
        return offre_id;
    }

    public void setOffre_id(int offre_id) {
        this.offre_id = offre_id;
    }

    public Quiz getQuiz() {
        return this.quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
/*
    public Utilisateur getUtilisateur() {
        return su.findById(user_id);
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public offre getOffre() {
        return so.findById(offre_id);
    }

    public void setOffre(offre offre) {
        this.offre = offre;
    }*/
    
    
    
    
    @Override
    public String toString() {
        return "Participation{" + "id=" + id + ", user_id=" + user_id + ", quiz_id=" + quiz_id + ", note=" + note + ", added=" + added + ", archive=" + archive + ", offre_id=" + offre_id + '}';
    }
    
    
    
}
