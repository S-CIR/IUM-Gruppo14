package com.example.myapplication.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class DeviceElem extends BaseAdapter {

    private Context mContext;
    private ArrayList<Device> device;
    private ImageView image;
    private TextView title;


    public DeviceElem(Context context, ArrayList<Device> d) {

        this.mContext=context;
        this.device=d;
    }

    @Override
    public int getCount() {
        return device.size();
    }

    @Override
    public Object getItem(int position) {
        return device.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=  LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.device_elem_layout,null);

        image = convertView.findViewById(R.id.picDevice);
        title = convertView.findViewById(R.id.titDevice);

        Device d= device.get(position);

        image.setImageBitmap(d.getFoto());
        title.setText(d.getIdDevice());

        return convertView;
    }


}
