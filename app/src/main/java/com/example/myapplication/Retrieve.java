package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Home.Home;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Retrieve extends AppCompatActivity {

    private TextView text;
    private EditText email;
    private Button richiedi;
    private DbManager dbManager;
    private  ArrayList<String > arrayList;
    private  int i=0, found=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_retrieve);
        arrayList = new ArrayList<>();
        dbManager= new DbManager(this);

        text = findViewById(R.id.text);
        email = (EditText) findViewById(R.id.mail);
        richiedi = findViewById(R.id.set);

        richiedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                if(!mail.equals("")) {
                    Cursor c = dbManager.getUser();
                    i = ifUserExist(c, mail);
                }

                if(i!=0){
                    text.setText("Abbiamo provveduto ad inviare i dati richiesti all'indirizzo email da te indicato");
                    email.setVisibility(View.INVISIBLE);
                    found=1;
                }
                else{
                    Toast.makeText(getApplicationContext(),"L'email indicata non risulta nel sistema. Riprova o registrati",Toast.LENGTH_LONG).show();
                    return;
                }

                richiedi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(found==1) {
                            Intent login = new Intent(Retrieve.this, Login.class);
                            startActivity(login);
                        }
                        else
                            return;
                    }
                });
            }
        });
    }

    private int ifUserExist(Cursor c, String user){
        ArrayList<String> arrayList = new ArrayList<>();
        int i=0;
        if(c.moveToFirst()){
                do{
                    if (c.getString(2).equals(user)) {
                        i=1;
                        arrayList.add(c.getString(0));
                        arrayList.add(c.getString(1));
                        arrayList.add(c.getString(2));
                        arrayList.add(c.getString(3));
                        arrayList.add(c.getString(4));
                    }
                }while(c.moveToNext());

            }
        return i;
    }

}
