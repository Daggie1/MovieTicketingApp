package com.franktan.popularmovies.model;


public class TicketsModel {
    public String id, qrpath,qrname,userThe;

    public TicketsModel() {
    }


    public TicketsModel(String id, String qrpath, String qrname, String userThe) {
        this.id = id;
        this.qrpath = qrpath;
        this.qrname = qrname;
        this.userThe = userThe;
    }

    public String getId() {
        return id;
    }

    public String getQrpath() {
        return qrpath;
    }

    public String getQrname() {
        return qrname;
    }

    public String getUserThe() {
        return userThe;
    }
}

