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

public class Menu_penggunabu extends AppCompatActivity {

    ImageView btnPertanyaan, btnProfil, btnUstad, btnSearch, btnDraft, btnhistori;

    String kode_pengguna, nama_pengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pengguna);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Menu_penggunabu.this);
        Boolean Registered = sharedPref.getBoolean("Registered", false);
        if (!Registered){
            finish();
        }else {
            kode_pengguna= sharedPref.getString("kode_pengguna", "");
            nama_pengguna = sharedPref.getString("nama_pengguna", "");
        }

        btnProfil=(ImageView)findViewById(R.id.btnProfil);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_penggunabu.this,Profil.class);
                i.putExtra("pk",kode_pengguna);
                startActivity(i);
            }});


        btnUstad=(ImageView)findViewById(R.id.btnUstad);
        btnUstad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_penggunabu.this,List_user.class);
                startActivity(i);
            }});

        btnPertanyaan=(ImageView)findViewById(R.id.btnPertanyaan);
        btnPertanyaan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_penggunabu.this,Pertanyaan.class);
                startActivity(i);
            }});

        btnSearch=(ImageView)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_penggunabu.this,Cari.class);
                startActivity(i);
            }});

        btnDraft=(ImageView)findViewById(R.id.btnDraft);
        btnDraft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_penggunabu.this,List_draft.class);
                startActivity(i);
            }});

        btnhistori=(ImageView)findViewById(R.id.btnhistori);
        btnhistori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_penggunabu.this,List_histori_pengguna.class);
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
        AlertDialog.Builder ad=new AlertDialog.Builder(Menu_penggunabu.this);
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
