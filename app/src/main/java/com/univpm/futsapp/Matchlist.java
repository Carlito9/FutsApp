package com.univpm.futsapp;

import android.content.Context;

import java.util.ArrayList;

public class Matchlist {
    private ArrayList<String> teams;
    private String data;
    private String orario;
    private String luogo;
    private int costo;
    private String risultato;

    public Matchlist(ArrayList<String> teams, String data, String orario, String luogo, int costo, String risultato){
        this.teams=teams;
        this.data=data;
        this.orario=orario;
        this.luogo=luogo;
        this.costo=costo;
        this.risultato=risultato;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public ArrayList<String> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<String> teams) {
        this.teams = teams;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getCosto() {return costo;}

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getRisultato() {return risultato;}

    public void setRisultato(String risultato) {this.risultato = risultato;}
}
