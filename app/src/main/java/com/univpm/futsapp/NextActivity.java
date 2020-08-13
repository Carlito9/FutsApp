package com.univpm.futsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.annotation.Documented;

public class NextActivity extends AppCompatActivity {

    FirebaseFirestore db;
    DocumentReference Doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        ButtonHandler bh = new ButtonHandler();
        findViewById(R.id.back).setOnClickListener(bh);

        db= FirebaseFirestore.getInstance();
        Doc = db.collection("partite").document("2020").collection("2020").document("5").collection("5").document("13").collection("13").document("fiore13");
        Doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println(document.getData().get("ora"));
                        Toast.makeText(NextActivity.this, "esiste", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(NextActivity.this, "Non esiste", Toast.LENGTH_LONG).show();
                                            }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
    }
        );}

    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
            }
        }
    }
}