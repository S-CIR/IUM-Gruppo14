package com.example.myapplication.Plant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.R;
import com.example.myapplication.Home.Home;
import com.example.myapplication.Home.Plant;

public class ProfiloPiante extends Fragment {

    private DbManager dbManager;

    private Plant plant;

    private View view;

    private int id;

    private TextView title,type,sem,zona,typeZone,racc;
    private ImageView foto;
    private Button del;
    private SharedPreferences sp;

    public ProfiloPiante(int id) {
        this.id=id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DbManager(getContext());
        plant = dbManager.getPlant(this.id);
        sp = getContext().getSharedPreferences("Zone", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profilo_piante_layout, container, false);

        title = view.findViewById(R.id.PlantTitle);
        title.setText(plant.getName());

        type = view.findViewById(R.id.PlantType);
        type.setText(plant.getTipo());

        sem = view.findViewById(R.id.PlantSemina);
        sem.setText(plant.getData());

        zona = view.findViewById(R.id.Zona);
        zona.setText(plant.getZona());

        typeZone = view.findViewById(R.id.TypeZone);
        typeZone.setText(sp.getString(plant.getZona(),"N/A"));

        racc = view.findViewById(R.id.PlantRacc);
        racc.setText(plant.getRaccolta());

        foto = view.findViewById(R.id.PlantFoto);
        foto.setImageBitmap(plant.getFoto());

        del = view.findViewById(R.id.PlantDel);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delPlant();
            }
        });
        return view;
    }

    public void delPlant (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sicuro di voler proseguire?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean resp = dbManager.delPlant(plant.getID());
                if(resp){
                    Toast.makeText(getContext(),"Pianta Eliminata",Toast.LENGTH_LONG);
                }

                configHome();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.create();
        builder.show();

    }

    private void configHome () {
        FragmentManager fm = getFragmentManager();
        Fragment f = new Home();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        ft.commit();
    }
}
