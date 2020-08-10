package com.univpm.futsapp.utilities;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.univpm.futsapp.FragmentContact;
import com.univpm.futsapp.Matchlist;
import com.univpm.futsapp.NewGame.DataList;
import com.univpm.futsapp.NewGame.NewGameActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataLoad {
    FirebaseFirestore db;
    List<DataList> lista=new ArrayList<>();
    List<Matchlist> list=new ArrayList<>();

    public DataLoad()
    {
        db= FirebaseFirestore.getInstance();
        db.collection("utenti")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DataList a=new DataList((String)document.getData().get("username"),Integer.parseInt(String.valueOf(document.getData().get("rating"))));
                                lista.add(a);

                            }
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }

                        NewGameActivity.players= lista.toArray(new DataList[0]);}
                });

    }
    public DataLoad(String user) {
        /*String temp;
        for (int i = 0; i < 10; i++) {
            if(i<5)
                temp="A"+i;
            else
                temp="B"+i;*/
            Calendar calendario;
            calendario= Calendar.getInstance();
            int day=calendario.get(Calendar.DAY_OF_MONTH);
            int month=calendario.get(Calendar.MONTH)+1;
            int y=calendario.get(Calendar.YEAR);

            db = FirebaseFirestore.getInstance();

                //db.collection("partite").document(String.valueOf(y)).collection(String.valueOf(y)).document(String.valueOf(month)).collection(String.valueOf(month)).document(String.valueOf(day)).collection(String.valueOf(day))
                        //.whereEqualTo("giocatore" + temp, user)
                        db.collection("partite")
                        .whereArrayContains("giocatori", user)
                        .whereGreaterThanOrEqualTo("data",y*10000+(month)*100+day)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        ArrayList<String> giocatori= (ArrayList<String>) document.getData().get("giocatori");
                                        Matchlist a = new Matchlist(giocatori, ConvertData((int)(long)document.getData().get("data")), (String) document.getData().get("ora"), (String) document.getData().get("luogo"),(int)(long)document.getData().get("costo"));
                                        list.add(a);
                                    }
                                } else {
                                    System.out.println("Error getting documents: " + task.getException());
                                }
                                FragmentContact.matchlist = list.toArray(new Matchlist[0]);
                            }
                        });


    }

    private String ConvertData(Integer data){
        int giorno=data%100;
        int mese=(data-giorno)/100%100;
        int anno=(data-(mese*100)-giorno)/10000%10000;
        return giorno+"-"+mese+"-"+anno;
    }
}
