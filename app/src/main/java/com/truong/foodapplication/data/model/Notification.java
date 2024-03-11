package com.truong.foodapplication.data.model;

import java.sql.Timestamp;
import java.util.Date;

public class Notification {
    public Notification(String name, String context, Date date, String imageUrl) {
        this.name = name;
        this.context = context;
        this.date = date;
        this.imageUrl = imageUrl;
    }
    public Notification(){

    }

    private String name;
    private String context;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Date date;
    private String imageUrl;
}
