package com.univpm.futsapp;



import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.univpm.futsapp.NewGame.DataList;
import com.univpm.futsapp.loginRegistration.LoginActivity;
import com.univpm.futsapp.utilities.DataLoad;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final int LOGIN_REQUEST = 101;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    View headerView;
    ActionBarDrawerToggle toogle;
    public static SharedPreferences preferences;
    public static String username;
    public static boolean check;
    public static DataList[] players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            //riempie array giocatori
        check=false;
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        apriLogin();
        bottomNavigationView = findViewById(R.id.bottomNav);

        /*NotificationCompat.Builder n = new NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID)
                .setContentTitle("Arrivato nuovo messaggio!!")
                .setContentText("Autore: Nicola Rossi")
                .setSmallIcon(android.R.drawable.ic_dialog_email);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());*/
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(this)).commit();
        }
        bottomNavigationView.setSelectedItemId(R.id.house);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {

                    case R.id.house:
                        fragment = new HomeFragment(MainActivity.this);
                        break;
                    case R.id.contact:
                        fragment = new FragmentContact();
                        break;
                    case R.id.search:
                        fragment = new FragmentSearch();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });


        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent getCredentials) {
        super.onActivityResult(requestCode, resultCode, getCredentials);
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == RESULT_OK) {

                FirebaseUser currentUser = mAuth.getCurrentUser();
                username=currentUser.getDisplayName();
                CaricaPartite();
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstrun", false);
                editor.putString("user",username);
                editor.apply();
                            }
            else if(resultCode== RESULT_CANCELED)
                finish();
        }
    }


@Override
    protected void onResume() {
        super.onResume();

        if(!check) {//FirebaseFirestore db = FirebaseFirestore.getInstance();
            String email;
            //mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            username = currentUser.getDisplayName();
            email = currentUser.getEmail();
            TextView slotUsername = (TextView) headerView.findViewById(R.id.slotUsername);
            slotUsername.setText(username);
            TextView slotEmail = (TextView) headerView.findViewById(R.id.slotEmail);
            slotEmail.setText(email);
            check = true;

        }
    }


    public void onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            case R.id.logout: {
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstrun", true);
                editor.apply();
                apriLogin();
            }
            break;
        }

    }

    void apriLogin (){
        if (preferences.getBoolean("firstrun", true)) {
            Intent launchLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(launchLogin, LOGIN_REQUEST);
        }

    }

    public static void CaricaPartite(){
        new DataLoad(username);
    }
    public static void CaricaUtenti(){
        new DataLoad();
    }


}