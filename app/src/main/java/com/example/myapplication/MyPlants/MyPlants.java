package com.example.myapplication.MyPlants;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.AddPlant.AddPlant;
import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.Plant.ProfiloPiante;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MyPlants extends Fragment  {

    private DbManager dbManager;
    private ArrayList<Plant> piante;
    private SharedPreferences sp;
    private GridView list;
    private TextView add;

    public MyPlants() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DbManager(getContext());
        piante = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myplant_layout,container,false);

        list = view.findViewById(R.id.listPlant);
        add = view.findViewById(R.id.addPlant);

        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        add.setTypeface(tf);
        add.setText(Html.fromHtml("\uF067"));

        InsertPlantsInList();
        if (piante.size() != 0) {
            System.out.println("piante non vuoto onCreate");
            ListMyPlant adapter = new ListMyPlant(getContext(), piante);
            list.setAdapter(adapter);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configAddPlant();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant selected = piante.get(position);
                configPlantView(selected);
            }
        });

        return view;
    }

    @Override
    public void onStart (){
        super.onStart();
        InsertPlantsInList();

        if(piante.size()!=0) {
            ListMyPlant adapter = new ListMyPlant(getContext(), piante);
            list.setAdapter(adapter);
        }
    }

    private void configPlantView (Plant plant) {
        FragmentManager fm = getFragmentManager();
        Fragment f = new ProfiloPiante(plant.getID());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    private void configAddPlant () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new AddPlant();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    private void InsertPlantsInList (){
            sp= getContext().getSharedPreferences("Profilo", 0);
            String mail = sp.getString("Mail","User Mail");
            Cursor c = dbManager.getAllPlants();
            piante.clear();

            if(c.moveToFirst()){
                do {
                    if(c.getString(10).equals(mail)) {
                        byte[] byteArray = c.getBlob(1);
                        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        int i = Integer.parseInt(c.getString(0));
                        piante.add(new Plant(i, bm, c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8)));
                    }
                }while (c.moveToNext());
            }

    }

}
