
package com.example.myapplication.Zone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Concimazione.Concimazione;
import com.example.myapplication.Irrigazione.Irrigazione;
import com.example.myapplication.Monitoraggio.InfoZone;
import com.example.myapplication.Pesticidi.Pesticidi;
import com.example.myapplication.R;

public class ZoneManager extends Fragment {

    private SharedPreferences sp;
    private SharedPreferences.Editor myEdit;

    private int zoneNum;

    private int selector = -1;

    private GridView zone;

    /*
        selector = -1 --> noZone;
        selector = 0 --> irrigazione;
        selector = 1 --> concimazione;
        selector = 2 --> pesticidi;
        selector = 3 --> monitoraggio;
     */

    public ZoneManager(int selector) {
        this.selector = selector;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getContext().getSharedPreferences("Zone", 0);
        myEdit = sp.edit();

        zoneNum = sp.getInt("last",0);

        if(zoneNum == 0)
            redirectSelector(-1);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zone_manager_layout,container,false);
        zone = view.findViewById(R.id.ListZone);

        ZoneAdapter adapter = new ZoneAdapter(getContext(), zoneNum);
        zone.setAdapter(adapter);

        zone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                redirectSelector(position+1);
            }
        });

        return view;
    }

    private void redirectSelector (int selected){
        if(selected == -1){
            FragmentManager fm = getFragmentManager();
            Fragment f = new NoZone();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack("zoneIrrig");
            ft.replace(R.id.MainFrameLayout,f);
            ft.commit();
        }
        else if(selector == 0){
            FragmentManager fm = getFragmentManager();
            Fragment f = new Irrigazione(selected);
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack("zoneIrrig");
            ft.replace(R.id.MainFrameLayout,f);
            ft.commit();
        }
        else if(selector == 1){
            FragmentManager fm = getFragmentManager();
            Fragment f = new Concimazione(selected);
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack("zoneConc");
            ft.replace(R.id.MainFrameLayout,f);
            ft.commit();
        }
        else if(selector == 2){
            FragmentManager fm = getFragmentManager();
            Fragment f = new Pesticidi(selected);
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack("zoneConc");
            ft.replace(R.id.MainFrameLayout,f);
            ft.commit();
        }
        else if(selector == 3){
            FragmentManager fm = getFragmentManager();
            Fragment f = new InfoZone(selected);
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack("zoneConc");
            ft.replace(R.id.MainFrameLayout,f);
            ft.commit();
        }

    }

}
