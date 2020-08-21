package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.univpm.futsapp.Main.MainActivity;

import static com.univpm.futsapp.Main.MainActivity.CaricaPartite;
import static com.univpm.futsapp.Main.MainActivity.CaricaUtenti;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MainActivity.preferences = getSharedPreferences("login", MODE_PRIVATE);
        MainActivity.username=MainActivity.preferences.getString("user","nnn");
        CaricaUtenti();
        CaricaPartite();

        Thread background = new Thread() {
            public void run() {
                try {sleep(5*1000);
                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {System.out.println("Problema nel caricamento main activity");
                }
            }
        };
        background.start();
    }
}
