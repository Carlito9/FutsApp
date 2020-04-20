package com.univpm.futsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NewGameActivity extends AppCompatActivity   {

    TextView tv;
    Calendar mCurrentDate;
    int day,month,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        ButtonHandler bh = new ButtonHandler();
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
                    show("Ancora da creare");
            }
        }
    }


    void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

}
