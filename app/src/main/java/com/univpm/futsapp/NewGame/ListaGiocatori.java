package com.univpm.futsapp.NewGame;

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
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.gold));
         }
        else {
            //holder.relativeLayout.setBackgroundColor(Color.WHITE);
            holder.relativeLayout.setBackgroundResource(R.drawable.border);
        }
        holder.username.setText(lista.getUsername());
        holder.rating.setText(String.valueOf(lista.getRating()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chosen.contains(lista.getUsername())){
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.gold));
                    chosen.add(lista.getUsername());
                }
                else {
                    //holder.relativeLayout.setBackgroundColor(Color.WHITE);
                    holder.relativeLayout.setBackgroundResource(R.drawable.border);
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
