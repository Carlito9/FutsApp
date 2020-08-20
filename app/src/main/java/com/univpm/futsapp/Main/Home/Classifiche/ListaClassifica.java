package com.univpm.futsapp.Main.Home.Classifiche;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.R;
import com.univpm.futsapp.utilities.data.DataSave;
import com.univpm.futsapp.utilities.listForAdapter.DataList;

import java.io.IOException;
import java.util.ArrayList;

class ListaClassifica extends RecyclerView.Adapter<ListaClassifica.ViewHolder> {
    private DataList[] classifica;
    private Context context;
    private Dialog myDialog;
    ListaClassifica(DataList[] classifica, Context context) {
        this.classifica=classifica;
        this.context=context;
        myDialog=new Dialog(context);
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
        myDialog.setContentView(R.layout.popup_vedi_giocatore);
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
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtclose=myDialog.findViewById(R.id.txtclose);
                TextView username=myDialog.findViewById(R.id.username);
                TextView giocate=myDialog.findViewById(R.id.giocate);
                TextView vinte=myDialog.findViewById(R.id.vinte);
                TextView pareggiate=myDialog.findViewById(R.id.pareggiate);
                TextView perse=myDialog.findViewById(R.id.perse);
                TextView gol=myDialog.findViewById(R.id.gol);
                ImageView imageView=myDialog.findViewById(R.id.profilePic);

                try {MainActivity.LoadImage.LoadImage(lista.getUsername(),imageView); } catch (IOException e) {
                    e.printStackTrace();
                }
                username.setText(lista.getUsername());
                giocate.setText(String.valueOf(lista.getDati().get("partite giocate")));
                vinte.setText(String.valueOf(lista.getDati().get("vittorie")));
                pareggiate.setText(String.valueOf(lista.getDati().get("pareggi")));
                perse.setText(String.valueOf(lista.getDati().get("sconfitte")));
                gol.setText(String.valueOf(lista.getDati().get("gol fatti")));
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
        try{
        return classifica.length;}
        catch (NullPointerException e){return 0;}
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView posiz;
        TextView username;
        LinearLayout medal;
        LinearLayout item;
        TextView punteggio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posiz = (TextView) itemView.findViewById(R.id.position);
            username = (TextView) itemView.findViewById(R.id.username);
            medal =itemView.findViewById(R.id.medal_badge);
            item= itemView.findViewById(R.id.linearLayout);
            punteggio=itemView.findViewById(R.id.punteggio);
        }
    }
}
