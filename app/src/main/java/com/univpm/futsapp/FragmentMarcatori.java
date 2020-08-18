package com.univpm.futsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.NewGame.DataList;


public class FragmentMarcatori extends androidx.fragment.app.Fragment {
    private Context con;
    private DataList[] marcatori;
    public FragmentMarcatori(Context con, DataList[] marcatori) {
        this.con=con;
        this.marcatori=marcatori;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marcatori,container,false);
        RecyclerView RecMarc = (RecyclerView) view.findViewById(R.id.marcatori);
        RecMarc.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(view.getContext());
        RecyclerView.Adapter mAdapter = new ListaMarcatori(marcatori,view.getContext());
        RecMarc.setAdapter(mAdapter);
        RecMarc.setLayoutManager(lManager);
        return view;
    }
}
