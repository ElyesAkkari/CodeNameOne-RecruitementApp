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
public class Quiz {
        private int id, nbr_quest;
    private String titre, owner;
    private List questions;

    public Quiz(int id, int nbr_quest, String titre, String owner) {
        this.id = id;
        this.nbr_quest = nbr_quest;
        this.titre = titre;
        this.owner = owner;
    }

    public Quiz() {
    }

    public int getId() {
        return id;
    }

    public int getNbr_quest() {
        return nbr_quest;
    }

    public String getTitre() {
        return titre;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbr_quest(int nbr_quest) {
        this.nbr_quest = nbr_quest;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return this.titre;
    }

   /* public List getQuestions() {
        ServiceQuestion sc = new ServiceQuestion();
        return sc.finByQuizId(this.id);
    }*/
    
}
