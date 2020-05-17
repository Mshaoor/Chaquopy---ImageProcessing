package com.myproject.imageprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;

import java.time.Instant;

public class Dashboard extends AppCompatActivity {
Button registration,recognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        registration= (Button)findViewById(R.id.registration);
        recognition= (Button)findViewById(R.id.recognition);




        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),   MainActivity.class);
                startActivity(myIntent);
            }
        });




        recognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent myIntent = new Intent(getApplicationContext(),   recognition.class);
                //startActivity(myIntent);
            }
        });




    }
}
