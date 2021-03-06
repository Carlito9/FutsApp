package com.univpm.futsapp.Main.Home.NewGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.R;
import com.univpm.futsapp.utilities.listForAdapter.DataList;

public class ListaDoppiaGiocatori extends RecyclerView.Adapter<ListaGiocatori.ViewHolder> {
        private DataList[] listdata=new DataList[5];
        Context context;
        String[] squadra;
        int layout;


        // RecyclerView recyclerView;
        public ListaDoppiaGiocatori(DataList[] listdata,Context context, String[] squadra, int layout) {
            this.context=context;
            this.squadra=squadra;
            this.layout=layout;
            int i=0;
            for (String s:squadra){
                if(s.equals("0"))
                {
                    this.listdata[i++]=new DataList("aggiungi");
                    continue;
                }
                for (DataList a:listdata){
                    if(s.equals(a.getUsername()))
                        this.listdata[i++]=a;
                }
                if(i==5)
                    break;
            }

        }

        @NonNull
        @Override
        public ListaGiocatori.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(layout, parent, false);
            ListaGiocatori.ViewHolder viewHolder = new ListaGiocatori.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListaGiocatori.ViewHolder holder, final int position) {
            final DataList lista = listdata[position];
            holder.username.setText(lista.getUsername());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ChoosePlayer.class);
                    intent.putExtra("scelti",squadra);
                    ((Activity)context).startActivityForResult(intent,103);


                }
            });

        }


        @Override
        public int getItemCount() {
            return listdata.length;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView username;
            public RelativeLayout relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                this.username = (TextView) itemView.findViewById(R.id.uname);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);

            }


        }

    }

