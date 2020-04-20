package com.univpm.futsapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private TextInputEditText textUser, textEmail, textPassword;
    private Button btnRegistra;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        textUser = findViewById(R.id.text_user);
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        btnRegistra = findViewById(R.id.btn_registra);
        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    final String username=textUser.getText().toString();
                    String email = textEmail.getText().toString();
                    String password = textPassword.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                final FirebaseUser user=mAuth.getCurrentUser();
                                Toast.makeText(Register.this, "Registrazione completata", Toast.LENGTH_SHORT).show();
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .build();
                                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        InsertUser(username, user.getUid());
                                        Intent intent = new Intent();
                                        intent.putExtra("username", textUser.getText().toString());
                                        intent.putExtra("email", textEmail.getText().toString());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
                            }else
                            {
                                Toast.makeText(Register.this,"Vafamocc a sort",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                catch (NullPointerException e){
                Toast.makeText(Register.this, "Riempire tutti i campi per favore",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void InsertUser(String username, String uid) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("partite giocate",0);
        user.put("vittorie",0);
        user.put("pareggi",0);
        user.put("sconfitte",0);
        user.put("gol fatti",0);
        user.put("media voto",6.0);
        user.put("rating",60);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("utenti").document(uid).set(user);
    }
}