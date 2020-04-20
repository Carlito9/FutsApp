package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText  textEmail, textPassword;
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        bLogin = findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent giveCredentials = new Intent();
                giveCredentials.putExtra("email", textEmail.getText().toString());
                setResult(RESULT_OK, giveCredentials);
                finish();
            }
        });
    }
}
