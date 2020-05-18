package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChoosePlayer extends AppCompatActivity {
    Set<String> chosen= new HashSet<>();
    int max=5;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        DataList[] players=NewGameActivity.players;
        RecyclerView giocatori = (RecyclerView) findViewById(R.id.fGiocatori);
        giocatori.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        Intent intent=getIntent();
        String[] scelti=intent.getExtras().getStringArray("scelti");
        aggiungi(scelti);

        RecyclerView.Adapter mAdapter = new ListaGiocatori(players,this,chosen);
        giocatori.setAdapter(mAdapter);
        giocatori.setLayoutManager(lManager);

         mAuth = FirebaseAuth.getInstance();

        System.out.println(scelti[0]);
        if(scelti[0].equals(mAuth.getCurrentUser().getDisplayName()))
            max=4;



    }

    public void next(View view) {

        if (chosen.contains(mAuth.getCurrentUser().getDisplayName()))
             chosen.remove(mAuth.getCurrentUser().getDisplayName());
        if(chosen.size()>max)
            Toast.makeText(view.getContext(), "Devi togliere "+(chosen.size()-max)+" giocatori", Toast.LENGTH_SHORT).show();
        else if(chosen.size()<max)
            Toast.makeText(view.getContext(), "Devi aggiungere "+(max-chosen.size())+" giocatori", Toast.LENGTH_SHORT).show();
        else
        {
            Intent intent=new Intent();
            String[] partecipanti= chosen.toArray(new String[0]);
            intent.putExtra("partecipanti",partecipanti);
            setResult(RESULT_OK, intent);
            finish();

        }
    }
    public void aggiungi(String[] scelti){
        for(String s:scelti) {
            if (!s.equals("0"))
                chosen.add(s);
        }
        for(String s:chosen)
            System.out.println(s);
    }
}
