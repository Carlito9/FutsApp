package com.univpm.futsapp.Main.Home.NewGame;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.univpm.futsapp.R;
import com.univpm.futsapp.utilities.listForAdapter.DataList;

import java.util.Set;


public class ListaGiocatori extends RecyclerView.Adapter<ListaGiocatori.ViewHolder> {
    private DataList[] listdata;
    Context context;

    Set<String> chosen;

    // RecyclerView recyclerView;
    public ListaGiocatori(DataList[] listdata,Context context, Set<String> chosen) {
        this.listdata = listdata;
        this.context=context;
        this.chosen=chosen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fragment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DataList lista = listdata[position];
        if (chosen.contains(lista.getUsername())){
            holder.relativeLayout.setBackgroundResource(R.drawable.border_choose_clicked);
         }
        else {
            holder.relativeLayout.setBackgroundResource(R.drawable.border_choose_default);
        }
        holder.username.setText(lista.getUsername());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chosen.contains(lista.getUsername())){
                    holder.relativeLayout.setBackgroundResource(R.drawable.border_choose_clicked);
                    chosen.add(lista.getUsername());
                }
                else {
                    holder.relativeLayout.setBackgroundResource(R.drawable.border_choose_default);
                    chosen.remove(lista.getUsername());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);

            this.username = (TextView) itemView.findViewById(R.id.uname);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);

        }


    }

}
