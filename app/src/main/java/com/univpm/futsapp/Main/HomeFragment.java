package com.univpm.futsapp.Main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.univpm.futsapp.Main.Home.Classifiche.Classifiche;
import com.univpm.futsapp.Main.Home.InserisciRisultato.InserisciRisultato;
import com.univpm.futsapp.Main.Home.NewGame.NewGameActivity;
import com.univpm.futsapp.Viewed;
import com.univpm.futsapp.trash.NextActivity;
import com.univpm.futsapp.R;
import com.univpm.futsapp.Main.Home.Storico.Storico;


public class HomeFragment extends Fragment {
    View view;
    Context con;
    Button InserisciRis;
    Navigator nav = new Navigator();
    boolean verifica=false;
    private Dialog myDialog;
    HomeFragment(Context con) {
        this.con= con;
        myDialog=new Dialog(this.con);}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        TextView golCasa;
        TextView golOspite;
        view.findViewById(R.id.bStorico).setOnClickListener(nav);
        view.findViewById(R.id.bClassifiche).setOnClickListener(nav);
        view.findViewById(R.id.bNuovaPartita).setOnClickListener(nav);
        view.findViewById(R.id.UltimoEvento).setOnClickListener(nav);
        InserisciRis=view.findViewById(R.id.bInserisci);

        try {
            golCasa=view.findViewById(R.id.golCasa);
            String golC = MainActivity.giocate[0].getgolCasa();
            golCasa.setText(golC);
            String golO = MainActivity.giocate[0].getgolOspite();
            golOspite = view.findViewById(R.id.golOspite);
            golOspite.setText(golO);
        }catch (NullPointerException | ArrayIndexOutOfBoundsException e)
        {
            view.findViewById(R.id.risultato).setVisibility(View.GONE);
            view.findViewById(R.id.ultima).setVisibility(View.GONE);
            view.findViewById(R.id.UltimoEvento).setBackgroundResource(R.drawable.campo_prima_volta);
        }

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
                InserisciRis.setBackgroundResource(R.drawable.button_inserisci);
                InserisciRis.setTextColor(getResources().getColor(R.color.white, null));
                a=true;
            }

            if (MainActivity.daRegistrare.length != 1)
                InserisciRis.setText(MainActivity.daRegistrare.length + " risultati da inserire");
            else
                InserisciRis.setText(MainActivity.daRegistrare.length + " risultato da inserire");
        }
        catch (NullPointerException e){
            InserisciRis.setText("0 risultati da inserire");
            return false;
        }
        return a;
    }


    private class Navigator implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bInserisci:
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
            case R.id.bClassifiche: {
                show("open social");
                openClassifica();
                break;}
            case R.id.bNuovaPartita: {
                show("creiamo una nuova partita");
                MainActivity.check=true;
                openNewGame();
                break;}
            case R.id.UltimoEvento:
                openLast();
                break;
        }

    }


    private void openNewGame() {
        Intent intent = new Intent(getView().getContext(), NewGameActivity.class);
        startActivity(intent);
    }
    public  void openLast(){
        myDialog.setContentView(R.layout.popup_vedi_risultato);
                TextView txtclose;
                TextView[] casa;
                TextView[] trasferta;
                TextView[] golA;
                TextView[] golB;
                TextView ora;
                TextView data;
                TextView stadio;
                TextView golCasa;
                TextView golOspiti;

                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                casa=(TextView[]) new TextView[]{myDialog.findViewById(R.id.playerA0), myDialog.findViewById(R.id.playerA1), myDialog.findViewById(R.id.playerA2), myDialog.findViewById(R.id.playerA3), myDialog.findViewById(R.id.playerA4)};
                trasferta=(TextView[]) new TextView[]{myDialog.findViewById(R.id.playerB0), myDialog.findViewById(R.id.playerB1), myDialog.findViewById(R.id.playerB2), myDialog.findViewById(R.id.playerB3), myDialog.findViewById(R.id.playerB4)};
                golA= (TextView[]) new TextView[] {myDialog.findViewById(R.id.golA0),myDialog.findViewById(R.id.golA1),myDialog.findViewById(R.id.golA2),myDialog.findViewById(R.id.golA3),myDialog.findViewById(R.id.golA4)};
                golB= (TextView[]) new TextView[] {myDialog.findViewById(R.id.golB0),myDialog.findViewById(R.id.golB1),myDialog.findViewById(R.id.golB2),myDialog.findViewById(R.id.golB3),myDialog.findViewById(R.id.golB4)};
                for (int i=0;i<5;i++){
                    casa[i].setText(MainActivity.giocate[MainActivity.giocate.length-1].getTeams().get(i));
                    golA[i].setText(String.valueOf((int)(long) MainActivity.giocate[MainActivity.giocate.length-1].getGolgioc()[i]));
                    trasferta[i].setText(MainActivity.giocate[MainActivity.giocate.length-1].getTeams().get(i+5));
                    golB[i].setText(String.valueOf((int)(long)MainActivity.giocate[MainActivity.giocate.length-1].getGolgioc()[i+5]));
                }
                ora= (TextView) myDialog.findViewById(R.id.oraPartita);
                data= (TextView) myDialog.findViewById(R.id.date);
                stadio= (TextView) myDialog.findViewById(R.id.stadio);
                golCasa= (TextView) myDialog.findViewById(R.id.golCasa);
                golOspiti= (TextView) myDialog.findViewById(R.id.golOspite);
                ora.setText(MainActivity.giocate[MainActivity.giocate.length-1].getOrario());
                data.setText(MainActivity.giocate[MainActivity.giocate.length-1].getData());
                stadio.setText(MainActivity.giocate[MainActivity.giocate.length-1].getLuogo());
                golCasa.setText(MainActivity.giocate[MainActivity.giocate.length-1].getgolCasa());

                golOspiti.setText(MainActivity.giocate[MainActivity.giocate.length-1].getgolOspite());

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
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
