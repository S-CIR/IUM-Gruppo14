package com.example.myapplication.Monitoraggio;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;
import com.example.myapplication.Zone.ZoneManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.io.File;
import java.util.ArrayList;

public class Monitoraggio extends Fragment {
    private View view;

    private MapView mapView;
    private MapController map;
    private Button info;
    private Double latitude;
    private Double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getContext().getSharedPreferences("location", 0);
        latitude = Double.parseDouble(sp.getString("lat", "0.0"));
        longitude = Double.parseDouble(sp.getString("lon", "0.0"));

        org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
        File basePath = new File(getContext().getCacheDir().getAbsolutePath(), "osmdroid");
        osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tile");
        osmConf.setOsmdroidTileCache(tileCache);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.monitoraggio_layout, container, false);

        info = view.findViewById(R.id.info);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);
        map = (MapController) mapView.getController();
        map.setZoom(18);
        GeoPoint gPt = new GeoPoint(latitude, longitude);
        map.setCenter(gPt);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configInfoZone (3);
            }
        });

        return view;
    }


    private void configInfoZone (int selected){
        FragmentManager fm = getFragmentManager();
        Fragment f = new ZoneManager(selected);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.addToBackStack("ZoneSelected:"+selected);
        ft.commit();
    }
}
