package com.univpm.futsapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {

    private TextInputEditText textNome, textCognome, textEmail, textPassword;
    private Button btnRegistra;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        textNome = findViewById(R.id.text_nome);
        textCognome = findViewById(R.id.text_cognome);
        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        btnRegistra = findViewById(R.id.btn_registra);
        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String email = textEmail.getText().toString();
            String password = textPassword.getText().toString();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                        Toast.makeText(Register.this,"Registrazione completata",Toast.LENGTH_SHORT).show();
                    else
                    {

                        Toast.makeText(Register.this,"Vafamocc a sort",Toast.LENGTH_SHORT).show();}
                }
            });
            }
        });
    }
}
