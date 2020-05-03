package com.univpm.futsapp;



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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final int LOGIN_REQUEST = 101;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    View headerView;
    ActionBarDrawerToggle toogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(this)).commit();
        }

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
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        if (preferences.getBoolean("firstrun", true)) {
            Intent launchLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(launchLogin, LOGIN_REQUEST);
        } else {
            onResume();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        //Navigator nav = new Navigator();

/*
        findViewById(R.id.bGruppi).setOnClickListener(nav);
        findViewById(R.id.bStorico).setOnClickListener(nav);
        findViewById(R.id.bIdeeFuture).setOnClickListener(nav);
        findViewById(R.id.bNuovaPartita).setOnClickListener(nav);
        findViewById(R.id.bProssimoEvento).setOnClickListener(nav);*/

//funzione che genera database NON TOGLIERE COMMENTO, RESETTA IL DATABASE!!!
        //generaData.generare("2020");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent getCredentials) {
        super.onActivityResult(requestCode, resultCode, getCredentials);
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstrun", false);
                editor.apply();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Map<String, Object> prova;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String username, email;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        username = currentUser.getDisplayName();
        email = currentUser.getEmail();
        TextView slotUsername = (TextView) headerView.findViewById(R.id.slotUsername);
        slotUsername.setText(username);
        TextView slotEmail = (TextView) headerView.findViewById(R.id.slotEmail);
        slotEmail.setText(email);
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
                Intent launchLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(launchLogin, LOGIN_REQUEST);
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstrun", true);
                editor.apply();
                finish();
            }
            break;
        }

    }




  /*  private class Navigator implements View.OnClickListener {

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
                    show("open social");
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

    }*/
/*
public void openNewGame(){
    Intent intent = new Intent(this,NewGameActivity.class);
    startActivity(intent);
    }

void show(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}*/
}