package com.example.user.konsultasi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
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

public class Menu_pengguna extends AppCompatActivity {
    String ip="",kode_pertanyaan="",PESAN="";
    JSONParser jParser = new JSONParser();
    JSONArray myJSON = null;
    int adabaru=0;
    Handler mHandler;
    private static final String TAG_SUKSES = "sukses";
    private static final String TAG_record = "record";
    ImageView btnPertanyaan, btnProfil, btnUstad, btnSearch, btnDraft, btnhistori;

    String kode_pengguna, nama_pengguna;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pengguna);
        ip=jParser.getIP();
        sharedPrefManager = new SharedPrefManager(this);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Menu_pengguna.this);
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
                Intent i = new Intent(Menu_pengguna.this,Profil.class);
                i.putExtra("pk",kode_pengguna);
                startActivity(i);
            }});


        btnUstad=(ImageView)findViewById(R.id.btnUstad);
        btnUstad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_pengguna.this,List_user.class);
                startActivity(i);
            }});

        btnPertanyaan=(ImageView)findViewById(R.id.btnPertanyaan);
        btnPertanyaan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_pengguna.this,Pertanyaan.class);
                startActivity(i);
            }});

        btnSearch=(ImageView)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_pengguna.this,Cari.class);
                startActivity(i);
            }});

        btnDraft=(ImageView)findViewById(R.id.btnDraft);
        btnDraft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_pengguna.this,List_draft.class);
                startActivity(i);
            }});

        btnhistori=(ImageView)findViewById(R.id.btnhistori);
        btnhistori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(Menu_pengguna.this,List_histori_pengguna.class);
                startActivity(i);
            }});



        this.mHandler = new Handler();
        m_Runnable.run();
    }

    private final Runnable m_Runnable = new Runnable()    {
        public void run()  {
            //    Toast.makeText(Menu_utama.this,"cek in runnable",Toast.LENGTH_SHORT).show();
            // addNotification();
            new load().execute();
            Menu_pengguna.this.mHandler.postDelayed(m_Runnable,5000);
        }
    };

    private void addNotification1() {
        Toast.makeText(Menu_pengguna.this,"Info "+PESAN,Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_star_black_16dp)
                        .setContentTitle("Notif, Jawaban ")
                        .setContentText(PESAN);

        Intent notificationIntent = new Intent(this, Notif.class);//eventList
        notificationIntent.putExtra("kode_pertanyaan", kode_pertanyaan);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }


    private void addNotification2() {
        Toast.makeText(Menu_pengguna.this,"Info "+PESAN,Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.admin)
                        .setContentTitle("Notif, Jawaban")
                        .setContentText(PESAN);

        Intent notificationIntent = new Intent(this, Notif.class);//eventList
        notificationIntent.putExtra("kode_pertanyaan", kode_pertanyaan);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Logout");//.setIcon(R.drawable.ic_launcher_background);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Logout();
                return true;

//            case 1:
//                finish();
//                return true;
        }
        return false;
    }

    public void keluar(){
        new android.app.AlertDialog.Builder(this)
                .setTitle("Menutup Aplikasi")
                .setMessage("Terimakasih... Anda Telah Menggunakan Aplikasi Ini")
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        finish();
                    }}).show();
    }

    public void Logout(){
        android.app.AlertDialog.Builder ad=new android.app.AlertDialog.Builder(Menu_pengguna.this);
        ad.setTitle("Konfirmasi");
        ad.setMessage("Apakah benar ingin Logout?");

        ad.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(Menu_pengguna.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }});

        ad.setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface arg0, int arg1) {
            }});

        ad.show();
    }

    public void keluarYN(){
        android.app.AlertDialog.Builder ad=new android.app.AlertDialog.Builder(Menu_pengguna.this);
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

    class load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("kode_pengguna", kode_pengguna));
            JSONObject json = jParser.makeHttpRequest(ip + "pertanyaan/notif.php", "GET", params);
            Log.d("show: ", json.toString());
            try {
                adabaru = json.getInt("sukses");
                if (adabaru == 1) {
                    JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
                    final JSONObject myJSON = myObj.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                PESAN = myJSON.getString("PESAN");
                                kode_pertanyaan= myJSON.getString("kode_pertanyaan");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if(adabaru==1) {
                        addNotification1();

                        MediaPlayer mp = MediaPlayer.create(Menu_pengguna.this, R.raw.alarm);
                        if (!mp.isPlaying()) {
                            mp.start();
                            //mp.setLooping(true);
                        }
try {
    vibrate();
}
catch(Exception ee){}

                        Toast.makeText(Menu_pengguna.this, "Ada Data Baru", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }

    void vibrate(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
        //=============================================
    public void keluar2(){
        new AlertDialog.Builder(this)
                .setTitle("Menutup Aplikasi")
                .setMessage("Terimakasih... Anda Telah Menggunakan Aplikasi Ini")
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        finish();
                    }})
                .show();
    }
    public void keluarYN2(){
        AlertDialog.Builder ad=new AlertDialog.Builder(Menu_pengguna.this);
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

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            keluarYN();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
