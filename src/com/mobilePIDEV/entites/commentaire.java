/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

/**
 *
 * @author 21628
 */
public class commentaire {
      int id, idformation_id;
    String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdformation_id() {
        return idformation_id;
    }

    public void setIdformation_id(int idformation_id) {
        this.idformation_id = idformation_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public commentaire(int id, int idformation_id, String body) {
        this.id = id;
        this.idformation_id = idformation_id;
        this.body = body;
    }

    public commentaire(int idformation_id, String body) {
        this.idformation_id = idformation_id;
        this.body = body;
    }

    @Override
    public String toString() {
        return "commentaire{" + "id=" + id + ", idformation_id=" + idformation_id + ", body=" + body + '}';
    }

    public commentaire() {
    }
    
}
