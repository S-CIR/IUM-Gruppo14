package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DbManager;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    public ArrayList<String> utente;
    public DbManager dbManager;
    private TextView nome, cognome, mail, user, psw, cpsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        utente = new ArrayList<String>();
        dbManager=new DbManager(this);
        utente.add("true");

        nome = (EditText) findViewById(R.id.nome);
        cognome = (EditText) findViewById(R.id.cognome);
        mail = (EditText) findViewById(R.id.mail);
        user = (EditText) findViewById(R.id.user);
        psw = (EditText) findViewById(R.id.psw);
        cpsw = (EditText) findViewById(R.id.conf);

        Button set = (Button) findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utente.add(nome.getText().toString());
                utente.add(cognome.getText().toString());
                utente.add(mail.getText().toString());
                utente.add(user.getText().toString());
                utente.add(psw.getText().toString());
                utente.add(cpsw.getText().toString());

                if(!utente.get(5).equals(utente.get(6))){
                    Toast.makeText(v.getContext(),"Le password non corrispondono",Toast.LENGTH_LONG).show();
                }
                else if(utente.get(1).equals("") || utente.get(1).equals("Nome")){
                    Toast.makeText(v.getContext(),"Nome non inserito",Toast.LENGTH_LONG).show();
                }
                else if(utente.get(2).equals("") || utente.get(2).equals("Cognome")){
                    Toast.makeText(v.getContext(),"Cognome non inserito",Toast.LENGTH_LONG).show();
                }
                else if(utente.get(3).equals("") || utente.get(3).equals("E-Mail")){
                    Toast.makeText(v.getContext(),"E-Mail non inserita",Toast.LENGTH_LONG).show();
                }
                else if(utente.get(4).equals("") || utente.get(4).equals("User")){
                    Toast.makeText(v.getContext(),"User non inserita",Toast.LENGTH_LONG).show();
                }
                else {
                    dbManager.addUser(utente.get(1),utente.get(2),utente.get(3),utente.get(4),utente.get(5));

                    SharedPreferences sp = getSharedPreferences("Profilo", 0);
                    SharedPreferences.Editor myEdit = sp.edit();
                    myEdit.putString("State", utente.get(0));
                    myEdit.putString("Nome", utente.get(1));
                    myEdit.putString("Cognome",utente.get(2));
                    myEdit.putString("Mail",utente.get(3));
                    myEdit.putString("User",utente.get(4));
                    myEdit.putString("Pass",utente.get(5));
                    myEdit.apply();

                    Intent log = new Intent(SignUp.this,Login.class);
                    startActivity(log);
                }
            }
        });
    }
}
