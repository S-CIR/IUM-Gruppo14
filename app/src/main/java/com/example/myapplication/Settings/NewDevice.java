package com.example.myapplication.Settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;

public class NewDevice extends Fragment {

    private DbManager dbManager;
    private SharedPreferences sp;
    private Switch switchBtn;
    private ImageView image;
    private EditText code, nome, tipo;
    private Button loc, conf;
    private String typology;
    private Bitmap b;
    private Device d;

    private static final int CAPTURE_PICCODE=989;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getContext());
        d= new Device();
        setDefImage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_device_layout,container,false);
        image = (ImageView) v.findViewById(R.id.dispimg);
        code=  (EditText) v.findViewById(R.id.code);
        nome = (EditText) v.findViewById(R.id.marchio);
        tipo = (EditText) v.findViewById(R.id.type);
        switchBtn = v.findViewById(R.id.switchBtn);
        loc =  (Button) v.findViewById(R.id.locDevice);
        conf  =  (Button)v.findViewById(R.id.confDevice);
        typology ="drone";
        sp= getContext().getSharedPreferences("Profilo",0);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    typology ="drone";
                else
                    typology ="sensore";
            }
        });


        image.setImageBitmap(d.getFoto());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(v.getContext() , Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED)
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,CAPTURE_PICCODE);
                }
            }
        });

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.setIdDevice(code.getText().toString());
                d.setMarchio(nome.getText().toString());
                d.setTypology(typology);
                d.setType(tipo.getText().toString());

                if(checkDevice(d)){
                    String mail= sp.getString("Mail","User Mail");
                    dbManager.addDevice(d,mail);
                    FragmentManager fm = getFragmentManager();
                    Fragment f = new DeviceSettings();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.MainFrameLayout,f);
                    ft.commit();
                }
            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device temp = new Device( code.getText().toString(), d.getFoto(), typology,nome.getText().toString(),tipo.getText().toString() );
                configLocation(temp);
            }
        });

        return v;
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
            d.setFoto(b);
        }
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

    private void setDefImage (){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.default_device_icon);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray  = stream.toByteArray();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        this.b=bmp;
        d.setFoto(b);
    }

    private boolean checkDevice (Device d){
        if(d.getIdDevice().equals("none"))
            return false;
        else if(d.getMarchio().equals("none"))
            return false;
        else if(d.getType().equals("none"))
            return false;
        else
            return true;
    }
    private void configLocation (Device temp) {
        FragmentManager fm = getFragmentManager();
        Fragment f = new DeviceLocation(temp);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.addToBackStack("locDev");
        ft.commit();
    }


}
