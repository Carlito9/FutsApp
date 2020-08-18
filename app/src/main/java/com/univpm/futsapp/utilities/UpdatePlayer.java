package com.univpm.futsapp.utilities;

import java.util.Map;

public class UpdatePlayer {
    UpdatePlayer(Map<String,Object> giocatore, int esito, int gol, double voto ){
        voto=((Double)giocatore.get("media voto")*(Integer)giocatore.get("partite giocate")+voto);
        int rating;
        giocatore.put("partite giocate",(Integer)giocatore.get("partite giocate")+1);
        switch (esito) {
            case 1: {
                giocatore.put("vittorie",(Integer)giocatore.get("vittorie")+1);
                break;
            }
            case 0: {
                giocatore.put("pareggi",(Integer)giocatore.get("pareggi")+1);
                break;
            }
            case 2: {
                giocatore.put("sconfitte",(Integer)giocatore.get("sconfitte")+1);
                break;
            }
        }
        giocatore.put("gol fatti",(Integer)giocatore.get("gol fatti")+gol);
        voto=voto/(Integer)giocatore.get("partite giocate");
        giocatore.put("media voto",voto);
        voto=voto*10;
        if(voto<60)
            rating=(int) voto;
        else if(voto<70.5)
            rating= (int) (60+(voto-60)*2);
        else if(voto<90.5)
            rating=(int) (80+(voto-70));
        else rating=100;
        giocatore.put("rating",rating);


    }


}
