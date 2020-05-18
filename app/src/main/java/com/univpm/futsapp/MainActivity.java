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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
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
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DataLoad();
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        apriLogin();
        bottomNavigationView = findViewById(R.id.bottomNav);

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.d("TOKENMIO",idToken);
                            // Send token to your backend via HTTPS
                            // ...
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                });
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
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
            else if(resultCode== RESULT_CANCELED)
                finish();
        }
    }


@Override
    protected void onResume() {
        super.onResume();
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
}