package com.univpm.futsapp.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.univpm.futsapp.Main.Home.Classifiche.Classifiche;
import com.univpm.futsapp.Main.Home.InserisciRisultato.InserisciRisultato;
import com.univpm.futsapp.Main.Home.NewGame.NewGameActivity;
import com.univpm.futsapp.trash.NextActivity;
import com.univpm.futsapp.R;
import com.univpm.futsapp.Main.Home.Storico.Storico;


public class HomeFragment extends Fragment {
    View view;
    Context con;
    Button InserisciRis;
    Navigator nav = new Navigator();
    boolean verifica=false;
    HomeFragment(Context con) {con= this.con;}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);


        view.findViewById(R.id.bStorico).setOnClickListener(nav);
        view.findViewById(R.id.bIdeeFuture).setOnClickListener(nav);
        view.findViewById(R.id.bNuovaPartita).setOnClickListener(nav);
        view.findViewById(R.id.bProssimoEvento).setOnClickListener(nav);
        InserisciRis=view.findViewById(R.id.bGruppi);
        verifica=CheckMatch(nav);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        verifica=CheckMatch(nav);
    }

    private boolean CheckMatch(Navigator nav) {
        boolean a=false;
        try {
            if (MainActivity.daRegistrare.length != 0)
            {
                InserisciRis.setOnClickListener(nav);
                a=true;
            }

            if (MainActivity.daRegistrare.length != 1)
                InserisciRis.setText("Hai " + MainActivity.daRegistrare.length + " risultati da inserire");
            else
                InserisciRis.setText("Hai " + MainActivity.daRegistrare.length + " risultato da inserire");
        }
        catch (NullPointerException e){
            InserisciRis.setText("Hai 0 risultati da inserire");
            return false;
        }
        return a;
    }


    private class Navigator implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGruppi:
                if(verifica)
                {
                    MainActivity.check=true;
                    show("visualizzo i gruppi");
                    openInsert();
                }
                break;
            case R.id.bStorico: {
                show("visualizzo lo storico");
                openHistory();
                break;}
            case R.id.bIdeeFuture: {
                show("open social");
                openClassifica();
                break;}
            case R.id.bNuovaPartita: {
                show("creiamo una nuova partita");
                MainActivity.check=true;
                openNewGame();
                break;}
            case R.id.bProssimoEvento:
                show("il prossimo evento è...");
                openNext();
                break;
        }

    }


    private void openNewGame() {
        Intent intent = new Intent(getView().getContext(), NewGameActivity.class);
        startActivity(intent);
    }
    public  void openNext(){
        Intent intent = new Intent(getView().getContext(), NextActivity.class);
        startActivity(intent);
    }

    void show(String s) {

        Toast.makeText(getView().getContext(), s, Toast.LENGTH_LONG).show();
    }

}

    private void openClassifica() {
        Intent intent = new Intent(getView().getContext(), Classifiche.class);
        startActivity(intent);
    }

    private void openInsert() {
            Intent intent = new Intent(getView().getContext(), InserisciRisultato.class);
            startActivity(intent);
    }

    private void openHistory() {
        Intent intent = new Intent(getView().getContext(), Storico.class);
        startActivity(intent);
    }


}
