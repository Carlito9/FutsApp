package com.univpm.futsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.NewGame.DataList;
import com.univpm.futsapp.NewGame.NewGameActivity;

import java.util.ArrayList;

public class FragmentClassifica extends Fragment {
    private Context con;
    private DataList[] amici;
    public FragmentClassifica(Context con, DataList[] amici) {this.con=con;
    this.amici=amici;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classifica,container,false);
        RecyclerView classifica = (RecyclerView) view.findViewById(R.id.classifica);
        classifica.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(view.getContext());
        RecyclerView.Adapter mAdapter = new ListaClassifica(amici,view.getContext());
        classifica.setAdapter(mAdapter);
        classifica.setLayoutManager(lManager);
        return view;
    }




}
