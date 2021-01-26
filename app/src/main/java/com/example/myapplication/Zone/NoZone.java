package com.example.myapplication.Zone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.AddPlant.AddPlant;
import com.example.myapplication.AddPlant.NewZone;
import com.example.myapplication.Home.Home;
import com.example.myapplication.R;

public class NoZone extends Fragment {

    private Button newZone,home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nozone_layout, container, false);
        newZone= v.findViewById(R.id.newZone);
        home = v.findViewById(R.id.back);

        newZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configNewZone();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configHome();
            }
        });
        return v;
    }

    private void configNewZone() {
        FragmentManager fm = getFragmentManager();
        Fragment f = new AddPlant();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }
    private void configHome () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new Home();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }
}
