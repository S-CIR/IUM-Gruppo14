package com.example.myapplication.Irrigazione;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;
import com.example.myapplication.Sensor.SensoreIrrigazione;

import java.util.ArrayList;

public class Irrigazione extends Fragment {

    private DbManager dbManager;
    private ArrayList<Plant> piante;

    private int selectedZone;

    private GridView list;
    private TextView mode,start;
    private SensoreIrrigazione sensor;

    public Irrigazione (int zone){
        this.selectedZone = zone;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DbManager(getContext());
        piante = new ArrayList<>();
        sensor = new SensoreIrrigazione();
    }

    private void insertPlantByZone (){
        Cursor c = dbManager.getByZone("'Zona: " + selectedZone + "'");
        piante.clear();
        if(c.moveToFirst()){
            do{
                byte[] byteArray = c.getBlob(1);
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                int i=Integer.parseInt(c.getString(0));
                piante.add(new Plant (i,bm,c.getString(2),c.getString(3),c.getString(4)));
            }while(c.moveToNext());
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.irrigazione_layout,container,false);

        list = view.findViewById(R.id.listIrr);
        mode = view.findViewById(R.id.IrMode);
        start = view.findViewById(R.id.IrStart);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        start.setTypeface(tf);
        start.setText(Html.fromHtml("\uF043"));

        mode.setText("AUTO");

        insertPlantByZone();
        if (piante.size() != 0) {
            IrrigElem adapter = new IrrigElem(sensor, getContext(), piante);
            list.setAdapter(adapter);
        }

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.getText().toString().equals("AUTO")){
                    mode.setText("SELF");
                    start.setVisibility(View.VISIBLE);
                    start.setClickable(true);
                }
                else{
                    mode.setText("AUTO");
                    start.setVisibility(View.INVISIBLE);
                    start.setClickable(false);
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configIrr();
            }
        });

        return view;
    }

    private void configIrr () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new SelfIrrigazione(piante);
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack("irrig");
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }

    @Override
    public void onStart (){
        super.onStart();
        insertPlantByZone();

        if(piante.size()!=0) {
            IrrigElem adapter = new IrrigElem(sensor, getContext(), piante);
            list.setAdapter(adapter);
        }
    }

}
