package com.example.myapplication.WikiPlants;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ResultAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList <WikiPlant> list;
    private ArrayList <String> selectList;

    private TextView resText;

    public ResultAdapter(Context mContext, ArrayList<WikiPlant> result){
        this.mContext = mContext;
        this.selectList = new ArrayList<>();
        this.list = result;
        createSelList();
    }

    private void createSelList (){
        for(WikiPlant wp: list){
            selectList.add(wp.getName());
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.result_adapter_layout, null);

        resText = v.findViewById(R.id.Result);

        resText.setText(selectList.get(position));

        return v;
    }
}
