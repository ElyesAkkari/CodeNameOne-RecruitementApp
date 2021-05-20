/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilePIDEV.entites;

import java.util.Date;

/**
 *
 * @author 21628
 */
public class participer {
    private int id;
    private Date date;
    private int formation_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFormation_id() {
        return formation_id;
    }

    public void setFormation_id(int formation_id) {
        this.formation_id = formation_id;
    }

    public participer(int id, Date date, int formation_id) {
        this.id = id;
        this.date = date;
        this.formation_id = formation_id;
    }

    public participer(Date date, int formation_id) {
        this.date = date;
        this.formation_id = formation_id;
    }

    @Override
    public String toString() {
        return "participer{" + "id=" + id + ", date=" + date + ", formation_id=" + formation_id + '}';
    }
    
    
            
}
