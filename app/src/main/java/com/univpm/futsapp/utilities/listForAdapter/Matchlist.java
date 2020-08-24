package com.univpm.futsapp.utilities.listForAdapter;

import android.content.Context;

import java.util.ArrayList;

public class Matchlist {
    private ArrayList<String> teams;
    private String data;
    private String orario;
    private String luogo;

    private String risultato;
    private Long[] golgioc;


    public Matchlist(ArrayList<String> teams, String data, String orario, String luogo, String risultato){
        this.teams=teams;
        this.data=data;
        this.orario=orario;
        this.luogo=luogo;
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

    public String getRisultato() {return risultato;}

    public void setRisultato(String risultato) {this.risultato = risultato;}

    public Long[] getGolgioc() {
        return golgioc;
    }

    public void setGolgioc(ArrayList<Long> golgioc) {this.golgioc= golgioc.toArray(new Long[0]);}

    public String getgolCasa(){return risultato.substring(0, risultato.indexOf("-")).trim(); }

    public String getgolOspite(){return risultato.substring(risultato.indexOf("-")+1).trim();}
}
