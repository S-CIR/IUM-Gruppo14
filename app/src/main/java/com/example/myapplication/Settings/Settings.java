package com.example.myapplication.Settings;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class Settings extends Fragment {

    private TextView labelSensor, valueSensor, labelWeth, valueWeth;

    public Settings () {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        labelSensor = view.findViewById(R.id.labelSensor);
        valueSensor = view.findViewById(R.id.valueSensor);
        labelWeth = view.findViewById(R.id.labelWeth);
        valueWeth = view.findViewById(R.id.valueWeth);

        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        labelSensor.setTypeface(tf);
        labelSensor.setText(Html.fromHtml("\uF080"));
        labelWeth.setTypeface(tf);
        labelWeth.setText(Html.fromHtml("\uF0C2"));

        valueSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configSensorSetting();
            }
        });

        valueWeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configWethSetting();
            }
        });

        return view;
    }

    private void configSensorSetting(){
        FragmentManager fm = getFragmentManager();
        Fragment f = new DeviceSettings();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("settings");
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    private void configWethSetting(){
        FragmentManager fm = getFragmentManager();
        Fragment f = new WetherSettings();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("settings");
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

}
