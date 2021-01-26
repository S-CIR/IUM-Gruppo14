package com.example.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapplication.Home.Plant;
import com.example.myapplication.Settings.Device;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DbManager extends SQLiteOpenHelper {

    private static int counter;

    private static final String DATABASE_NAME="PlantsDb";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Piante";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_FOTO="foto";
    private static final String COLUMN_TITLE="title";
    private static final String COLUMN_TYPE="type";
    private static final String COLUMN_DATE="date";
    private static final String COLUMN_ZONE="zona";
    private static final String COLUMN_IRRIG="irrigazione";
    private static final String COLUMN_CONCIM="concimazione";
    private static final String COLUMN_PEST="pesticidi";
    private static final String COLUMN_COORD="coordinate";
    private static final String COLUMN_MAIL="mail";

    private static final String TABLE_NAME1="Device";
    private static final String COLUMN_DEVICE="idDevice";
    private static final String COLUMN_FOTO1="fotoDevice";
    private static final String COLUMN_TIPOLOGY="tipologyDevice";
    private static final String COLUMN_BRAND="brandDevice";
    private static final String COLUMN_TYPE1="typeDevice";
    private static final String COLUMN_MAIL1="mail";

    private static final String TABLE_NAME2="Profilo";
    private static final String COLUMN_N0ME="nome";
    private static final String COLUMN_COGNOME="cognome";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_USER="user";
    private static final String COLUMN_PSW="psw";


    public DbManager (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase){
        counter=1;
        String sql= "CREATE TABLE " + TABLE_NAME + " (\n" +
                "  " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  " + COLUMN_FOTO + " blob NOT NULL,\n" +
                "  " + COLUMN_TITLE + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_TYPE + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_DATE + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_ZONE + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_IRRIG + " varchar(200),\n" +
                "  " + COLUMN_CONCIM + " varchar(200),\n" +
                "  " + COLUMN_PEST + " varchar(200),\n" +
                "  " + COLUMN_COORD + " varchar(200),\n" +
                "  " + COLUMN_MAIL + " varchar(200) NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sql);

        counter=2;
        String sql1= "CREATE TABLE " + TABLE_NAME1 + " (\n" +
                "  " + COLUMN_DEVICE + " varchar(200) PRIMARY KEY ,\n" +
                "  " + COLUMN_FOTO1 + " blob NOT NULL,\n" +
                "  " + COLUMN_TIPOLOGY + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_BRAND + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_TYPE1 + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_MAIL1 + " varchar(200) NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sql1);

        counter=3;
        String sql2= "CREATE TABLE " + TABLE_NAME2 + " (\n" +
                "  " + COLUMN_N0ME + " varchar(200) PRIMARY KEY ,\n" +
                "  " + COLUMN_COGNOME + " blob NOT NULL,\n" +
                "  " + COLUMN_EMAIL + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_USER + " varchar(200) NOT NULL,\n" +
                "  " + COLUMN_PSW + " varchar(200) NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1){
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addPlant (Plant p, String mail){
        ContentValues contentValues = new ContentValues();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bmap = p.getFoto();
        bmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray  = stream.toByteArray();
        contentValues.put(COLUMN_FOTO,byteArray);
        contentValues.put(COLUMN_TITLE,p.getName());
        contentValues.put(COLUMN_TYPE,p.getTipo());
        contentValues.put(COLUMN_DATE,p.getData());
        contentValues.put(COLUMN_ZONE,p.getZona());
        contentValues.put(COLUMN_IRRIG,p.getData());
        contentValues.put(COLUMN_CONCIM,p.getConcimazione());
        contentValues.put(COLUMN_PEST,p.getPesticidi());
        contentValues.put(COLUMN_COORD,p.getCoordinates());
        contentValues.put(COLUMN_MAIL,mail);
        SQLiteDatabase db = getWritableDatabase();

        return db.insert(TABLE_NAME,null,contentValues) != -1;

    }

    public boolean addDevice (Device c, String mail){
        ContentValues contentValues = new ContentValues();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bmap = c.getFoto();
        bmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray  = stream.toByteArray();

        contentValues.put(COLUMN_DEVICE,c.getIdDevice());
        contentValues.put(COLUMN_FOTO1,byteArray);
        contentValues.put(COLUMN_TIPOLOGY,c.getTypology());
        contentValues.put(COLUMN_BRAND,c.getMarchio());
        contentValues.put(COLUMN_TYPE1,c.getType());
        contentValues.put(COLUMN_MAIL1,mail);

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(TABLE_NAME1,null,contentValues) != -1;

    }

    public boolean addUser(String nome, String cognome, String mail, String user, String psw){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_N0ME,nome);
        contentValues.put(COLUMN_COGNOME,cognome);
        contentValues.put(COLUMN_EMAIL,mail);
        contentValues.put(COLUMN_USER,user);
        contentValues.put(COLUMN_PSW,psw);

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(TABLE_NAME2,null,contentValues) != -1;
    }
    public Cursor getByZone (String zona){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ZONE + " = " + zona, null);
    }

    public Cursor getByType (){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
    }

    public Cursor getUser(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);

    }

    public Cursor getAllPlants (){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Plant getPlant (int id) {
        SQLiteDatabase db = getReadableDatabase();
        return retPlant(db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id, null));
    }

    public boolean delPlant (int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(TABLE_NAME,COLUMN_ID + "=?", new String[] {String.valueOf(id)})==1;
    }

    public boolean delDevice (String id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(TABLE_NAME1, COLUMN_DEVICE + "=?", new String[] {id})== 1;
    }

    public boolean setIrrig (int id, String last) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IRRIG, last);
        return (db.update(TABLE_NAME,cv,  COLUMN_ID + " = "+ id, null))==1;
    }

    public boolean setConc (int id, String last) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONCIM, last);
        return (db.update(TABLE_NAME,cv,  COLUMN_ID + " = "+ id, null))==1;
    }

    public boolean setPest (int id, String last) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PEST, last);
        return (db.update(TABLE_NAME,cv,  COLUMN_ID + " = "+ id, null))==1;
    }

    private Plant retPlant (Cursor c){
        Plant p = null;
        if(c.moveToFirst()){
            do{
                byte[] byteArray = c.getBlob(1);
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                int i=Integer.parseInt(c.getString(0));
                p = new Plant(i,bm,c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8));
            }while(c.moveToNext());
        }
        return p;
    }

}
