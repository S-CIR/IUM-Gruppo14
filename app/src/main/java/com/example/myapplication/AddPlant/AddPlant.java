package com.example.myapplication.AddPlant;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.R;
import com.example.myapplication.Home.Home;
import com.example.myapplication.Home.Plant;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPlant extends Fragment {

    private DbManager dbManager;
    private SharedPreferences sharedPreferences;
    private ImageView image;
    private EditText nome, tipo;
    private TextView zona, date;
    private Button loc, conf;

    private Bitmap b;

    private Plant p;

    private static final int CAPTURE_PICCODE=989;

    private  DatePickerDialog picker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences= getContext().getSharedPreferences("Profilo", Context.MODE_PRIVATE);
        dbManager = new DbManager(getContext());
        p = new Plant();
        setDefImage();
        p.setZona("Zona");
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.addplant_layout,view,false);
        image = (ImageView) v.findViewById(R.id.plantimg);
        nome = (EditText) v.findViewById(R.id.nomepianta);
        tipo = (EditText) v.findViewById(R.id.tipopianta);
        date = (TextView) v.findViewById(R.id.data);
        zona = (TextView) v.findViewById(R.id.zona);
        loc =  (Button) v.findViewById(R.id.plantloc);
        conf  =  (Button)v.findViewById(R.id.plantconf);



        image.setImageBitmap(p.getFoto());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(v.getContext() ,Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED)
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,CAPTURE_PICCODE);
                }
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configLocPlant();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configSetZona();
            }
        });


        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!zona.getText().equals("Zona")) {
                    p.setName(nome.getText().toString());
                    p.setTipo(tipo.getText().toString());
                    if (date.getText().toString().equals("Data di Semina")) {
                        String s = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        p.setData("Semina: " + s);
                    } else
                        p.setData("Semina: " + date.getText().toString());

                    if (!"Zona".equals(zona.getText().toString()))
                        p.setZona(zona.getText().toString());

                    if (checkPlant(p)) {
                        String mail = sharedPreferences.getString("Mail", null);
                        if (mail != null) {
                            dbManager.addPlant(p, mail);
                            FragmentManager fm = getFragmentManager();
                            Fragment f = new Home();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.MainFrameLayout, f);
                            ft.commit();
                        }

                    }
                } else
                    Toast.makeText(getContext(), "Devi inserire prima una zona per poter continuare", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    private boolean checkPlant (Plant p){
        if(p.getName().equals("none"))
            return false;
        else if(p.getTipo().equals("none"))
            return false;
        else if(p.getData().equals("none"))
            return false;
        else
            return true;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CAPTURE_PICCODE && resultCode== Activity.RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray  = stream.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            this.b=bmp;
            System.out.println(bmp.getHeight() + " " + bmp.getWidth());
            image.setImageBitmap(b);
            p.setFoto(b);
        }
    }

    private void setDefImage (){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.default_plant_icon);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray  = stream.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        this.b=bmp;
        p.setFoto(b);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURE_PICCODE);
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!p.getZona().equals("Zona") ||!p.getZona().equals("1") )
            zona.setText(p.getZona());

        if(!p.getCoordinates().equals("none"))
            loc.setText("Pianta Localizzata");
    }

    private void configLocPlant () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new LocPlant(this.p);
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("adding");
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    private void configSetZona () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new SelectZona(this.p);
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("zona");
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }



}
