package com.example.myapplication.Sensor;

import java.util.Random;

public class SensoreConcime {
    private String[] data;
    private String zona;

    private String [] nature = new String[]{"Macerato Vegetale", "Guano", "Stallatico","Cornunghia","Pollina","Compost","Humus di Lombrico"};
    private String [] chimico = new String[] {"Nitrato d'Ammonio", "Cianammide di Calcio", "Superfosfato d'Ammonio","Cloruro di Potassio","Nitrato di Potassio","Fosfato di Potassio","Fosfato d'Ammonio"};
    public SensoreConcime(String zona) {
        this.zona = zona;
        data = new String[3];
    }


    public String[] genData (){
        Random r = new Random();
        int i = r.nextInt(6);
        data[0]=zona;
        if(zona.equals("Naturale")){
            data[1]=nature[i];
        }else{
            data[1]=chimico[i];
        }
        data[2] = r.nextInt(100)+"";
        return data;
    }

    public String concima(){
        Random r = new Random();
        return r.nextInt(40)+"";
    }
}
