package com.univpm.futsapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.univpm.futsapp.NewGame.DataList;
import com.univpm.futsapp.NewGame.NewGameActivity;

import java.util.ArrayList;

public class FragmentSearch extends Fragment {
    ListView lv;
    View view;
    FirebaseListAdapter adapter;
    ImageButton searchbutton;
    EditText searchtext;

    RecyclerView giocatori;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);

        giocatori = (RecyclerView) view.findViewById(R.id.Rubrica);
        Crearecycle (MainActivity.players);
        searchbutton = view.findViewById(R.id.searchbutton);
        searchtext = view.findViewById(R.id.searchtext);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                   Filtrarisultati( searchtext.getText().toString().trim());
                } catch (NullPointerException|IllegalArgumentException e ){
                    Crearecycle(MainActivity.players); 
                }

            }
        });
        return view;
        }

    private void Crearecycle(DataList[] players) {
        giocatori.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(view.getContext());
        RecyclerView.Adapter mAdapter = new ListaRubrica(players,view.getContext());
        giocatori.setAdapter(mAdapter);
        giocatori.setLayoutManager(lManager);
    }

    private void Filtrarisultati(String nome) {
        ArrayList <DataList> giocatori = new ArrayList<>();
        for (DataList d : MainActivity.players) {
            if (d.getUsername().contains(nome))
                giocatori.add(d);
        }
         Crearecycle(giocatori.toArray(new DataList[0]) );
    }


}
