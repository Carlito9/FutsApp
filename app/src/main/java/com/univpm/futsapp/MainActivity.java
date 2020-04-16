package com.univpm.futsapp;



import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.View;

import android.widget.Toast;



import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    Toolbar toolbar;

    NavigationView navigationView;

    ActionBarDrawerToggle toogle;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        drawerLayout = findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar);

        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);

        drawerLayout.addDrawerListener(toogle);

        toogle.syncState();



        ButtonHandler bh = new ButtonHandler();

        findViewById(R.id.bGruppi).setOnClickListener(bh);

        findViewById(R.id.bStorico).setOnClickListener(bh);

        findViewById(R.id.bIdeeFuture).setOnClickListener(bh);

        findViewById(R.id.bNuovaPartita).setOnClickListener(bh);

        findViewById(R.id.bProssimoEvento).setOnClickListener(bh);



    }







    @Override

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.profile:

                Toast.makeText(MainActivity.this, "Profile Selected", Toast.LENGTH_LONG).show();

                break;

            case R.id.contact:

                Toast.makeText(MainActivity.this, "Contact us Selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.about:

                Toast.makeText(MainActivity.this, "About us Selected", Toast.LENGTH_SHORT).show();

                break;

            case R.id.logout:

                Toast.makeText(MainActivity.this, "Log Out Selected", Toast.LENGTH_SHORT).show();

                break;

        }





        return false;

    }



    private class ButtonHandler implements View.OnClickListener {



        @Override

        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.bGruppi:

                    show("visualizzo i gruppi");

                    break;

                case R.id.bStorico:

                    show("visualizzo lo storico");

                    break;

                case R.id.bIdeeFuture:

                    show("cosa ci riserva il futuro");

                    break;

                case R.id.bNuovaPartita:

                    show("creiamo una nuova partita");
                    openNewGame();

                    break;

                case R.id.bProssimoEvento:

                    show("il prossimo evento è...");

                    break;

                default:

                    show("non dovrebbe succedere");

            }



        }

    }

public void openNewGame(){
    Intent intent = new Intent(this,NewGameActivity.class);
    startActivity(intent);
    }

    void show(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

    }

}