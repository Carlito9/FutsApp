package com.univpm.futsapp.utilities.data;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.univpm.futsapp.Main.Home.InserisciRisultato.InserisciRisultato;
import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.Profile;
import com.univpm.futsapp.R;
import com.univpm.futsapp.utilities.listForAdapter.DataList;
import com.univpm.futsapp.Main.Home.NewGame.NewGameActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DataSave {
        private FirebaseFirestore db;
        private StorageReference StorageRef;
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
                        Carica();
                        SavePlayer(golgioc,teams,segno);
                        final Dialog caricamento=new Dialog(myDialog.getContext());
                        caricamento.setContentView(R.layout.popup_loading);
                        caricamento.show();
                        Thread backg = new Thread() {

                            public void run() {

                                try {sleep(3*1000);
                                    caricamento.dismiss();
                                    myDialog.dismiss();

                                    new InserisciRisultato().close();
                                } catch (Exception e) {System.out.println("Problema nel salvataggio");
                                    e.printStackTrace();
                                }
                            }
                        };
                        backg.start();


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


    public void saveImage(Uri imageData, final Context con) {
        StorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ref=StorageRef.child("images/"+MainActivity.username);
        ref.putFile(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(con, "Salvata", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Carica(){
        DataLoad d=new DataLoad();
        d.LoadMatch(MainActivity.username);
        d.LoadUser();
    }
}
