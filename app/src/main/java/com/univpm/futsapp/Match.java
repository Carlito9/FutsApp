package com.univpm.futsapp;

public class Match {
    private String name;
    private Integer costo;
    private String luogo;
    private String data;
    /*private  Integer ora*/;

    public Match( String name, Integer costo, String luogo, String data/*Integer ora*/)
        {
        this.name = name;
        this.costo = costo;
        this.luogo = luogo;
        this.data = data;
        /*this.ora = ora;*/ }

    public String getName() {
        return name;
    }

    public Integer getCosto() {
        return costo;
    }

    public String getLuogo() {
        return luogo;
    }

    public String getData() {
        return data;
    }



}
