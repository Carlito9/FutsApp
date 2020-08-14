package com.univpm.futsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.NewGame.DataList;
import com.univpm.futsapp.NewGame.ListaGiocatori;

import java.util.Set;

public class ListaRubrica extends  RecyclerView.Adapter<ListaRubrica.ViewHolder>{
    private DataList[] listdata;
    Context context;


    // RecyclerView recyclerView;
    public ListaRubrica(DataList[] listdata,Context context) {
        this.listdata = listdata;
        this.context=context;
     }

    @NonNull
    @Override
    public ListaRubrica.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_rubrica_item, parent, false);
        ListaRubrica.ViewHolder viewHolder = new ListaRubrica.ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListaRubrica.ViewHolder holder, final int position) {
        final DataList lista = listdata[position];

        holder.username.setText(lista.getUsername());
        holder.rating.setText(String.valueOf(lista.getRating()));

        };



    @Override
    public int getItemCount() {
        return listdata.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rating;
        TextView username;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            this.rating = (TextView) itemView.findViewById(R.id.rating);
            this.username = (TextView) itemView.findViewById(R.id.uname);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);

        }


    }

}
