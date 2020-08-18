package com.univpm.futsapp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.NewGame.DataList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


class ListaMarcatori extends RecyclerView.Adapter<ListaMarcatori.ViewHolder> {
    private DataList[] marcatori;
    private Context context;
    ListaMarcatori(DataList[] marcatori, Context context) {
        this.marcatori=marcatori;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_classifica_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaMarcatori.ViewHolder holder, int position) {
        final DataList lista=marcatori[position];
        if(position==0)
            holder.medal.setBackgroundResource(R.drawable.gold);
        else if(position==1)
            holder.medal.setBackgroundResource(R.drawable.silver);
        else if(position==2)
            holder.medal.setBackgroundResource(R.drawable.bronzo);
        else
            holder.posiz.setText(String.valueOf(position+1));

        holder.punteggio.setText(String.valueOf(lista.getDati().get("gol fatti")));
        holder.username.setText(lista.getUsername());
    }


    @Override
    public int getItemCount() {
        try{
            return marcatori.length;}
        catch (NullPointerException e){return 0;}
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView posiz;
        TextView username;
        LinearLayout medal;
        TextView punteggio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posiz = (TextView) itemView.findViewById(R.id.position);
            username = (TextView) itemView.findViewById(R.id.username);
            medal =itemView.findViewById(R.id.medal_badge);
            punteggio=itemView.findViewById(R.id.punteggio);
        }
    }
}
