package com.example.myapplication.Settings;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.Plant.ProfiloPiante;
import com.example.myapplication.R;

import java.util.ArrayList;


public class DeviceSettings extends Fragment {

    private TextView sensori, droni;
    private GridView grid;
    private Button add;

    private ArrayList<Device> device;
    private DbManager dbManager;

    public DeviceSettings() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        device = new ArrayList<>();
        dbManager = new DbManager(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.device_settings_layout, container, false);

        sensori = v.findViewById(R.id.sensori);
        droni = v.findViewById(R.id.droni);
        grid = v.findViewById(R.id.grid);
        add = v.findViewById(R.id.addDisp);
        sensori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensori.setBackgroundColor(Color.rgb(233,255,89));
                droni.setBackgroundColor(Color.rgb(112,112,112));
                device.clear();
                insertByType("sensore");
                if(device.size()!=0) {
                    DeviceElem adapter = new DeviceElem(getContext(), device);
                    grid.setAdapter(adapter);
                }
                else{
                    DeviceElem adapter = new DeviceElem(getContext(), device);
                    grid.setAdapter(adapter);
                    Toast.makeText(getContext(), "Al momento non hai Sensori collegati, inseriscine uno per poterlo visualizzare", Toast.LENGTH_SHORT).show();
                }
            }
        });

        droni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droni.setBackgroundColor(Color.rgb(233,255,89));
                sensori.setBackgroundColor(Color.rgb(112,112,112));
                device.clear();
                insertByType("drone");
                if(device.size()!=0) {
                    DeviceElem adapter = new DeviceElem(getContext(), device);
                    grid.setAdapter(adapter);
                }
                else{
                    DeviceElem adapter = new DeviceElem(getContext(), device);
                    grid.setAdapter(adapter);
                    Toast.makeText(getContext(), "Al momento non hai Droni collegati, inseriscine uno per poterlo visualizzare", Toast.LENGTH_SHORT).show();
                }
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device device = (Device) grid.getItemAtPosition(position);
                confidDeviceView(device);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configNewDevice();
            }
        });

        return v;
    }

    private void insertByType(String type) {

        Cursor c = dbManager.getByType();
        device.clear();

        if (c.moveToFirst()) {
            do {
                byte[] byteArray = c.getBlob(1);
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                if(c.getString(2).equals(type))
                device.add(new Device(c.getString(0), bm, type, c.getString(3), c.getString(4)));

            } while (c.moveToNext());
        }
    }

    private void configNewDevice(){
        FragmentManager fm = getFragmentManager();
        Fragment f = new NewDevice();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        device.clear();
        insertByType("drone");
        if(device.size()!=0) {
            DeviceElem adapter = new DeviceElem(getContext(), device);
            grid.setAdapter(adapter);
        }
        else{
            Toast.makeText(getContext(), "Al momento non hai Droni collegati, inseriscine uno per poterlo visualizzare", Toast.LENGTH_SHORT).show();
        }
    }

    private void confidDeviceView (Device device) {
        FragmentManager fm = getFragmentManager();
        Fragment f = new DeviceProfile(device);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }
}
