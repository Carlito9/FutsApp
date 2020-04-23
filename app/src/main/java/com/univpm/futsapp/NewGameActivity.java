package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class NewGameActivity extends AppCompatActivity   {
    TextView tv;
    EditText Name,Luogo,Costo;
    TextView Data;
    TimePicker Orario;
    Button btn_conferma;
    Calendar mCurrentDate;
    int day,month,year;
    FirebaseFirestore db;
    FirebaseAuth col;
    Match match;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        ButtonHandler bh = new ButtonHandler();

        Name=(EditText)findViewById(R.id.Name);
        Luogo=(EditText)findViewById(R.id.Luogo);
        Costo=(EditText)findViewById(R.id.Costo);
        Data=(TextView)findViewById(R.id.Data);
        Orario=(TimePicker)findViewById(R.id.Orario);
        System.out.println(Orario);
        col=FirebaseAuth.getInstance();
        currentUser = col.getCurrentUser();
        db= FirebaseFirestore.getInstance();




        findViewById(R.id.goback).setOnClickListener(bh);
        findViewById(R.id.btn_conferma).setOnClickListener(bh);


        tv = (TextView) findViewById(R.id.Data);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month = month+1;

        tv.setText(day+"/"+month+"/"+year);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewGameActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {
                        monthofyear = monthofyear+1;
                        tv.setText(dayOfMonth+"/"+monthofyear+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }




    private class ButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.goback:
                    show("Torniamo indietro");
                    finish();
                    break;
                case R.id.btn_conferma:
                {
                    int costo = Integer.parseInt(Costo.getText().toString().trim());
                    String data = Data.getText().toString().trim();
                    String name = Name.getText().toString().trim();
                    String luogo = Luogo.getText().toString().trim();


                    match = new Match(name,costo,luogo,data);

                    db.collection("partita").document(currentUser.getUid()).set(match);
                    Toast.makeText(NewGameActivity.this,"Salvato",Toast.LENGTH_LONG).show();
                }
               break;
            }
        }
    }


    void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
