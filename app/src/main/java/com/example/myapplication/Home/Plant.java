package com.example.myapplication.Home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Plant {

    private int dbID;

    private Bitmap foto;
    private String name;
    private String tipo;
    private String data; //GG-MM-AAAA
    private String zona;
    private String irrigazione;
    private String concimazione;
    private String pesticidi;
    private String coordinates;

    public Plant (){
        this.name="none";
        this.tipo="none";
        this.data="none";
        this.zona="1";
        this.irrigazione="none";
        this.concimazione="none";
        this.pesticidi="none";
        this.coordinates="none";
    }

    public Plant (Bitmap f, String n, String t, String d){
        this.foto=f;
        this.name=n;
        this.tipo=t;
        this.data=d;
        this.zona="none";
        this.irrigazione="none";
        this.concimazione="none";
        this.pesticidi="none";
        this.coordinates="none";
    }

    public Plant (int id, Bitmap f, String n, String t, String d, String z, String i, String c, String p){
        this.dbID=id;
        this.foto=f;
        this.name=n;
        this.tipo=t;
        this.data=d;
        this.zona=z;
        this.irrigazione=i;
        this.concimazione=c;
        this.pesticidi=p;
        this.coordinates="none";
    }

    public Plant (Bitmap f, String n, String t, String d, String i, String c, String p, String coo){
        this.foto=f;
        this.name=n;
        this.tipo=t;
        this.data=d;
        this.zona="none";
        this.irrigazione=i;
        this.concimazione=c;
        this.pesticidi=p;
        this.coordinates=coo;
    }

    public Plant (int id,Bitmap f, String n, String t, String d){
        this.dbID=id;
        this.foto=f;
        this.name=n;
        this.tipo=t;
        this.data=d;
        this.zona="none";
        this.irrigazione="none";
        this.concimazione="none";
        this.pesticidi="none";
        this.coordinates="none";
    }

    public String getRaccolta (){
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal= Calendar.getInstance();
        String raccolta="N/A";
        try {
            cal.setTime(dateFormat.parse(data.substring(8)));
            cal.add(Calendar.DATE,50);
            raccolta="Raccolta: "+dateFormat.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return raccolta;
    }

    public void setID (int ID){
        this.dbID=ID;
    }

    public int getID (){
        return this.dbID;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data; }

    public String getZona() {
        return zona; }

    public void setZona(String zona) {
        this.zona = zona; }

    public String toString (){
        return this.name+" "+this.tipo+" "+this.data+"\n"; }

    public String getIrrigazione() { return irrigazione; }

    public void setIrrigazione(String irrigazione) {
        this.irrigazione = irrigazione; }

    public String getConcimazione() {
        return concimazione; }

    public void setConcimazione(String concimazione) {
        this.concimazione = concimazione; }

    public String getPesticidi() {
        return pesticidi; }

    public void setPesticidi(String pesticidi) {
        this.pesticidi = pesticidi; }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
