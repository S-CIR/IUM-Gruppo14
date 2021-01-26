package com.example.myapplication.WikiPlants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class PlantInfo extends Fragment {

    private TextView vTitle, vType, vSeeding, vDescription;
    private String title, type, seeding, description;

    private View v;

    public PlantInfo(WikiPlant plant) {
        this.title = plant.getName();
        this.type = plant.getType();
        this.seeding = plant.getSeeding();
        this.description = plant.getDescription();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.infoplant_layout, view, false);

        vTitle = v.findViewById(R.id.titolo);
        vType  = v.findViewById(R.id.type);
        vSeeding = v.findViewById(R.id.seeding);
        vDescription = v.findViewById(R.id.descr);

        vTitle.setText(title);
        vType.setText(type);
        vSeeding.setText("Semina: "+seeding);
        vDescription.setText(description);

        return v;
    }

}