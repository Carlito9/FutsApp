package com.univpm.futsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {
    View view;
    Context con;
    HomeFragment(Context con) {con= this.con;}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        Navigator nav = new Navigator();

        view.findViewById(R.id.bGruppi).setOnClickListener(nav);
        view.findViewById(R.id.bStorico).setOnClickListener(nav);
        view.findViewById(R.id.bIdeeFuture).setOnClickListener(nav);
        view.findViewById(R.id.bNuovaPartita).setOnClickListener(nav);
        view.findViewById(R.id.bProssimoEvento).setOnClickListener(nav);
        return view;
    }


private class Navigator implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGruppi:
                show("visualizzo i gruppi");
                break;
            case R.id.bStorico:
                show("visualizzo lo storico");
                break;
            case R.id.bIdeeFuture:
                show("open social");
                break;
            case R.id.bNuovaPartita:
                show("creiamo una nuova partita");
                openNewGame();
                break;
            case R.id.bProssimoEvento:
                show("il prossimo evento è...");
                break;
            default:
                show("non dovrebbe succedere");
        }

    }

    public void openNewGame() {
        Intent intent = new Intent(getView().getContext(), NewGameActivity.class);
        startActivity(intent);
    }

    void show(String s) {

        Toast.makeText(getView().getContext(), s, Toast.LENGTH_LONG).show();
    }

}
}