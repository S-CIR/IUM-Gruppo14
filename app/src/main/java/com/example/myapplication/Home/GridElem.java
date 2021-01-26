package com.example.myapplication.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class GridElem extends BaseAdapter {

    private Context mContext;

    private TextView text;
    private ImageView img;

    private  ArrayList <Plant> piante;

    public GridElem(Context context,  ArrayList <Plant> obj) {
        this.mContext=context;
        this.piante=obj;
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
   public View getView (int pos, View v, ViewGroup parent){

        LayoutInflater inflater=  LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.activity_grid_elem,null);

        text = v.findViewById(R.id.tit);
        img = v.findViewById(R.id.pic);

        Plant p = piante.get(pos);

        img.setImageBitmap(p.getFoto());
        text.setText(p.getName());

        return v;
   }
}

