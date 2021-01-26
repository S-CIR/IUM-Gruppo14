package com.example.myapplication.Pesticidi;

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
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Concimazione.ConcimElem;
import com.example.myapplication.Concimazione.SelfConcimazione;
import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.Irrigazione.IrrigElem;
import com.example.myapplication.Irrigazione.SelfIrrigazione;
import com.example.myapplication.R;
import com.example.myapplication.Sensor.SensoreConcime;
import com.example.myapplication.Sensor.SensoreIrrigazione;
import com.example.myapplication.Sensor.SensorePesticidi;

import java.util.ArrayList;

public class Pesticidi extends Fragment {

    private DbManager dbManager;
    private SharedPreferences sp;

    private ArrayList<Plant> piante;

    private int selectedZone;

    private GridView list;
    private TextView mode;
    private SensorePesticidi sensor;

    public Pesticidi(int zone){
        this.selectedZone = zone;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        sp = getContext().getSharedPreferences("Zone", 0);
        String def = "Zona: 1";
        sensor = new SensorePesticidi(sp.getString("Zona: "+selectedZone,def));
        dbManager = new DbManager(getContext());
        piante = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.pesticidi_layout,container,false);

        list = view.findViewById(R.id.listCon);
        mode = view.findViewById(R.id.CoMode);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/fa-solid-900.ttf");
        mode.setTypeface(tf);
        mode.setText(Html.fromHtml("\uE05D"));

        insertPlantByZone();
        if (piante.size() != 0) {
            PestElem adapter = new PestElem(sensor, getContext(), piante);
            list.setAdapter(adapter);
        }

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configPest();
            }
        });

        return view;
    }

    private void configPest () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new SelfPesticidi(piante);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.addToBackStack("pesticidi");
        ft.commit();
    }

    @Override
    public void onStart (){
        super.onStart();
        insertPlantByZone();

        if(piante.size()!=0) {
            PestElem adapter = new PestElem(sensor, getContext(), piante);
            list.setAdapter(adapter);
        }
    }

}
