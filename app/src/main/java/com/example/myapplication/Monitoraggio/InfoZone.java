package com.example.myapplication.Monitoraggio;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.Plant.ProfiloPiante;
import com.example.myapplication.R;


import java.util.ArrayList;


public class InfoZone extends Fragment {

    private View view;

    private DbManager dbManager;
    private SharedPreferences sp;

    private Button back;

    private TextView info, zone;
    private int zona;
    private ArrayList<Plant> piante;
    private GridView grid;

    public InfoZone(int zona){

        this.zona=zona;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getContext());
        piante = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_zone_layout, container, false);
        zone= view.findViewById(R.id.where);
        info = view.findViewById(R.id.information);
        back = view.findViewById(R.id.backList);
        grid = view.findViewById(R.id.zonaPlant);
        insertPlantByZone();

        zone.setText("Zona: "+zona);
        sp = getContext().getSharedPreferences("Zone", 0);

        String tipo=sp.getString("Zona: "+zona,"Zona: 0");
        String information = "Nella Zona: "+zona+" sono stati utilizzati: \nPesticidi di tipo:"+tipo+";\nConcimanti di tipo:"+tipo+";";
        info.setText(information);
        if (piante.size() != 0) {
            MonitElem adapter = new MonitElem(getContext(), piante);
            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    confProfPiante(position);
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStackImmediate();
            }
        });

        return view;
    }

    private void insertPlantByZone (){
        Cursor c = dbManager.getByZone("'Zona: " + zona + "'");
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

    private void confProfPiante(int position){
        FragmentManager fm = getFragmentManager();
        Fragment f = new ProfiloPiante(piante.get(position).getID());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.addToBackStack("Pianta: "+position);
        ft.commit();
    }

}
