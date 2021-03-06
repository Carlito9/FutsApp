package com.univpm.futsapp.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.Main.NextEvent.ListaNextEvent;
import com.univpm.futsapp.R;


public class FragmentNextEvent extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_next_event,container,false);

        RecyclerView giocatori = (RecyclerView) view.findViewById(R.id.Notifiche);
        giocatori.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(view.getContext());
        RecyclerView.Adapter mAdapter = new ListaNextEvent(MainActivity.daFare,view.getContext());
        giocatori.setAdapter(mAdapter);
        giocatori.setLayoutManager(lManager);
        return view;
    }


}

