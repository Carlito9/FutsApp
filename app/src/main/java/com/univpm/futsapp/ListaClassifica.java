package com.univpm.futsapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.NewGame.DataList;

class ListaClassifica extends RecyclerView.Adapter<ListaClassifica.ViewHolder> {
    private DataList[] classifica;
    private Context context;
    ListaClassifica(DataList[] classifica, Context context) {
        this.classifica=classifica;
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
    public void onBindViewHolder(@NonNull ListaClassifica.ViewHolder holder, int position) {
        final DataList lista=classifica[position];
        if(position==0)
            holder.medal.setBackgroundResource(R.drawable.gold);
        else if(position==1)
            holder.medal.setBackgroundResource(R.drawable.silver);
        else if(position==2)
            holder.medal.setBackgroundResource(R.drawable.bronzo);
        else
            holder.posiz.setText(String.valueOf(position+1));

        holder.username.setText(lista.getUsername());
        holder.punteggio.setText(String.valueOf(lista.getDati().get("vittorie")));
    }


    @Override
    public int getItemCount() {
        try{
        return classifica.length;}
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
