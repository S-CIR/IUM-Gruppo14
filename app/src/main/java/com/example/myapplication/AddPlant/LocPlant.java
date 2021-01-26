package com.example.myapplication.AddPlant;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.ArrayList;

public class LocPlant extends Fragment {

    private View view;

    private DbManager dbManager;

    private ArrayList<Plant> piante;

    private MapView mapView;
    private MapController map;
    private Button conf;

    private Plant plant;

    private Double latitude;
    private Double longitude;

    public LocPlant(Plant p) {
        this.plant = p;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getContext());

        org.osmdroid.config.IConfigurationProvider osmConf = org.osmdroid.config.Configuration.getInstance();
        File basePath = new File(getContext().getCacheDir().getAbsolutePath(), "osmdroid");
        osmConf.setOsmdroidBasePath(basePath);
        File tileCache = new File(osmConf.getOsmdroidBasePath().getAbsolutePath(), "tile");
        osmConf.setOsmdroidTileCache(tileCache);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        SharedPreferences sp = getContext().getSharedPreferences("location", 0);
        String lat = sp.getString("lat","0.0");
        String lon = sp.getString("lon","0.0");
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lon);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.locplant_layout, container, false);

        conf = (Button) view.findViewById(R.id.confLocation);
        mapView = (MapView) view.findViewById(R.id.mapviewPlant);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);
        map = (MapController) mapView.getController();
        map.setZoom(20);
        GeoPoint gPt = new GeoPoint(latitude, longitude);
        map.setCenter(gPt);
        Marker start = new Marker(mapView);
        start.setIcon(getResources().getDrawable(R.mipmap.map_marker, null));

        start.setPosition(gPt);
        start.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        start.setTitle("è qui".toUpperCase());
        mapView.getOverlays().add(start);

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coord = latitude + " " + longitude;
                plant.setCoordinates(coord);
                configAddPlant();
            }
        });

        return view;
    }

    private void configAddPlant () {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
    }

}
