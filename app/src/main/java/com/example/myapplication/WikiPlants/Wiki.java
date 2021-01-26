package com.example.myapplication.WikiPlants;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Wiki extends Fragment {

    LinearLayout arom, fruit, vege;
    TextView btn;
    EditText search;
    View v;

    private ArrayList<WikiPlant> wiki;

    public Wiki() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wiki = new ArrayList<>();
        WikiPlant.doRetrieveInfo(wiki);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.wikiplants_layout, view, false);

        arom = v.findViewById(R.id.arom);
        fruit  = v.findViewById(R.id.fruit);
        vege = v.findViewById(R.id.vege);

        btn = v.findViewById(R.id.searchBtn);
        search = v.findViewById(R.id.searchBar);

        Typeface tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/fa-solid-900.ttf");
        btn.setTypeface(tf);
        btn.setText("\uF002");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = search.getText().toString();
                ArrayList<WikiPlant> result = new ArrayList<>();
                if(!key.equals("")){
                    for(WikiPlant wp: wiki) {
                        if (Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(wp.getName()).find() ||
                            Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(wp.getType()).find() ||
                            Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(wp.getSeeding()).find() ||
                            Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(wp.getDescription()).find())
                                result.add(wp);
                    }
                    int size = result.size();

                    if(size==0){
                        Toast.makeText(getContext(),"La tua ricerca non ha prodotto risultati.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(size==1){
                        configPlantInfo(result.get(0));
                    }
                    else{
                        configWikiSelect(key,result);
                    }
                }
            }
        });

        arom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<WikiPlant> result = new ArrayList<>();

                for(WikiPlant wp: wiki){
                    if(wp.getType().equals("Aromatica"))
                        result.add(wp);
                }

                configWikiSelect("Aromatica",result);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<WikiPlant> result = new ArrayList<>();

                for(WikiPlant wp: wiki){
                    if(wp.getType().equals("Frutto"))
                        result.add(wp);
                }

                configWikiSelect("Frutto",result);

            }
        });

        vege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<WikiPlant> result = new ArrayList<>();

                for(WikiPlant wp: wiki){
                    if(wp.getType().equals("Vegetali"))
                        result.add(wp);
                }

                configWikiSelect("Vegetali",result);

            }
        });

        return v;
    }

    private void configPlantInfo (WikiPlant plant) {
        FragmentManager fm = getFragmentManager();
        Fragment f = new PlantInfo(plant);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.addToBackStack("Wiki");
        ft.commit();
    }

    private void configWikiSelect (String key, ArrayList<WikiPlant> list) {
        FragmentManager fm = getFragmentManager();
        Fragment f = new WikiSelect(key,list);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.addToBackStack("Wiki");
        ft.commit();
    }



}
