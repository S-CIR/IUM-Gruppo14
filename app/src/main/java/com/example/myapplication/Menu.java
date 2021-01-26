package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.Monitoraggio.Monitoraggio;
import com.example.myapplication.MyPlants.MyPlants;
import com.example.myapplication.Home.Home;
import com.example.myapplication.Settings.Settings;
import com.example.myapplication.User.Profilo;
import com.example.myapplication.WikiPlants.Wiki;
import com.example.myapplication.Zone.ZoneManager;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public static ImageView image;
    public static LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.loading);
        linear = findViewById(R.id.linear);


        this.configToolbar();
        this.configDrawerLayout();
        this.configNavigationView();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.configHome();
    }

    @Override
    public void onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.piante:
                this.configAddPlant();
                break;
            case R.id.concimazione:
                configZone (1);
                break;
            case R.id.irrigazione:
                configZone (0);
                break;
            case R.id.pesticidi:
                configZone (2);
                break;
            case R.id.monitoraggio:
                configMonit();
                break;
            case R.id.wiki:
                configWiki();
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configToolbar () {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu (android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu_tool,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.profilo:
                this.configProf();
                break;
            case R.id.home:
                this.configHome();
                break;
            case R.id.add:
                this.configSettings();
                break;
            default:
                break;
        }
        return true;
    }

    private void configHome () {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new Home();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }
    private void configSettings () {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new Settings();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }

    private void configZone (int selected){
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new ZoneManager(selected);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.addToBackStack("ZoneSelected:"+selected);
        ft.commit();
    }

    private void configProf () {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new Profilo();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }

    private void configAddPlant () {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new MyPlants();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }

    private void configMonit() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new Monitoraggio();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }

    private void configWiki() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new Wiki();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainFrameLayout,f);
        fm.popBackStackImmediate();
        ft.commit();
    }

    private void configDrawerLayout () {
        this.drawerLayout = findViewById(R.id.DrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navDrawOpen, R.string.navDrawClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configNavigationView () {
        this.navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
