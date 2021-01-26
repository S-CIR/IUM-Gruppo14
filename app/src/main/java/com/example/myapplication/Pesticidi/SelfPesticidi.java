package com.example.myapplication.Pesticidi;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SelfPesticidi extends Fragment {

    private View view;

    private DbManager dbManager;

    private ArrayList<Plant> piante;

    private MapView mapView;
    private MapController map;

    private Button start;

    private TextView conc;

    private Double latitude;
    private Double longitude;


    public SelfPesticidi(ArrayList<Plant> arrayList) {
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
        view = inflater.inflate(R.layout.selfpesticidi_layout, container, false);

        start = view.findViewById(R.id.startIrrig);
        conc = view.findViewById(R.id.Conc);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        conc.setTypeface(tf);
        conc.setText(Html.fromHtml("\uE05C"));
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);
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
                        Fragment f= new ZoneManager(2);
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
        conc.setVisibility(View.VISIBLE);
        map.setZoom(18);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
