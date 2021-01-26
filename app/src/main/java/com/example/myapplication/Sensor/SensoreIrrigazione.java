package com.example.myapplication.Sensor;

import java.util.Random;

public class SensoreIrrigazione {
    int[] data;

    public SensoreIrrigazione () {
        data = new int[2];
    }

    public int[] genData (){
        Random r = new Random();
        data[0] = r.nextInt(70)+30;
        data[1] = r.nextInt(100);
        return data;
    }

    public int genNec () {
        Random r = new Random();
        return r.nextInt(30);
    }



}
