package com.univpm.futsapp.NewGame;
//"?android:attr/listPreferredItemHeightLarge"
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.univpm.futsapp.MainActivity;
import com.univpm.futsapp.R;



import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.univpm.futsapp.MainActivity.players;

public class NewGameActivity extends AppCompatActivity   {


    EditText Luogo,Costo;

    TimePicker Orario;
    TextView Data;
    RecyclerView giocatoriA;
    RecyclerView giocatoriB;
    RecyclerView.LayoutManager lManager;
    RecyclerView.Adapter mAdapter;
    Calendar mCurrentDate;
    int day,month,y;
    String time;
    public static DataList[] amici;


    String[] teamA={"0","0","0","0","0"};
    String[] teamB={"0","0","0","0","0"};
    //Set<String> chosen= new HashSet<>();


    public static final int  CHOOSE_PLAYER= 103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        teamA[0]= MainActivity.username;



        Luogo=(EditText)findViewById(R.id.Luogo);
        Costo=(EditText)findViewById(R.id.Costo);
        Data=(TextView)findViewById(R.id.Data);
        Orario=(TimePicker)findViewById(R.id.Orario);


        giocatoriA = (RecyclerView) findViewById(R.id.SquadraA);
        FragSetter(teamA,giocatoriA);
        giocatoriB = (RecyclerView) findViewById(R.id.SquadraB);
        FragSetter(teamB,giocatoriB);


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
                        month=monthofyear;
                        day=dayOfMonth;
                        Data.setText(day+"/"+(month+1)+"/"+y);

                    }
                },y,month,day);
                datePickerDialog.show();
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==CHOOSE_PLAYER && resultCode==RESULT_OK){
            String[] temp=intent.getExtras().getStringArray("partecipanti");
            if(temp.length==4)
            {
            int i=1;
                for(String s:temp)
                    teamA[i++]=s;
                FragSetter(teamA,giocatoriA);
            }
            else
                {
                    int i=0;
                    for(String s:temp)
                        teamB[i++]=s;
                    FragSetter(teamB,giocatoriB);
            }

        }

    }



    public void save(View view){
        Map<String, Object> partita = new HashMap<>();
        try{
        int costo = Integer.parseInt(Costo.getText().toString().trim());
        //String data = Data.getText().toString().trim();
        String luogo = Luogo.getText().toString().trim();
        if(Orario.getMinute()<10)
            time= Orario.getHour() +":0"+Orario.getMinute();
        else
            time= Orario.getHour() +":"+Orario.getMinute();
        partita.put("costo",costo);
        partita.put("luogo",luogo);
        //partita.put("data", day+"-"+(month+1)+"-"+y)
        partita.put("data", y*10000+(month+1)*100+day);

        partita.put("ora", time);
        partita.put("risultato", "no");
        if(!teamA[4].equals("0") && !teamB[4].equals("0"))
            Salva(partita);
        else
            Toast.makeText(this, "Completa le squadre", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(NewGameActivity.this, "Riempire tutti i campi", Toast.LENGTH_SHORT).show();
        }

    }

    void Salva(final Map<String,Object> match) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        try {
            for (int i = 0; i < teamA.length; i++) {
                for (String s : teamB) {
                    if (teamA[i].equals(s))
                        throw new RuntimeException();}
                   // match.put("giocatoreA" + i, teamA[i]);

            }
            match.put("giocatori", Arrays.asList(ArrayUtils.concat(teamA,teamB)));

            final DocumentReference docRef = db.collection("partite").document();
            docRef.set(match).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(NewGameActivity.this, "Salvato", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(NewGameActivity.this, "Salvataggio non riuscito", Toast.LENGTH_SHORT).show();
                }

            });
        }
        catch (RuntimeException e){Toast.makeText(NewGameActivity.this, "Stesso giocatore presente in due squadre", Toast.LENGTH_SHORT).show();}
    }


    public void FragSetter(String[] team, RecyclerView giocatori){
        giocatori.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);

        mAdapter = new ListaDoppiaGiocatori(amici,this,team);
        giocatori.setAdapter(mAdapter);
        giocatori.setLayoutManager(lManager);

    }



}
