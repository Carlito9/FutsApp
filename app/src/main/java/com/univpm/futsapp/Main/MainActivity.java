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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.univpm.futsapp.Profile;
import com.univpm.futsapp.R;
import com.univpm.futsapp.SplashActivity;
import com.univpm.futsapp.utilities.listForAdapter.DataList;
import com.univpm.futsapp.loginRegistration.LoginActivity;
import com.univpm.futsapp.utilities.data.DataLoad;
import com.univpm.futsapp.utilities.listForAdapter.Matchlist;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final int LOGIN_REQUEST = 101;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    View headerView;
    ImageView profilePic;
    ActionBarDrawerToggle toogle;
    String email="nessuna";
    public static SharedPreferences preferences;
    public static String username;
    public static boolean check;
    public static DataList[] players;
    public static Matchlist[] daFare;
    public static Matchlist[] giocate;
    public static Matchlist[] daRegistrare;
    public static DataList user;
    public static DataLoad LoadImage=new DataLoad();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //riempie array giocatori
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        profilePic=headerView.findViewById(R.id.profilePic);

        preferences = getSharedPreferences("login", MODE_PRIVATE);
        apriLogin();
        bottomNavigationView = findViewById(R.id.bottomNav);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(this)).commit();
        }
        aggiorna();
        check=true;
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
                        fragment = new FragmentNextEvent();
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
            if (resultCode == RESULT_OK)
            {

                FirebaseUser currentUser = mAuth.getCurrentUser();
                username=currentUser.getDisplayName();
                Intent intent= new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
            }
            else if(resultCode== RESULT_CANCELED)
                finish();
        }



    }


@Override
    protected void onResume() {
        super.onResume();

        if(email.equals("nessuna") && !preferences.getBoolean("firstrun", true)) {
            //FirebaseFirestore db = FirebaseFirestore.getInstance();
            //mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            username = currentUser.getDisplayName();
            email = currentUser.getEmail();
            TextView slotUsername = (TextView) headerView.findViewById(R.id.slotUsername);
            slotUsername.setText(username);
            TextView slotEmail = (TextView) headerView.findViewById(R.id.slotEmail);
            slotEmail.setText(email);
            try {LoadImage.LoadImage(username,profilePic); }
            catch (IOException e) {e.printStackTrace();}
        }
    }


    public void onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile: {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Profile Selected", Toast.LENGTH_LONG).show();
                break;}
            case R.id.about: {
                Toast.makeText(MainActivity.this, "About us Selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
                break;
            }
            case R.id.logout: {
                logout();
            }
            break;
        }
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstrun", true);
        editor.apply();
        apriLogin();
    }

    void apriLogin (){

        if (preferences.getBoolean("firstrun", true)) {
            Intent launchLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(launchLogin, LOGIN_REQUEST);
        }
        else {
            try {
                LoadImage.LoadImage(username, profilePic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void Carica(){
        DataLoad d=new DataLoad();
        d.LoadMatch(MainActivity.username);
        d.LoadUser();
    }

    private void aggiorna()
    {
        Thread background = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(5*60*1000);
                        if (!check) {
                            Carica();
                        } else
                            check = false;
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
            }
        };
        background.start();
    }


}