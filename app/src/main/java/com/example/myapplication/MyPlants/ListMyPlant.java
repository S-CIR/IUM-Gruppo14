package com.example.myapplication.MyPlants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Home.Plant;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ListMyPlant extends BaseAdapter {

    private Context context;
    private ArrayList <Plant> plants;

    private ImageView image;
    private TextView title, description;

    public ListMyPlant (Context context, ArrayList <Plant> list){
        this.context=context;
        this.plants=list;
    }


    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Object getItem(int position) {
        return plants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=  LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.details_plant,null);

        image = convertView.findViewById(R.id.ElemIco);
        title = convertView.findViewById(R.id.ElemTitle);
        description = convertView.findViewById(R.id.ElemDesc);

        Plant p = plants.get(position);

        image.setImageBitmap(p.getFoto());

        title.setText(p.getName());

        String desc = "Semina: " + p.getData() + ";\nIrrigazione: " + p.getIrrigazione() +
                ";\nConcimazione: " + p.getConcimazione() + ";\nPesticidi: " + p.getPesticidi() + ";";
        description.setText(desc);

        return convertView;
    }
}
