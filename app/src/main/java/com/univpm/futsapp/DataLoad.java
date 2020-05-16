package com.univpm.futsapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataLoad {
    FirebaseFirestore db;
    List<DataList> lista=new ArrayList<>();

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
}
