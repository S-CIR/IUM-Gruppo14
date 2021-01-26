package com.example.myapplication.AddPlant;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SelectZona extends Fragment {

    private SharedPreferences sp;
    private SharedPreferences.Editor myEdit;

    private Plant plant;

    private GridView zone;
    private TextView add;

    private ArrayList <String> lista_zone;
    private BaseAdapter adapter;

    private int last = 0;

    public SelectZona(Plant p) {
        this.plant = p;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getContext().getSharedPreferences("Zone", 0);
        myEdit = sp.edit();

        last = sp.getInt("last",0);

        lista_zone = new ArrayList<>();
        fillArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectionzone_layout, container, false);
        zone = view.findViewById(R.id.gridZone);
        last = sp.getInt("last",0);
        add = view.findViewById(R.id.addZone);
        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        add.setTypeface(tf);
        add.setText(Html.fromHtml("\uF067"));

        setUpAdapter();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = last + 1;
                configNewZone("Zona: " + next);
                last = sp.getInt("last",0);
                fillArray();
                setUpAdapter();
            }
        });

        zone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = lista_zone.get(position);
                plant.setZona(selected);

                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
            }
        });

        return view;
    }

    @Override

    public void onResume() {
        fillArray ();
        setUpAdapter ();
        super.onResume();
    }

    private void configNewZone (String zone){
        FragmentManager fm = getFragmentManager();
        Fragment f = new NewZone(zone);
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("zoneSelection");
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    private void fillArray (){
        lista_zone.clear();
        last = sp.getInt("last",0);
        for(int i=1; i<=last; i++){
            String zona = "Zona: "+i;
            lista_zone.add(zona);
        }
    }

    private void setUpAdapter () {
        Zona adapter = new Zona(getContext(),lista_zone);
        zone.setAdapter(adapter);
    }

}
