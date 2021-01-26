package com.example.myapplication.Pesticidi;

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
import com.example.myapplication.Sensor.SensoreIrrigazione;
import com.example.myapplication.Sensor.SensorePesticidi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PestElem extends BaseAdapter {

    private Context mContext;

    private DbManager dbManager;

    private ImageView image;
    private TextView title, description;

    private int hum, nec;

    private SensorePesticidi sensor;

    private ArrayList<Plant> piante;

    public PestElem(SensorePesticidi sensor, Context context, ArrayList <Plant> obj) {
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
        convertView = inflater.inflate(R.layout.pesticidi_details,null);

        image = convertView.findViewById(R.id.ElemIco);
        title = convertView.findViewById(R.id.ElemTitle);
        description = convertView.findViewById(R.id.ElemDesc);

        Plant p = piante.get(position);

        image.setImageBitmap(p.getFoto());

        title.setText(p.getName());

        String[] arr = sensor.genData();

        int i= Integer.parseInt(arr[2]);
        if(i>40){
            startPest(p);
            arr[2]=sensor.pesticida();

        }

        String det = "Tipologia: " + arr[0] + ";\nPesticida: " + arr[1] + ";\nNecessità: " + arr[2] + "%;";
        description.setText(det);

        return convertView;
    }

    public void startPest(Plant p){
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        dbManager.setPest(p.getID(),today);
    }

}
