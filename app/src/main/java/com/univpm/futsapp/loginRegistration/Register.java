package com.univpm.futsapp.loginRegistration;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.univpm.futsapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private TextInputEditText textUser, textEmail, textPassword;
    private Button btnRegistra;
    private FirebaseAuth mAuth;
    private Dialog myDialog;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myDialog=new Dialog(this);
        textUser = findViewById(R.id.text_user);
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        btnRegistra = findViewById(R.id.btn_registra);
        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.popup_loading);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
                int error=0;
                try {
                    final String username = textUser.getText().toString();
                    if (username.length()<11)
                        error=1;
                    final String email = textEmail.getText().toString();
                    if(!email.contains("@"))
                        error=2;
                    final String password = textPassword.getText().toString();
                    if(password.length()<6)
                        error=3;

                    CreateUser(username, email, password);

                } catch (IllegalArgumentException e) {
                    myDialog.dismiss();
                    if (error==1)
                        Toast.makeText(Register.this, "username troppo lungo", Toast.LENGTH_SHORT).show();
                    if (error==2)
                        Toast.makeText(Register.this, "email non esistente", Toast.LENGTH_SHORT).show();
                    if (error==3)
                        Toast.makeText(Register.this, "password troppo breve", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this, "Riempire tutti i campi per favore", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


        private void CreateUser(final String username, final String email, final String password){
            Thread aspetta = new Thread() {
                public void run() {
                    try {
                        sleep(20 * 1000);
                        if(myDialog.isShowing()) {
                            myDialog.dismiss();
                            Intent i = new Intent();
                            setResult(RESULT_CANCELED, i);
                            finish();
                        }
                    } catch (Exception e) {
                        System.out.println("Problema nel salvataggio");
                        e.printStackTrace();
                    }
                }
            };
            aspetta.start();
            db.collection("utenti").whereEqualTo("username",username).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                if(task.getResult().isEmpty())
                                {
                                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                final FirebaseUser user = mAuth.getCurrentUser();
                                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username)
                                                        .build();
                                                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        InsertUser(username, user);


                                                    }
                                                });
                                            } else {
                                                Toast.makeText(Register.this, "Fallito", Toast.LENGTH_SHORT).show();
                                                myDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(Register.this, "Nome già esistente", Toast.LENGTH_SHORT).show();
                                    myDialog.dismiss();
                                }
                            }
                            else {
                                Toast.makeText(Register.this, "Problema nella creazione utente", Toast.LENGTH_SHORT).show();
                                myDialog.dismiss();
                            }
                        }
                    });

        }


    private void InsertUser(String username, final FirebaseUser utente) {
        final Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("partite giocate", 0);
        user.put("vittorie", 0);
        user.put("pareggi", 0);
        user.put("sconfitte", 0);
        user.put("gol fatti", 0);
        user.put("amici", Arrays.asList(username));



            db.collection("utenti").document(username).set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                 Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                myDialog.dismiss();
                                finish();
                            } else {
                                Toast.makeText(Register.this, "Fallito", Toast.LENGTH_SHORT).show();
                                utente.delete();
                                myDialog.dismiss();
                            }
                        }
                    });

    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent();
        setResult(RESULT_FIRST_USER,intent);
        finish();
    }
}