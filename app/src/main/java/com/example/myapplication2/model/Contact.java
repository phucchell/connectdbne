package com.example.myapplication2.model;

public class Contact {
    private int id;
    private String contactName;


    public Contact() {

    }

    public Contact(int id, String contactName) {
        this.contactName = contactName;
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
