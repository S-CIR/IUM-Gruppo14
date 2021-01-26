package com.example.myapplication.Settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Home.Home;
import com.example.myapplication.Menu;
import com.example.myapplication.Pesticidi.PestElem;
import com.example.myapplication.R;


public class WetherSettings extends Fragment {
    private LinearLayout layout;
    private SharedPreferences sp;
    private TextView city, up, temp, det, hum, pres, ico, wind;
    private Button back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getContext().getSharedPreferences("Tempo", 0);

    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_settings_layout,container,false);
        layout=view.findViewById(R.id.layWeath);
        layout.setVisibility(View.INVISIBLE);

        ico=view.findViewById(R.id.ico);
        city=view.findViewById(R.id.city);
        up=view.findViewById(R.id.upda);
        det=view.findViewById(R.id.det);
        temp=view.findViewById(R.id.temp);
        wind=view.findViewById(R.id.wind);
        hum=view.findViewById(R.id.hum);
        pres=view.findViewById(R.id.pres);
        back = view.findViewById(R.id.indietro);

        String provider = android.provider.Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Le informazioni da te richieste non sono possibili senza il GPS attivo.Vuoi attivarlo?");
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    turnGPSOn();
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStackImmediate();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            layout.setVisibility(View.VISIBLE);
            setValue();
        }
        return view;

    }

    private void turnGPSOn(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);

        layout.setVisibility(View.VISIBLE);
        setValue();
    }

    private void setValue(){

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        ico.setTypeface(tf);
        ico.setText(Html.fromHtml(sp.getString("ico", null)));

        String s = sp.getString("city", null);
        System.out.println(s);
        city.setText(sp.getString("city", null));
        up.setText(sp.getString("up", null));
        det.setText(sp.getString("det", null));
        temp.setText(sp.getString("tem", null));
        wind.setText(sp.getString("wind", null));
        hum.setText(sp.getString("hum", null));
        pres.setText(sp.getString("pres", null));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
            }
        });
    }
}
