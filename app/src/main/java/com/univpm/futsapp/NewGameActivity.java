package com.univpm.futsapp;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewGameActivity extends AppCompatActivity   {

    EditText Name,Luogo,Costo;

    TimePicker Orario;
    TextView Data;
    Button btn_conferma;
    Calendar mCurrentDate;
    int day,month,y;
    String time;



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


        findViewById(R.id.goback).setOnClickListener(bh);
        findViewById(R.id.btn_conferma).setOnClickListener(bh);



        mCurrentDate = Calendar.getInstance();

        day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mCurrentDate.get(Calendar.MONTH);
        y=mCurrentDate.get(Calendar.YEAR);
        Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewGameActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofyear, int dayOfMonth) {

                        y=year;
                        month=monthofyear+1;
                        day=dayOfMonth;
                        Data.setText(day+"/"+month+"/"+y);

                    }
                },y,month,day);
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
                    Map<String, Object> partita = new HashMap<>();
                    int costo = Integer.parseInt(Costo.getText().toString().trim());
                    //String data = Data.getText().toString().trim();
                    String name = Name.getText().toString().trim();
                    String luogo = Luogo.getText().toString().trim();


                    if(Orario.getMinute()<10)
                    time= Orario.getHour() +":0"+Orario.getMinute();
                    else time= Orario.getHour() +":"+Orario.getMinute();
                    partita.put("nomePartita",name);
                    partita.put("costo",costo);
                    partita.put("luogo",luogo);
                    partita.put("data", day+"-"+month+"-"+y);
                    partita.put("ora", time);
                    Salva(partita);

                }
               break;
            }
        }
    }


    void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    void Salva(final Map<String,Object> match) {
        FirebaseFirestore db;
        db= FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("partite").document(String.valueOf(y)).collection(String.valueOf(y)).document(String.valueOf(month)).collection(String.valueOf(month)).document(String.valueOf(day)).collection(String.valueOf(day)).document((String) match.get("nomePartita"));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(NewGameActivity.this, "Nome già esistente", Toast.LENGTH_LONG).show();

                    } else {
                        docRef.set(match);
                        Toast.makeText(NewGameActivity.this, "Salvato", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });
    }


}
