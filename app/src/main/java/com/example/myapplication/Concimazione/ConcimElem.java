package com.example.myapplication.Concimazione;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;
import com.example.myapplication.Sensor.SensoreConcime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConcimElem extends BaseAdapter {

    private Context mContext;

    private DbManager dbManager;

    private ImageView image;
    private TextView title, description;

    private int hum, nec;

    private SensoreConcime sensor;

    private ArrayList<Plant> piante;

    public ConcimElem (SensoreConcime sensor, Context context, ArrayList <Plant> obj) {
        this.sensor = sensor;
        this.mContext=context;
        this.piante=obj;
        dbManager = new DbManager(context);
    }

    @Override
    public int getCount() {
        return piante.size();
    }

    @Override
    public Object getItem(int position) {
        return piante.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=  LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.concime_details,null);

        image = convertView.findViewById(R.id.ElemIco);
        title = convertView.findViewById(R.id.ElemTitle);
        description = convertView.findViewById(R.id.ElemDesc);

        Plant p = piante.get(position);

        image.setImageBitmap(p.getFoto());

        title.setText(p.getName());

        String[] arr = sensor.genData();

        int i= Integer.parseInt(arr[2]);
        if(i>40){
            startConc(p);
            arr[2]=sensor.concima();

        }

        String det = "Tipologia: " + arr[0] + ";\nConcime: " + arr[1] + ";\nNecessità: " + arr[2] + "%;";
        description.setText(det);

        return convertView;
    }

    public void startConc (Plant p){
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        dbManager.setConc(p.getID(),today);
    }

}
