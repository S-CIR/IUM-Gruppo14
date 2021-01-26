package com.example.myapplication.Settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Home;
import com.example.myapplication.R;


public class DeviceProfile extends Fragment {

    private TextView title,brand,type;
    private Button loc ,del;
    private ImageView image;

    private DbManager dbManager;
    private Device device;
    private View view;

    public DeviceProfile(Device d){
            this.device=d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.device_profile_layout, container, false);

        title = view.findViewById(R.id.devTitle);
        brand = view.findViewById(R.id.devBrand);
        type = view.findViewById(R.id.devType);
        loc = view.findViewById(R.id.devLocation);
        del = view.findViewById(R.id.devDel);
        image = view.findViewById(R.id.devFoto);

        title.setText(device.getIdDevice());
        brand.setText(device.getMarchio());
        type.setText(device.getType());

        image.setImageBitmap(device.getFoto());

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configLocation();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delPlant();
            }
        });

        return view;
    }

    public void delPlant (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sicuro di voler proseguire?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean resp = dbManager.delDevice(device.getIdDevice());
                if(resp){
                    Toast.makeText(getContext(),"Device Eliminato",Toast.LENGTH_LONG);
                }

                configDevice();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.create();
        builder.show();

    }
    private void configDevice () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new DeviceSettings();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }
    private void configLocation () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new DeviceLocation(device);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.addToBackStack("locDev");
        ft.commit();
    }
}
