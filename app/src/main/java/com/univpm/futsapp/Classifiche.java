package com.univpm.futsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.univpm.futsapp.NewGame.DataList;
import com.univpm.futsapp.NewGame.NewGameActivity;

import java.util.Arrays;

public class Classifiche extends AppCompatActivity {
    TextView textClassifica;
    TextView textMarcatori;
    DataList[] classifica=new DataList[NewGameActivity.amici.length];
    DataList[] marcatori=new DataList[NewGameActivity.amici.length];;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifiche);

        ordina(savedInstanceState);

        textClassifica=findViewById(R.id.textClassifica);
        textMarcatori=findViewById(R.id.textMarcatori);
        textClassifica.setTextSize(14);
        textMarcatori.setTextSize(12);
        textClassifica.setTextColor(getColor(R.color.c1));

    }



    public void navigaClassifica(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.textClassifica: {
                FormattaTesto(textClassifica,textMarcatori);
                fragment = new FragmentClassifica(Classifiche.this, classifica);
                break;
            }
            case R.id.textMarcatori:{
                FormattaTesto(textMarcatori, textClassifica);
                fragment = new FragmentMarcatori(Classifiche.this,marcatori);
                break;
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contenitore, fragment).commit();
    }

    private void FormattaTesto(TextView a, TextView b) {
        a.setTextSize(14);
        b.setTextSize(12);
        a.setTextColor(getColor(R.color.c1));
        b.setTextColor(getColor(R.color.black));

    }

    private void ordina(Bundle savedInstanceState) {

        boolean verifica;
        DataList temp;
        classifica= Arrays.copyOf(NewGameActivity.amici,NewGameActivity.amici.length);
        marcatori= Arrays.copyOf(NewGameActivity.amici,NewGameActivity.amici.length);
        for(int i=0;i<classifica.length;i++)
        {

            verifica=false;
            for(int j=1;j<classifica.length;j++)
            {

                if(((long)classifica[j].getDati().get("vittorie")>(long)classifica[j-1].getDati().get("vittorie")) ||
                        ((long)classifica[j].getDati().get("vittorie")==(long)classifica[j-1].getDati().get("vittorie") &&
                                (long)classifica[j].getDati().get("partite giocate")<(long)classifica[j-1].getDati().get("partite giocate")))
               {
                    temp=classifica[j-1];
                    classifica[j-1]=classifica[j];
                    classifica[j]=temp;
                    verifica=true;
                }
               if(((long)marcatori[j].getDati().get("gol fatti")>(long)marcatori[j-1].getDati().get("gol fatti")) ||
                        ((long)marcatori[j].getDati().get("gol fatti")==(long)marcatori[j-1].getDati().get("gol fatti") &&
                                (long)marcatori[j].getDati().get("partite giocate")<(long)marcatori[j-1].getDati().get("partite giocate")))
                {
                    temp=marcatori[j-1];
                    marcatori[j-1]=marcatori[j];
                    marcatori[j]=temp;
                    verifica=true;
                }
            }
            if(!verifica)
                break;
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenitore, new FragmentClassifica(this,classifica)).commit();
        }
           }

}
