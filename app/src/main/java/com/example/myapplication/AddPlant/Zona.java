package com.example.myapplication.AddPlant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class Zona extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> zone;

    public Zona (Context ctx, ArrayList<String> s){
        this.mContext = ctx;
        this.zone = s;
    }

    @Override
    public int getCount() {
        return zone.size();
    }

    @Override
    public Object getItem(int position) {
        return zone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.zona_layout, null);

        TextView text = v.findViewById(R.id.cellZone);


        String p = zone.get(position);

        text.setText(p);

        return v;
    }
}
