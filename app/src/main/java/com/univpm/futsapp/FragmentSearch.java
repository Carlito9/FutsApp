package com.univpm.futsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.NewGame.NewGameActivity;

public class FragmentSearch extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        RecyclerView giocatori = (RecyclerView) view.findViewById(R.id.Rubrica);
        giocatori.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(view.getContext());
        RecyclerView.Adapter mAdapter = new ListaRubrica(NewGameActivity.amici,view.getContext());
        giocatori.setAdapter(mAdapter);
        giocatori.setLayoutManager(lManager);
        return view;
        }

}
