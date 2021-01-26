package com.example.myapplication.Settings;

import android.graphics.Bitmap;

public class Device {

    private String idDevice;
    private Bitmap foto;
    private String typology;
    private String marchio;
    private String type;

    public Device() { }

    public Device(String id, Bitmap foto,String typology, String marchio, String type) {
        this.idDevice = id;
        this.foto=foto;
        this.typology =typology;
        this.marchio = marchio;
        this.type = type;
    }



    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getTypology() {
        return typology;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public String getMarchio() {
        return marchio;
    }

    public void setMarchio(String marchio) {
        this.marchio = marchio;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
