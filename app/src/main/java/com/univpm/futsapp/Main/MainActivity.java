package com.univpm.futsapp.Main;



import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.univpm.futsapp.R;
import com.univpm.futsapp.utilities.listForAdapter.DataList;
import com.univpm.futsapp.loginRegistration.LoginActivity;
import com.univpm.futsapp.utilities.data.DataLoad;
import com.univpm.futsapp.utilities.listForAdapter.Matchlist;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final int LOGIN_REQUEST = 101;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    View headerView;
    ActionBarDrawerToggle toogle;
    String email="nessuna";
    public static SharedPreferences preferences;
    public static String username;
    public static boolean check;
    public static DataList[] players;
    public static Matchlist[] daFare;
    public static Matchlist[] giocate;
    public static Matchlist[] daRegistrare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBadge();
            //riempie array giocatori
        aggiorna();
        check=true;
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        apriLogin();
        bottomNavigationView = findViewById(R.id.bottomNav);

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
        if(email.equals("nessuna")) {//FirebaseFirestore db = FirebaseFirestore.getInstance();
            //mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            username = currentUser.getDisplayName();
            email = currentUser.getEmail();
            TextView slotUsername = (TextView) headerView.findViewById(R.id.slotUsername);
            slotUsername.setText(username);
            TextView slotEmail = (TextView) headerView.findViewById(R.id.slotEmail);
            slotEmail.setText(email);
        }
        CheckBadge();
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

    private void aggiorna()
    {
        Thread background = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(5*60*1000);
                        if (!check) {
                            CaricaPartite();
                            CaricaUtenti();
                        } else
                            check = false;
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }
            }
        };
        background.start();
    }

    public void CheckBadge() {
        try {
            if (MainActivity.daRegistrare.length == 0)
                findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
        } catch (NullPointerException e) {
                findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);
        }
    }
}