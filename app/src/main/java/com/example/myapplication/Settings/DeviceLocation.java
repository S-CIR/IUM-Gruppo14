package com.example.myapplication.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.DB.DbManager;
import com.example.myapplication.R;
import com.example.myapplication.Zone.ZoneManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.io.File;

public class DeviceLocation extends Fragment {

    private View view;
    private DbManager dbManager;
    private MapView mapView;
    private MapController map;

    private Button start;

    private ImageView pos;

    private Double latitude;
    private Double longitude;
    private Device device;


    public DeviceLocation(Device d){
        this.device=d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getContext());

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
        view = inflater.inflate(R.layout.device_location_layout, container, false);

        start = view.findViewById(R.id.startLoc);

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);
        pos = view.findViewById(R.id.dev);
        map = (MapController) mapView.getController();
        map.setZoom(10);
        GeoPoint gPt = new GeoPoint(latitude, longitude);
        map.setCenter(gPt);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageVisible();
                start.setText("Back");
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getFragmentManager();
                        fm.popBackStackImmediate();
                    }
                });
            }
        });

        return view;
    }

    public void setImageVisible(){
        pos.setVisibility(View.VISIBLE);
        map.setZoom(15);
    }
}
