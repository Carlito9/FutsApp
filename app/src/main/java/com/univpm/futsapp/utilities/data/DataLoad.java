package com.univpm.futsapp.utilities.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.univpm.futsapp.GlideApp;
import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.R;
import com.univpm.futsapp.SplashActivity;
import com.univpm.futsapp.Viewed;
import com.univpm.futsapp.utilities.listForAdapter.Matchlist;
import com.univpm.futsapp.utilities.listForAdapter.DataList;
import com.univpm.futsapp.Main.Home.NewGame.NewGameActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataLoad {
    FirebaseFirestore db;
    List<DataList> lista=new ArrayList<>();
    List<DataList> listamici=new ArrayList<>();
    List<Matchlist> giocate=new ArrayList<>();
    List<Matchlist> dafare=new ArrayList<>();
    List<Matchlist> daregistrare=new ArrayList<>();
    Set<Viewed> viewed= new HashSet<>();
    Context con;
    int conta=0;
    public DataLoad(Context con)
    {this.con=con;}

    public DataLoad() {

    }

    public void LoadUserAndGo(){
        db=FirebaseFirestore.getInstance();
        final Intent intent=new Intent(con,MainActivity.class);
                        db.collection("utenti")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                DataList a=new DataList((String)document.getData().get("username"),document.getData());
                                                lista.add(a);
                                                if(document.getData().get("username").equals(MainActivity.username))
                                                    MainActivity.user=a;
                                            }
                                        } else {
                                            System.out.println("Error getting documents: " + task.getException());
                                        }

                                        MainActivity.players= lista.toArray(new DataList[0]);
                                        CaricaAmici(MainActivity.username);
                                        if(conta==1)
                                            con.startActivity(intent);
                                        else
                                            conta++;

                                    }
                                });

    }

    public void LoadUser(){
        db=FirebaseFirestore.getInstance();

        db.collection("utenti")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DataList a=new DataList((String)document.getData().get("username"),document.getData());
                                lista.add(a);
                                if(document.getData().get("username").equals(MainActivity.username))
                                    MainActivity.user=a;

                            }
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }

                        MainActivity.players= lista.toArray(new DataList[0]);
                    }
                });

    }

    public void LoadMatchAndGo(final String user) {

            Calendar calendario;
            calendario= Calendar.getInstance();
            final Intent intent=new Intent(con,MainActivity.class);
            final int day=calendario.get(Calendar.DAY_OF_MONTH);
            final int month=calendario.get(Calendar.MONTH)+1;
            final int y=calendario.get(Calendar.YEAR);
            Query q;
            db = FirebaseFirestore.getInstance();

            q=db.collection("partite").whereArrayContains("giocatori", user).orderBy("data", Query.Direction.ASCENDING);

                    q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult())
                                    {
                                        ArrayList<String> giocatori= (ArrayList<String>) document.getData().get("giocatori");
                                        Matchlist a = new Matchlist(giocatori, ConvertData((int)(long)document.getData().get("data")), (String) document.getData().get("ora"), (String) document.getData().get("luogo"),(String)document.getData().get("risultato"));
                                        if((int) (long)document.getData().get("data")>=(y*10000+(month)*100+day))
                                            dafare.add(a);
                                        else if(a.getTeams().get(0).equals(user) && a.getRisultato().equals(document.getId()))
                                            daregistrare.add(a);
                                        else if(!a.getRisultato().equals(document.getId())) {
                                            a.setGolgioc((ArrayList<Long>) document.getData().get("golgioc"));
                                            giocate.add(a);
                                        }
                                    }
                                } else {
                                    System.out.println("Error getting documents: " + task.getException());
                                }

                                MainActivity.daFare= dafare.toArray(new Matchlist[0]);
                                MainActivity.giocate= giocate.toArray(new Matchlist[0]);
                                MainActivity.daRegistrare= daregistrare.toArray(new Matchlist[0]);
                                Ordina();
                                if(conta==1)
                                {
                                    con.startActivity(intent);
                                }

                                else
                                    conta++;
                            }
                        });


    }




    public void LoadMatch(final String user) {

        Calendar calendario;
        calendario= Calendar.getInstance();
        final int day=calendario.get(Calendar.DAY_OF_MONTH);
        final int month=calendario.get(Calendar.MONTH)+1;
        final int y=calendario.get(Calendar.YEAR);
        Query q;
        db = FirebaseFirestore.getInstance();

        q=db.collection("partite").whereArrayContains("giocatori", user).orderBy("data", Query.Direction.ASCENDING);

        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        ArrayList<String> giocatori= (ArrayList<String>) document.getData().get("giocatori");
                        Matchlist a = new Matchlist(giocatori, ConvertData((int)(long)document.getData().get("data")), (String) document.getData().get("ora"), (String) document.getData().get("luogo"),(String)document.getData().get("risultato"));
                        if((int) (long)document.getData().get("data")>=(y*10000+(month)*100+day))
                            dafare.add(a);
                        else if(a.getTeams().get(0).equals(user) && a.getRisultato().equals(document.getId()))
                            daregistrare.add(a);
                        else if(!a.getRisultato().equals(document.getId())) {
                            a.setGolgioc((ArrayList<Long>) document.getData().get("golgioc"));
                            giocate.add(a);
                        }
                    }
                } else {
                    System.out.println("Error getting documents: " + task.getException());
                }
                MainActivity.daFare= dafare.toArray(new Matchlist[0]);
                MainActivity.giocate= giocate.toArray(new Matchlist[0]);
                MainActivity.daRegistrare= daregistrare.toArray(new Matchlist[0]);

            }
        });


    }

    public void LoadImage(final String user, final ImageView img) throws IOException {
        /*for(Viewed v:viewed) {
            if (v.getNome().equals(user)) {
                img.setImageURI(v.getUri());
                return;
            }
        }*/

            //final File localFile = File.createTempFile(user, ".jpg");
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + user);
            Glide.with(img.getContext())
                    .load(storageRef)
                    .placeholder(R.drawable.utente)
                    .into(img);
            /*storageRef.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Uri imageData = Uri.fromFile(localFile);
                        img.setImageURI(imageData);
                        Viewed v=new Viewed(imageData, user);
                        viewed.add(v);
                    }
                }
            });*/
    }

    private void CaricaAmici(String user)
    {

        db.collection("utenti").document(user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ArrayList<String> nomi;
                        if (task.isSuccessful())
                        {
                            nomi= (ArrayList<String>) task.getResult().get("amici");

                            for(DataList d: lista)
                                {
                                    if(nomi.contains(d.getUsername()))
                                        listamici.add(d);
                                }
                            }
                        NewGameActivity.amici= listamici.toArray(new DataList[0]);
                    }
                });

    }

    private String ConvertData(Integer data){
        int giorno=data%100;
        int mese=(data-giorno)/100%100;
        int anno=(data-(mese*100)-giorno)/10000%10000;
        return giorno+"-"+mese+"-"+anno;
    }

    private void Ordina() {
        Matchlist m;
        int i=0;
        while(i<(MainActivity.giocate.length-1-i))
        {
            m=MainActivity.giocate[i];
            MainActivity.giocate[i]=MainActivity.giocate[MainActivity.giocate.length-1-i];
            MainActivity.giocate[MainActivity.giocate.length-1-i]=m;
            i++;
        }
    }

}
