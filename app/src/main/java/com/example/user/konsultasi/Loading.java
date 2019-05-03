package com.example.user.konsultasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class Loading extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);


        new Thread() {
            public void run() {
                try{Thread.sleep(1000);}
                catch (Exception e) {}
                Intent i = new Intent(Loading.this, Menu_guest.class);
                Loading.this.finish();
                startActivity(i);
            } }.start();


    }
}
