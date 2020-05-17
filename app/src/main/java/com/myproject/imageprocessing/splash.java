package com.myproject.imageprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView logo = (TextView) findViewById(R.id.logo);
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds

                    sleep(4*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),Dashboard.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }

            }
        };
        // start thread
        background.start();
        Toast.makeText(getApplicationContext(),"WELCOME",Toast.LENGTH_LONG).show();
    }
}
