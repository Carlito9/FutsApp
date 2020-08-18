package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Storico extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        RecyclerView storico = (RecyclerView) findViewById(R.id.Storico);
        storico.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new ListaStorico(MainActivity.giocate,this);
        storico.setAdapter(mAdapter);
        storico.setLayoutManager(lManager);
    }
}
