package com.example.myapplication.Irrigazione;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;


public class SelfIrrigazione extends Fragment {

    private View view;

    private DbManager dbManager;

    private ArrayList<Plant> piante;

    private MapView mapView;
    private MapController map;

    private Button startIrrig;

    private TextView water;

    private Double latitude;
    private Double longitude;

    public SelfIrrigazione(ArrayList<Plant> arrayList) {
        piante = arrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        view = inflater.inflate(R.layout.selfirrigazione_layout, container, false);

        startIrrig = view.findViewById(R.id.startIrrig);
        water = view.findViewById(R.id.Water);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        water.setTypeface(tf);
        water.setText(Html.fromHtml("\uF043"));
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);
        map = (MapController) mapView.getController();
        map.setZoom(10);
        GeoPoint gPt = new GeoPoint(latitude, longitude);
        map.setCenter(gPt);

        startIrrig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setImageVisible();
                    startIrrig.setText("Back");
                    startIrrig.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager fm = getFragmentManager();
                            Fragment f= new ZoneManager(0);
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.MainFrameLayout,f);
                            ft.commit();
                        }
                    });
            }
        });

        return view;
    }

    public void setImageVisible(){
        water.setVisibility(View.VISIBLE);
        map.setZoom(20);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void startIrrig (){
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        for (Plant p: piante){
            dbManager.setIrrig(p.getID(),today);
        }
    }

}
