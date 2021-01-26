package com.example.myapplication.AddPlant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;

public class NewZone extends Fragment {

    private SharedPreferences sp;
    private SharedPreferences.Editor myEdit;

    private String name;

    private TextView title;
    private Switch switchBtn;
    private TextView textSpec;
    private Button conf;

    private String chimico = "I concimi chimici hanno azione rapida e restano sul suolo per breve tempo.\n" +
            "I pesticidi chimici allontanano gli insetti nocivi dalle nostre piante ma potrebbero essere pericolosi o dannosi per l'uomo o per gli altri animali.";

    private String naturale = "I concimi naturali hanno azione lenta e restano sul suolo per lungo tempo.\n" +
            "I pesticidi naturali allontanano gli insetti nocivi dalle nostre piante senza però diventare pericolosi o dannosi per l'uomo o per gli altri animali.";

    public NewZone (String name){
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getContext().getSharedPreferences("Zone", 0);
        myEdit = sp.edit();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_zone_layout,container,false);

        title = view.findViewById(R.id.zoneName);
        title.setText(name);

        switchBtn = view.findViewById(R.id.switchBtn);
        textSpec = view.findViewById(R.id.textSpec);
        conf = view.findViewById(R.id.conf);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    textSpec.setText(chimico);
                else
                    textSpec.setText(naturale);
            }
        });

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String temp;
                if(switchBtn.isChecked()){
                    temp = switchBtn.getTextOn().toString();
                }
                else{
                    temp = switchBtn.getTextOff().toString();
                }
                int last = sp.getInt("last",0);
                last++;
                myEdit.putInt("last",last);
                myEdit.apply();

                setZoneType(temp);
                configSelectZona();
            }
        });
        return view;
    }

    private void setZoneType (String type) {
        SharedPreferences sp = getActivity().getSharedPreferences("Zone", 0);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString(name, type);
        myEdit.apply();
    }

    private void configSelectZona (){
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
    }

}
