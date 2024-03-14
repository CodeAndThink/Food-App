package com.truong.foodapplication.data.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PurchaseItem {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public PurchaseItem(String userName, List<Item> items, Date date) {
        this.userName = userName;
        this.items = items;
        this.date = date;
    }

    public PurchaseItem(String userName) {
        this.userName = userName;
    }

    private String userName;
    private List<Item> items;
    private Date date;
}
