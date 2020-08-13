package com.univpm.futsapp.NewGame;

import java.util.Map;

public class DataList {
    private String username;
    private int rating;
    private Map<String, Object> dati;

    public DataList(String username, int rating, Map<String, Object> dati) {
        this.username = username;
        this.rating = rating;
        this.dati = dati;
    }
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
    public Map<String, Object> getDati() {return dati;}
    public void setDati(Map<String, Object> dati) {this.dati = dati;}
}
