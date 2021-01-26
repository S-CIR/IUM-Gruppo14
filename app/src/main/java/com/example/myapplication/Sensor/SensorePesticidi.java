package com.example.myapplication.Sensor;

import java.util.Random;

public class SensorePesticidi {
    private String[] data;
    private String zona;

    private String [] nature = new String[]{"Sapone Vegetale", "Macerato di Equiseto", "Bicarbonato","Ortica","Quassia Amara","Olio di Pino","Azardiractina"};
    private String [] chimico = new String[] {"Acephate", "Bitertanolo", "Cabaril","Ciprodinil","Diazinone","Eptaclor","Feranimol"};

    public SensorePesticidi(String zona) {
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

    public String pesticida(){
        Random r = new Random();
        return r.nextInt(40)+"";
    }
}
