package com.example.myapplication.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myapplication.DB.DbManager;
import com.example.myapplication.Login;
import com.example.myapplication.R;

public class Profilo extends Fragment {

    private ViewGroup con;
    private CheckBox show;
    private TextView psw;
    private TextView user;
    private TextView nome;
    private TextView cognome;
    private TextView mail;
    private Button logout;

    private String s ;

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sp = getActivity().getSharedPreferences("Profilo",0);
         s = sp.getString("User","USER NAME");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.profilo_layout, container, false);

        this.con = container;

        show = view.findViewById(R.id.profShowPsw);
        psw = view.findViewById(R.id.profPsw);
        user = view.findViewById(R.id.profUser);
        nome = view.findViewById(R.id.profName);
        cognome = view.findViewById(R.id.profSurn);
        mail = view.findViewById(R.id.profMail);

        logout = view.findViewById(R.id.profLogout);



        user.setText(s);
        nome.setText(sp.getString("Nome", " "));
        cognome.setText(sp.getString("Cognome", " "));
        mail.setText(sp.getString("Mail", " "));
        psw.setText(sp.getString("Pass", " "));

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sp.edit();
                myEdit.putString("State","false");
                myEdit.apply();
                Intent log = new Intent(getActivity(), Login.class);
                startActivity(log);
            }
        });

        return view;
    }

}


