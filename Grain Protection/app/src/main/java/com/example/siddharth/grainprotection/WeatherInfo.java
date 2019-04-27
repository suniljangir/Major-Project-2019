package com.example.siddharth.grainprotection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WeatherInfo extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        Intent intent = getIntent();
        String markerTitle= intent.getExtras().getString("markertitle");

        textView=(TextView)findViewById(R.id.testing);
        textView.setText(markerTitle);
    }
}
