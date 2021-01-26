package com.example.myapplication.Home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Menu;
import com.example.myapplication.Plant.ProfiloPiante;
import com.example.myapplication.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Home extends Fragment {

    private View v;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private DbManager dbManager;

    private GridView gridView;

    private ArrayList<Plant> piante;

    private Double latitude;
    private Double longitude;

    private TextView city, temp, det, hum, pres, ico, wind;

    private TextView sugg;
    private SharedPreferences sp,sp1;
    private SharedPreferences.Editor myEdit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DbManager(getContext());
        piante = new ArrayList<>();

        sp = getContext().getSharedPreferences("Tempo", 0);
        myEdit = sp.edit();

        sp1 = getContext().getSharedPreferences("Profilo", 0);

        controlGPS();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_layout, view, false);
        gridView = v.findViewById(R.id.grid);

        city = v.findViewById(R.id.city);
        temp = v.findViewById(R.id.temp);
        hum = v.findViewById(R.id.hum);
        det = v.findViewById(R.id.det);
        pres = v.findViewById(R.id.pres);
        ico = v.findViewById(R.id.ico);
        wind = v.findViewById(R.id.wind);

        sugg = v.findViewById(R.id.sugg);
        sugg.setText(R.string.consiglio_mensile);

        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("log", "Permission granted");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        InsertPlantsInGrid();
        if (piante.size() != 0) {
            GridElem adapter = new GridElem(getContext(), piante);
            gridView.setAdapter(adapter);
        }

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);
        }else{
            getCurrentLocation();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant plant = (Plant) gridView.getItemAtPosition(position);
                configPlantView(plant);
            }
        });

        return v;
    }

    private void configPlantView (Plant plant) {
        FragmentManager fm = getFragmentManager();
        Fragment f = new ProfiloPiante(plant.getID());
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else
               Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart (){
        super.onStart();
        InsertPlantsInGrid();
        if(piante.size()!=0) {
            GridElem adapter = new GridElem(getContext(), piante);
            gridView.setAdapter(adapter);
        }
    }

    private void InsertPlantsInGrid (){
            String mail =sp1.getString("Mail",null);

            Cursor c = dbManager.getAllPlants();
            piante.clear();
            if (c.moveToFirst()) {
                do {
                    if(c.getString(10).equals(mail)) {
                        byte[] byteArray = c.getBlob(1);
                        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        int i = Integer.parseInt(c.getString(0));
                        piante.add(new Plant(i, bm, c.getString(2), c.getString(3), c.getString(4)));
                    }
                } while (c.moveToNext());
            }

    }

    private void getCurrentLocation (){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(locationRequest, new LocationCallback(){
                @Override
                public void onLocationResult (LocationResult result) {
                    super.onLocationResult(result);
                    LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(this);
                    if(result!=null && result.getLocations().size()>0){
                        int lastLocationIndex = result.getLocations().size()-1;
                        latitude = result.getLocations().get(lastLocationIndex).getLatitude();
                        longitude = result.getLocations().get(lastLocationIndex).getLongitude();

                        System.out.println(""+latitude+" "+longitude);
                        getJSONResponse(latitude+"",longitude+"");

                        SharedPreferences sp = getContext().getSharedPreferences("location", 0);
                        SharedPreferences.Editor myEdit = sp.edit();
                        myEdit.putString("lat", latitude+"");
                        myEdit.putString("lon", longitude+"");
                        myEdit.apply();
                    }
                }
        }, Looper.getMainLooper());
    }

    private void getJSONResponse (String lat, String lon) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=06c3e773163222980abc9f7f787b9d27&lang=it";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jsonWeather = new JSONObject(response);
                    if (jsonWeather != null){
                        setJSONWeather(jsonWeather);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void setJSONWeather (JSONObject jsonWeather) {
        String[] jsonData = new String[8];

        try{
            if(jsonWeather != null){
                JSONObject details = jsonWeather.getJSONArray("weather").getJSONObject(0);
                JSONObject main = jsonWeather.getJSONObject("main");
                JSONObject vento = jsonWeather.getJSONObject("wind");
                DateFormat df = DateFormat.getDateInstance();
                String city = jsonWeather.getString("name")+", "+jsonWeather.getJSONObject("sys").getString("country");
                String description = details.getString("description").toLowerCase(Locale.ITALY);
                String temperature = "Temperatura: "+String.format("%.0f",main.getDouble("temp"))+"°";
                String humidity = "Umidità: "+main.getString("humidity")+"%";
                String pressure = "Pressione: "+main.getString("pressure")+"hPa";
                String updateOn = df.format(new Date(jsonWeather.getLong("dt")*1000));
                String wind = "Vento: "+ vento.getString("speed")+"Km/h";

                String icoText= setWeatherIcon(details.getInt("id"),jsonWeather.getJSONObject("sys").getLong("sunrise")*1000,jsonWeather.getJSONObject("sys").getLong("sunset")*1000);

                jsonData[0] = city;
                jsonData[1] = description;
                jsonData[2] = temperature;
                jsonData[3] = humidity;
                jsonData[4] = pressure;
                jsonData[5] = updateOn;
                jsonData[6] = icoText;
                jsonData[7] = wind;

                myEdit.putString("city",city);
                myEdit.putString("det",jsonData[1]);
                myEdit.putString("tem",jsonData[2]);
                myEdit.putString("hum",jsonData[3]);
                myEdit.putString("press",jsonData[4]);
                myEdit.putString("up",jsonData[5]);
                myEdit.putString("ico",jsonData[6]);
                myEdit.putString("wind",jsonData[7]);
                myEdit.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        city.setText(jsonData[0]);
        det.setText(jsonData[1].toUpperCase());
        temp.setText(jsonData[2]);
        hum.setText(jsonData[3]);
        pres.setText(jsonData[4]);
        wind.setText(jsonData[7]);

        Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/weathericons-regular-webfont.ttf");

        ico.setTypeface(tf);
        ico.setText(Html.fromHtml(jsonData[6]));

        Menu.image.setVisibility(View.INVISIBLE);
        Menu.linear.setVisibility(View.VISIBLE);
    }

    public static String setWeatherIcon(int actualId, long sunrise, long sunset){
        int id= actualId/100;
        String icon="";
        if(actualId==800){
            long currentTime= new Date().getTime();
            if(currentTime>=sunrise&& currentTime< sunset){
                icon="&#xf00d;";
            }
            else{
                icon="&#xg02e;";
            }
        }
        else{
            switch(id){
                case 2:
                    icon="&#xf01e;";
                    break;
                case 3:
                    icon="&#xf01c;";
                    break;
                case 7:
                    icon="&#xf014;";
                    break;
                case 8:
                    icon="&#xf013;";
                    break;
                case 6:
                    icon="&#xf01b;";
                    break;
                case 5:
                    icon="&#xf019;";
                    break;
            }
        }
        return icon;
    }

    private void controlGPS (){
        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.contains("gps")){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Vuoi Attivare il GPS?");
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    turnGPSOn();
                    Menu.image.setVisibility(View.INVISIBLE);
                    Menu.linear.setVisibility(View.VISIBLE);
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Typeface tf=Typeface.createFromAsset(getContext().getAssets(),"fonts/weathericons-regular-webfont.ttf");
                    ico.setTypeface(tf);
                    ico.setText("\uF07B");
                    city.setText("Meteo non disponibile");
                    det.setText("Attiva il GPS");
                    Menu.image.setVisibility(View.INVISIBLE);
                    Menu.linear.setVisibility(View.VISIBLE);
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    private void turnGPSOn(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

}
