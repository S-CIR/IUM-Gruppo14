package com.example.myapplication.WikiPlants;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;


import java.util.ArrayList;

public class WikiSelect extends Fragment {

    private String key;
    private ArrayList <WikiPlant> selected;

    private TextView searchKey;
    private GridView result;


    public WikiSelect(String key, ArrayList<WikiPlant> selected) {
        this.key=key;
        this.selected=selected;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wikiselect_layout,container,false);
        searchKey = view.findViewById(R.id.searchKey);
        result = view.findViewById(R.id.gridResult);

        searchKey.setText(key);

        ResultAdapter adapter = new ResultAdapter(getContext(),selected);
        result.setAdapter(adapter);

        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                Fragment f = new PlantInfo(selected.get(position));
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack("selection");
                ft.replace(R.id.MainFrameLayout,f);
                ft.commit();
            }
        });

        return view;
    }

}
