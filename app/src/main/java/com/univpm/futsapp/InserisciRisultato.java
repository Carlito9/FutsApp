package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class InserisciRisultato extends AppCompatActivity {
    RecyclerView.LayoutManager lManager;
    RecyclerView insRis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci_risultato);
        insRis = (RecyclerView) findViewById(R.id.InserisciRisultato);
        insRis.setHasFixedSize(true);
        CreaFragment();
    }


    private void CreaFragment() {
        lManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new ListaInserisciRisultato(MainActivity.daRegistrare,this);
        insRis.setAdapter(mAdapter);
        insRis.setLayoutManager(lManager);
    }
}
