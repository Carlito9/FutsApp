package com.univpm.futsapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.univpm.futsapp.utilities.DataSave;

import java.util.ArrayList;

public class ListaInserisciRisultato extends RecyclerView.Adapter<ListaInserisciRisultato.ViewHolder>{
    private Matchlist[] listdata;
    private Dialog myDialog;

    public ListaInserisciRisultato(Matchlist[] listdata, Context context){
        this.listdata=listdata;
        myDialog=new Dialog(context);

    }
    @NonNull
    @Override
    public ListaInserisciRisultato.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_storico_item, parent, false);

        ListaInserisciRisultato.ViewHolder viewHolder = new ListaInserisciRisultato.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaInserisciRisultato.ViewHolder holder, final int position) {
        final Matchlist lista = listdata[position];
        myDialog.setContentView(R.layout.popup_inserisci);

        holder.capo.setText(String.format("organizzatore: %s", lista.getTeams().get(0)));
        holder.data.setText(String.valueOf(lista.getData()));
        holder.luogo.setText(String.valueOf(lista.getLuogo()));
        holder.orario.setText(String.valueOf(lista.getOrario()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtclose;
                Button regPartita;
                final TextView[] casa;
                final TextView[] trasferta;
                TextView ora;
                TextView data;
                TextView stadio;
                final EditText golCasa;
                final EditText golOspite;
                final EditText[] golA;
                final EditText[] golB;

                regPartita=(Button) myDialog.findViewById(R.id.regPartita);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                golCasa=(EditText) myDialog.findViewById(R.id.golCasa);
                golOspite = (EditText) myDialog.findViewById(R.id.golOspite);
                golA= (EditText[]) new EditText[] {myDialog.findViewById(R.id.golA0),myDialog.findViewById(R.id.golA1),myDialog.findViewById(R.id.golA2),myDialog.findViewById(R.id.golA3),myDialog.findViewById(R.id.golA4)};
                golB= (EditText[]) new EditText[] {myDialog.findViewById(R.id.golB0),myDialog.findViewById(R.id.golB1),myDialog.findViewById(R.id.golB2),myDialog.findViewById(R.id.golB3),myDialog.findViewById(R.id.golB4)};
                casa=(TextView[]) new TextView[]{myDialog.findViewById(R.id.playerA0), myDialog.findViewById(R.id.playerA1), myDialog.findViewById(R.id.playerA2), myDialog.findViewById(R.id.playerA3), myDialog.findViewById(R.id.playerA4)};
                trasferta=(TextView[]) new TextView[]{myDialog.findViewById(R.id.playerB0), myDialog.findViewById(R.id.playerB1), myDialog.findViewById(R.id.playerB2), myDialog.findViewById(R.id.playerB3), myDialog.findViewById(R.id.playerB4)};
                for (int i=0;i<5;i++){
                    casa[i].setText(lista.getTeams().get(i));
                    trasferta[i].setText(lista.getTeams().get(i+5));
                }
                ora= (TextView) myDialog.findViewById(R.id.oraPartita);
                data= (TextView) myDialog.findViewById(R.id.date);
                stadio= (TextView) myDialog.findViewById(R.id.stadio);

                ora.setText(lista.getOrario());
                data.setText(lista.getData());
                stadio.setText(lista.getLuogo());

                regPartita.setOnClickListener(new View.OnClickListener() {
                    int totale_casa;
                    int totale_ospiti;
                    Integer[] golgiocA=new Integer[5];
                    Integer[] golgiocB=new Integer[5];;
                    @Override
                    public void onClick(View v) {
                        try {
                            totale_casa = Integer.parseInt(golCasa.getText().toString());
                            totale_ospiti = Integer.parseInt(golOspite.getText().toString());
                            for (int i = 0; i < 5; i++) {
                                golgiocA[i] = Integer.parseInt(golA[i].getText().toString());
                                golgiocB[i] = Integer.parseInt(golB[i].getText().toString());
                            }
                            new DataSave().SaveMatch(totale_casa,totale_ospiti,golgiocA,golgiocB,lista.getTeams(),myDialog,lista.getRisultato());
                        }catch (NullPointerException | IllegalArgumentException e)
                        {
                            Toast.makeText(myDialog.getContext(), "Riempire tutti i campi per favore", Toast.LENGTH_SHORT).show();
                        }
                        //rimuoviElemento(listdata,position);
                        holder.cardView.setVisibility(View.GONE);
                    }
                });
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        try {
            return listdata.length;
        }
        catch (NullPointerException e){return 0;}
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView capo;
        TextView luogo;
        TextView data;
        TextView orario;
        CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.capo = (TextView) itemView.findViewById(R.id.capo);
            this.data = (TextView) itemView.findViewById(R.id.Data);
            this.luogo = (TextView) itemView.findViewById(R.id.Luogo);
            this.orario = (TextView) itemView.findViewById(R.id.Orario);
            this.cardView= (CardView) itemView.findViewById(R.id.card_view);
        }
    }

   /* private void rimuoviElemento(Matchlist[] listdata, int position) {
        for(int i=position+1; i<listdata.length;i++)
            swap(listdata[i],listdata[i-1]);
    }*/

    private void swap(Matchlist a, Matchlist b) {
        Matchlist temp;
        temp=a;
        a=b;
        b=temp;
    }


}


