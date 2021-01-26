package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DbManager;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    public String u,p,s,userdb,passdb, emaildb, nomedb, cognomedb;
    private EditText user, pass;
    private DbManager dbManager;
    private SharedPreferences sp;
    private TextView lost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbManager= new DbManager(this);
        sp = getSharedPreferences("Profilo",MODE_PRIVATE);
        lost = findViewById(R.id.lost);
        s= sp.getString("State","false");
        u= sp.getString("User","");
        p= sp.getString("Pass","");

        if(s.equals("true")){
            Intent home = new Intent(Login.this,Menu.class);
            startActivity(home);
        }

        Button up = (Button) findViewById(R.id.up);
        final Button in = (Button) findViewById(R.id.in);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.psw);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup= new Intent(Login.this,SignUp.class);
                startActivity(signup);
            }
        });

        in.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                String ptemp = pass.getText().toString();
                String utemp = user.getText().toString();
                Cursor c = dbManager.getUser();
                ifUserExist(c,utemp);

                if(!userdb.equals(utemp) || !passdb.equals(ptemp)){
                    Toast.makeText(getApplicationContext(),"User e/o Password Errate",Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences.Editor myEdit= sp.edit();
                    myEdit.putString("State", "true");
                    myEdit.putString("Nome",nomedb);
                    myEdit.putString("Cognome",cognomedb);
                    myEdit.putString("Mail",emaildb);
                    myEdit.putString("User",userdb);
                    myEdit.putString("Pass",passdb);
                    myEdit.apply();

                    Intent home = new Intent(Login.this,Menu.class);
                    startActivity(home);
                }
                    }
        });

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retrieve = new Intent(Login.this,Retrieve.class);
                startActivity(retrieve);
            }
        });
    }

    private void ifUserExist(Cursor c, String user){
        if(c.moveToFirst()){
                do{
                    if (c.getString(3).equals(user)) {
                        nomedb = c.getString(0);
                        cognomedb = c.getString(1);
                        emaildb = c.getString(2);
                        userdb = c.getString(3);
                        passdb = c.getString(4);
                    }
                }while(c.moveToNext());
            }
        }


}
