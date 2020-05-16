package com.univpm.futsapp;
//"?android:attr/listPreferredItemHeightLarge"
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    String[] teamA={"0","0","0","0","0"};
    String[] teamB={"0","0","0","0","0"};
    Set<String> chosen= new HashSet<>();

    public static DataList[] players;
    public static final int  CHOOSE_PLAYER= 103;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        teamA[0]=currentUser.getDisplayName();



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
                        month=monthofyear+1;
                        day=dayOfMonth;
                        Data.setText(day+"/"+month+"/"+y);

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
        partita.put("data", day+"-"+month+"-"+y);
        partita.put("ora", time);
        if(teamA[4].equals("aggiungi giocatore") && teamB[4].equals("aggiungi giocatore"))
            Salva(partita);
        else
            Toast.makeText(this, "Crea le squadre", Toast.LENGTH_SHORT).show();
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
                        throw new RuntimeException();
                    match.put("giocatoreA" + i, teamA[i]);
                }
            }

            final DocumentReference docRef = db.collection("partite").document(String.valueOf(y)).collection(String.valueOf(y)).document(String.valueOf(month)).collection(String.valueOf(month)).document(String.valueOf(day)).collection(String.valueOf(day)).document();
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
        catch (RuntimeException e){Toast.makeText(NewGameActivity.this, "Stesso Giocatore presente un due squadre", Toast.LENGTH_SHORT).show();}
    }


    public void FragSetter(String[] team, RecyclerView giocatori){
        giocatori.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);

        mAdapter = new ListaDoppiaGiocatori(players,this,team);
        giocatori.setAdapter(mAdapter);
        giocatori.setLayoutManager(lManager);

    }

    private void RiempiLista(List<Map<String,Object>> lista)
    {
        //List<DataList> l=new ArrayList<>();
        players=new DataList[lista.size()];
        int i=0;
        for(Map<String,Object> m: lista)
        {
            DataList a=new DataList((String)m.get("username"),Integer.parseInt(String.valueOf(m.get("rating"))));
            players[i++]=a;
            // l.add(new DataList((String)m.get("username"),Integer.parseInt(String.valueOf(m.get("rating")))));
        }

        //players= (DataList[]) l.toArray();
    }
}
