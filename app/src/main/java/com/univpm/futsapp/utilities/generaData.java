package com.univpm.futsapp.utilities;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

class generaData {
   static void generare(String anno){
        final FirebaseFirestore db;
        db= FirebaseFirestore.getInstance();
        Map<String,Object> date=new HashMap<>();
        date.put("anno",anno);

        db.collection("partite").document(anno).set(date);
        DocumentReference ref;
        date.remove("anno");
        for(int i=1;i<13;i++)
        {
            ref=db.collection("partite").document(anno);
            date.put("mese",i);
            ref.collection(anno).document(String.valueOf(i)).set(date);
            ref=ref.collection(anno).document(String.valueOf(i));
            date.remove("mese");
            for(int j=1;j<32;j++)
            {
                date.put("giorno",j);
                ref.collection(String.valueOf(i)).document(String.valueOf(j)).set(date);
                date.remove("giorno");
            }
        }

    }
}
