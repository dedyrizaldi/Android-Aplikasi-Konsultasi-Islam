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
import android.widget.Button;
import android.widget.ImageView;

public class Menu_ustad extends AppCompatActivity {

    ImageView btninbox, btnprofil, btnoutbox;
String kode_user, nama_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ustad);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Menu_ustad.this);
        Boolean Registered = sharedPref.getBoolean("Registered", false);
        if (!Registered){
            finish();
        }else {
            kode_user= sharedPref.getString("kode_user", "");
            nama_user= sharedPref.getString("nama_user", "");
        }


        btninbox=(ImageView)findViewById(R.id.btninbox);
        btninbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_ustad.this,List_inbox.class);
                startActivity(i);
            }});

        btnoutbox=(ImageView)findViewById(R.id.btnoutbox);
        btnoutbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_ustad.this,List_outbox.class);
                startActivity(i);
            }});


        btnprofil=(ImageView)findViewById(R.id.btnprofil);
        btnprofil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_ustad.this,Profil_user.class);
                i.putExtra("pk",kode_user);
                startActivity(i);
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
        AlertDialog.Builder ad=new AlertDialog.Builder(Menu_ustad.this);
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
