package com.univpm.futsapp;

public class DataList {
    private String username;
    private int rating;
    public DataList(String username, int rating) {
        this.username = username;
        this.rating = rating;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
