package com.example.myapplication.Irrigazione;

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
import com.example.myapplication.Sensor.SensoreIrrigazione;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IrrigElem extends BaseAdapter {

    private Context mContext;

    private DbManager dbManager;

    private ImageView image;
    private TextView title, description;



    private SensoreIrrigazione sensor;

    private ArrayList<Plant> piante;

    public IrrigElem (SensoreIrrigazione sensor,Context context,  ArrayList <Plant> obj) {
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
        convertView = inflater.inflate(R.layout.irrigation_details,null);

        image = convertView.findViewById(R.id.ElemIco);
        title = convertView.findViewById(R.id.ElemTitle);
        description = convertView.findViewById(R.id.ElemDesc);

        Plant p = piante.get(position);

        image.setImageBitmap(p.getFoto());

        title.setText(p.getName());

        int[] arr = sensor.genData();
        if(arr[1]>40){      //bisogna irrigare
            startIrrig (p);
            arr[1] = sensor.genNec();
        }

        String det = "Umidità Suolo: " + arr[0] + "%;\nNecessità: " + arr[1] + "%;";
        description.setText(det);

        return convertView;
    }

    public void startIrrig (Plant p){
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        dbManager.setIrrig(p.getID(),today);
    }

}
