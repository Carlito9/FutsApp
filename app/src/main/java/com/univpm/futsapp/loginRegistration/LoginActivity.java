package com.univpm.futsapp.loginRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText  textEmail, textPassword;
    private Button bLogin;
    private FirebaseAuth mAuth;
    public static final int REGISTER_REQUEST = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        bLogin = findViewById(R.id.bLogin);
        mAuth = FirebaseAuth.getInstance();
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            aggiornaUser();
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                }
                catch (IllegalArgumentException e){
                    Toast.makeText(LoginActivity.this, "Riempire tutti i campi per favore",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registra(View view) {
       lanciaReg();
    }

    private void lanciaReg() {
        Intent launchRegister = new Intent(LoginActivity.this, Register.class);
        startActivityForResult(launchRegister, REGISTER_REQUEST);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent getCredentials) {
        super.onActivityResult(requestCode, resultCode, getCredentials);
        if (requestCode == REGISTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                aggiornaUser();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else if (RESULT_CANCELED==resultCode)
            {Toast.makeText(LoginActivity.this, "Connessione debole o assente", Toast.LENGTH_SHORT).show();
                lanciaReg();}
        }
    }

    private void aggiornaUser() {
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstrun", false);
        editor.putString("user",mAuth.getCurrentUser().getDisplayName());
        editor.apply();

    }
}
