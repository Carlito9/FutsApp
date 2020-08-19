package com.univpm.futsapp.utilities.data;

import android.app.Dialog;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.univpm.futsapp.Main.Home.InserisciRisultato.InserisciRisultato;
import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.utilities.listForAdapter.DataList;
import com.univpm.futsapp.Main.Home.NewGame.NewGameActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DataSave {
        private FirebaseFirestore db;
    public void SaveMatch(int totale_casa, int totale_ospiti, final Integer[] golgiocA, final Integer[] golgiocB, final ArrayList<String> teams, final Dialog myDialog, String id) {
        db=FirebaseFirestore.getInstance();
        final int segno;
        if(totale_casa>totale_ospiti)
        {
            segno=1;
        }
        else if (totale_ospiti>totale_casa)
        {
            segno=2;
        }
        else
            segno=0;
        final Integer[] golgioc= ArrayUtils.concat(golgiocA,golgiocB);
        if(sommaArray(golgiocA)==totale_casa && sommaArray(golgiocB)==totale_ospiti)
        {
            db.collection("partite").document(id).update("risultato",totale_casa+"-"+totale_ospiti,
                                                                    "golgioc", Arrays.asList(golgioc))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        MainActivity.CaricaPartite();
                        MainActivity.CaricaUtenti();
                        SavePlayer(golgioc,teams,segno);
                        Thread background = new Thread() {
                            public void run() {
                                try {sleep(3*1000);
                                    myDialog.dismiss();
                                    new InserisciRisultato().CreaFragment();
                                } catch (Exception e) {System.out.println("Problema nel salvataggio");
                                }
                            }
                        };
                        background.start();


                    }
                    else
                        Toast.makeText(myDialog.getContext(), "salvataggio non riuscito", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(myDialog.getContext(), "errore nell'inserimento dati. Ricontrolla, grazie", Toast.LENGTH_SHORT).show();

    }

    private void SavePlayer(Integer[] golgioc, ArrayList<String> teams,int segno) {
        db=FirebaseFirestore.getInstance();
        CollectionReference coll=db.collection("utenti");
        int index;

        for (DataList d: NewGameActivity.amici)
        {
            if(teams.contains(d.getUsername()))
            {
                index=teams.indexOf(d.getUsername());
                if((index<5 && segno==1) || ((index>4 && segno==2))) {
                    RegistraGiocatore(coll,"vittorie",d,golgioc[index]);
                }
                else if(segno==0){
                    RegistraGiocatore(coll,"pareggi",d,golgioc[index]);
                }
                else{
                    RegistraGiocatore(coll,"sconfitte",d,golgioc[index]);
                }
            }
        }
    }

    private void RegistraGiocatore(CollectionReference coll, String esito, DataList d, Integer gol) {
        Map<String,Object> map;
        map=d.getDati();
        coll.document(d.getUsername()).update("gol fatti", ((int)(long) map.get("gol fatti") +gol),
                "partite giocate",((int)(long)map.get("partite giocate")+1),
                esito,((int)(long)map.get(esito)+1));
    }


    private Integer sommaArray(Integer[] arr){
        Integer somma=0;
        for (Integer i:arr)
            somma+=i;
        return somma;
    }

    public void SaveFriends(ArrayList<String> nomi, String user) {
        db=FirebaseFirestore.getInstance();
        db.collection("utenti").document(user).update("amici",nomi);
    }
}
