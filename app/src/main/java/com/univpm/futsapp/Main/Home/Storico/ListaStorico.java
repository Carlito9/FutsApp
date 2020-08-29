package com.univpm.futsapp.Main.Home.Storico;

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
import com.univpm.futsapp.R;
import com.univpm.futsapp.utilities.listForAdapter.Matchlist;

import java.io.IOException;


public class ListaStorico extends RecyclerView.Adapter<ListaStorico.ViewHolder> {
        private Matchlist[] listdata;
        private Dialog myDialog;

        public ListaStorico(Matchlist[] listdata, Context context){
            this.listdata=listdata;
            myDialog=new Dialog(context);
        }
        @NonNull
        @Override
        public ListaStorico.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.fragment_storico_item, parent, false);

            ListaStorico.ViewHolder viewHolder = new ListaStorico.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ListaStorico.ViewHolder holder, int position) {
            final Matchlist lista = listdata[position];
            myDialog.setContentView(R.layout.popup_vedi_risultato);
            String golC = lista.getgolCasa();
            String golO = lista.getgolOspite();
            if((Long.parseLong(golC)>Long.parseLong(golO) && lista.getTeams().indexOf(MainActivity.username)<5) ||
                    (Long.parseLong(golC)<Long.parseLong(golO) && lista.getTeams().indexOf(MainActivity.username)>4))
                holder.cardView.setBackgroundResource(R.drawable.background_storico_vinta);
            else if(Long.parseLong(golC)!=Long.parseLong(golO))
                holder.cardView.setBackgroundResource(R.drawable.background_storico_persa);
            holder.golCasa.setText(golC);
            holder.golOspite.setText(golO);
            holder.data.setText(String.valueOf(lista.getData()));
            holder.luogo.setText(String.valueOf(lista.getLuogo()));
            holder.orario.setText(String.valueOf(lista.getOrario()));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView txtclose;
                    TextView[] casa;
                    TextView[] trasferta;
                    TextView[] golA;
                    TextView[] golB;
                    TextView ora;
                    TextView data;
                    TextView stadio;
                    TextView golCasa;
                    TextView golOspiti;


                    txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                    casa=(TextView[]) new TextView[]{myDialog.findViewById(R.id.playerA0), myDialog.findViewById(R.id.playerA1), myDialog.findViewById(R.id.playerA2), myDialog.findViewById(R.id.playerA3), myDialog.findViewById(R.id.playerA4)};
                    trasferta=(TextView[]) new TextView[]{myDialog.findViewById(R.id.playerB0), myDialog.findViewById(R.id.playerB1), myDialog.findViewById(R.id.playerB2), myDialog.findViewById(R.id.playerB3), myDialog.findViewById(R.id.playerB4)};
                    golA= (TextView[]) new TextView[] {myDialog.findViewById(R.id.golA0),myDialog.findViewById(R.id.golA1),myDialog.findViewById(R.id.golA2),myDialog.findViewById(R.id.golA3),myDialog.findViewById(R.id.golA4)};
                    golB= (TextView[]) new TextView[] {myDialog.findViewById(R.id.golB0),myDialog.findViewById(R.id.golB1),myDialog.findViewById(R.id.golB2),myDialog.findViewById(R.id.golB3),myDialog.findViewById(R.id.golB4)};
                    for (int i=0;i<5;i++){
                        casa[i].setText(lista.getTeams().get(i));
                        golA[i].setText(String.valueOf((int)(long) lista.getGolgioc()[i]));
                        trasferta[i].setText(lista.getTeams().get(i+5));
                        golB[i].setText(String.valueOf((int)(long)lista.getGolgioc()[i+5]));
                    }
                    ora= (TextView) myDialog.findViewById(R.id.oraPartita);
                    data= (TextView) myDialog.findViewById(R.id.date);
                    stadio= (TextView) myDialog.findViewById(R.id.stadio);
                    golCasa= (TextView) myDialog.findViewById(R.id.golCasa);
                    golOspiti= (TextView) myDialog.findViewById(R.id.golOspite);
                    ora.setText(lista.getOrario());
                    data.setText(lista.getData());
                    stadio.setText(lista.getLuogo());
                    golCasa.setText(lista.getgolCasa());
                    golOspiti.setText(lista.getgolOspite());

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

            TextView golCasa;
            TextView golOspite;
            TextView luogo;
            TextView data;
            TextView orario;
            LinearLayout cardView;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.golOspite = itemView.findViewById(R.id.golOspite);
                this.golCasa=itemView.findViewById(R.id.golCasa);
                this.data = (TextView) itemView.findViewById(R.id.Data);
                this.luogo = (TextView) itemView.findViewById(R.id.Luogo);
                this.orario = (TextView) itemView.findViewById(R.id.Orario);
                this.cardView= (LinearLayout) itemView.findViewById(R.id.card_view);
            }
        }


}
