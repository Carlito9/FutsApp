package com.univpm.futsapp;

import android.net.Uri;

public class Viewed {
    private String nome;
    private Uri uri;
    public Viewed (Uri uri, String nome)
    {
        this.uri=uri;
        this.nome=nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
