package com.univpm.futsapp.utilities.listForAdapter;

import java.util.ArrayList;
import java.util.Map;

public class DataList {
    private String username;
    private Map<String, Object> dati;
    public DataList(String username, Map<String, Object> dati) {
        this.username = username;

        this.dati = dati;
    }

    public DataList(String username)
    {
        this.username = username;

    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Object> getDati() {return dati;}
    public void setDati(Map<String, Object> dati) {this.dati = dati;}
    public void setAmici(ArrayList<String> nomi) {
        Map<String,Object> m=getDati();
        m.remove("amici");
        m.put("amici", nomi);
    }
}
