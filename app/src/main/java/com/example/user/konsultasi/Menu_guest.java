package com.example.user.konsultasi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Menu_guest extends AppCompatActivity {

    ImageView  btnUstad, btnSearch, btnexit;
    TextView txtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_guest);




        txtlogin=(TextView)findViewById(R.id.txtlogin);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_guest.this,Login.class);
                startActivity(i);
            }});


        btnUstad=(ImageView)findViewById(R.id.btnUstad);
        btnUstad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_guest.this,List_user.class);
                startActivity(i);
            }});


        btnSearch=(ImageView)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_guest.this,Cari.class);
                startActivity(i);
            }});



        btnexit=(ImageView)findViewById(R.id.btnexit);
        btnexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                keluarYN();
            }});



    }

    public void keluar(){
        new AlertDialog.Builder(this)
                .setTitle("Menutup Aplikasi")
                .setMessage("Terimakasih... Anda Telah Menggunakan Aplikasi Ini")
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        finish();
                    }})
                .show();
    }
    public void keluarYN(){
        AlertDialog.Builder ad=new AlertDialog.Builder(Menu_guest.this);
        ad.setTitle("Konfirmasi");
        ad.setMessage("Apakah benar ingin keluar?");

        ad.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                keluar();
            }});

        ad.setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface arg0, int arg1) {
            }});

        ad.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            keluarYN();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
