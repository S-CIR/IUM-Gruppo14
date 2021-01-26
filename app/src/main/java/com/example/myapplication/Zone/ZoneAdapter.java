package com.example.myapplication.Zone;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ZoneAdapter extends BaseAdapter {

    private Context mContext;
    private int zone;

    private ArrayList <String> stringZone;

    private TextView zoneText;

    public ZoneAdapter (Context mContext, int zoneNum){
        this.mContext = mContext;
        this.zone = zoneNum;
        stringZone = new ArrayList<>();
        createZone();
    }

    private void createZone (){
        for(int i=0; i<zone; i++){
            int temp = i+1;
            stringZone.add("Zona: " + temp);
        }
    }

    @Override
    public int getCount() {
        return stringZone.size();
    }

    @Override
    public Object getItem(int position) {
        return stringZone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.zone_adapter_layout, null);

        zoneText = v.findViewById(R.id.Zone);

        zoneText.setText(stringZone.get(position));

        return v;
    }
}
