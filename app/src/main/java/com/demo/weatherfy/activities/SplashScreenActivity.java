package com.demo.weatherfy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.weatherfy.R;
/*
Class for loading the splash screen.
Improvement: Can render the image dynamically.
* */
public class SplashScreenActivity extends AppCompatActivity {

    private Button startSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        startSearch=findViewById(R.id.searchButton);
        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }
}