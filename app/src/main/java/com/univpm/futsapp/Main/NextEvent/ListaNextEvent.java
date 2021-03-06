package com.univpm.futsapp.Main.NextEvent;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.utilities.listForAdapter.Matchlist;
import com.univpm.futsapp.R;

import java.io.IOException;


public class ListaNextEvent extends RecyclerView.Adapter<ListaNextEvent.ViewHolder> {
    private Matchlist[] listdata;
    private Dialog myDialog;

    public ListaNextEvent(Matchlist[] listdata, Context context){
        this.listdata=listdata;
        myDialog=new Dialog(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_partita_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Matchlist lista = listdata[position];
        myDialog.setContentView(R.layout.popup_partite);
        try {
            MainActivity.LoadImage.LoadImage(lista.getTeams().get(0),holder.capo); } catch (IOException e) {
            e.printStackTrace();
        }
        holder.data.setText(String.valueOf(lista.getData()));
        holder.luogo.setText(String.valueOf(lista.getLuogo()));
        holder.orario.setText(String.valueOf(lista.getOrario()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtclose;
                TextView[] casa;
                TextView[] trasferta;
                TextView ora;
                TextView data;
                TextView stadio;
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
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
        ImageView capo;
        TextView luogo;
        TextView data;
        TextView orario;
        LinearLayout cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.capo =  (ImageView) itemView.findViewById(R.id.capo);
            this.data = (TextView) itemView.findViewById(R.id.Data);
            this.luogo = (TextView) itemView.findViewById(R.id.Luogo);
            this.orario = (TextView) itemView.findViewById(R.id.Orario);
            this.cardView= (LinearLayout) itemView.findViewById(R.id.card_view);
        }
    }
}
