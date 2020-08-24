package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.utilities.data.DataLoad;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MainActivity.preferences = getSharedPreferences("login", MODE_PRIVATE);
        MainActivity.username=MainActivity.preferences.getString("user","nnn");
        if(CheckConnection()) {
            //Intent i=new Intent(SplashActivity.this,MainActivity.class);
            Carica();

            Thread background = new Thread() {
                public void run() {
                    try {
                        sleep(10 * 1000);
                    /*
                    Intent i=new Intent(SplashActivity.this,MainActivity.class);
                    if(connected)
                        startActivity(i);*/
                        System.out.println("ciaooooooooo");
                        finish();
                    } catch (Exception e) {
                        System.out.println("Problema nel caricamento main activity");
                    }
                }
            };
            background.start();
        }
        else
            finish();
    }

    public boolean CheckConnection() {
        Context mContext=this;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connectivityManager.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connectivityManager.getNetworkInfo(mNetwork);
            if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                return true;
            }
        }
        Toast.makeText(mContext,mContext.getString(R.string.please_connect_to_internet), Toast.LENGTH_LONG).show();
        return false;
    }
    public void Carica(){
        DataLoad d=new DataLoad(this);
        d.LoadMatchAndGo(MainActivity.username);
        d.LoadUserAndGo();
    }



}
