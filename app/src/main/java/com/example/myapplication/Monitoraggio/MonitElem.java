package com.example.myapplication.Monitoraggio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;
import com.example.myapplication.Sensor.SensorePesticidi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MonitElem extends BaseAdapter {

    private Context mContext;

    private DbManager dbManager;

    private ImageView image;
    private TextView title;

    private ArrayList<Plant> piante;

    public MonitElem( Context context, ArrayList <Plant> obj) {
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
        convertView = inflater.inflate(R.layout.monitoraggio_details,null);

        image = convertView.findViewById(R.id.ElemIco);
        title = convertView.findViewById(R.id.ElemTitle);


        Plant p = piante.get(position);

        image.setImageBitmap(p.getFoto());

        title.setText(p.getName());

        return convertView;
    }
}
