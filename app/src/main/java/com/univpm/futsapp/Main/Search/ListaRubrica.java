package com.univpm.futsapp.Main.Search;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class ListaRubrica extends  RecyclerView.Adapter<ListaRubrica.ViewHolder>{
    private DataList[] listdata;
    Context context;
    private Dialog myDialog;


    // RecyclerView recyclerView;
    public ListaRubrica(DataList[] listdata,Context context) {
        this.listdata = listdata;
        this.context=context;
        myDialog=new Dialog(context);
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
        myDialog.setContentView(R.layout.popup_giocatore);

        holder.username.setText(lista.getUsername());
        holder.rating.setText(String.valueOf(lista.getRating()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean check;
                TextView txtclose=myDialog.findViewById(R.id.txtclose);
                TextView username=myDialog.findViewById(R.id.username);
                TextView giocate=myDialog.findViewById(R.id.giocate);
                TextView vinte=myDialog.findViewById(R.id.vinte);
                TextView pareggiate=myDialog.findViewById(R.id.pareggiate);
                TextView perse=myDialog.findViewById(R.id.perse);
                TextView gol=myDialog.findViewById(R.id.gol);
                final Switch amici=myDialog.findViewById(R.id.amici);
                ImageView imageView=myDialog.findViewById(R.id.profilePic);
                try {
                    MainActivity.LoadImage.LoadImage(lista.getUsername(),imageView); } catch (IOException e) {
                    e.printStackTrace();
                }
                final ArrayList<String> nomi=(ArrayList<String>)lista.getDati().get("amici");
                if(nomi.contains(MainActivity.username)) {
                    amici.setChecked(true);
                    check=true;
                }
                else {
                    amici.setChecked(false);
                    check=false;
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
                        if(amici.isChecked() && !check)
                        {
                            nomi.add(MainActivity.username);
                            lista.setAmici(nomi);
                            new DataSave().SaveFriends(nomi,lista.getUsername());
                        }
                        else if(!amici.isChecked() && check)
                        {
                            nomi.remove(MainActivity.username);
                            lista.setAmici(nomi);
                            new DataSave().SaveFriends(nomi,lista.getUsername());
                        }
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
