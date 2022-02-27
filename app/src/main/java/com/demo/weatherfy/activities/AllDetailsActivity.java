package com.demo.weatherfy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.weatherfy.R;
import com.demo.weatherfy.models.CityWeatherDetails;
import com.google.gson.Gson;

public class AllDetailsActivity extends AppCompatActivity {

    TextView cityName;
    TextView currentTemp;
    TextView currentHumidity;
    TextView mainCurrentDescription;
    TextView detailedCurrentDescription;
    TextView feelsLike;
    TextView windSpeed;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_details);
        cityName=findViewById(R.id.detailedLocation);
        currentTemp=findViewById(R.id.detailedCurrentTemp);
        currentHumidity=findViewById(R.id.deatiledHumidityTextView);
        mainCurrentDescription=findViewById(R.id.detailedDescription);
        detailedCurrentDescription=findViewById(R.id.detailedMoreDescription);
        feelsLike=findViewById(R.id.detailedFeelLike);
        windSpeed=findViewById(R.id.detailedWindSpeed);
        imageView=findViewById(R.id.detailedImageView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Gson gson = new Gson();
        CityWeatherDetails cityWeatherDetails = gson.fromJson(getIntent().getStringExtra("PassDetailsAsJson"), CityWeatherDetails.class);
        cityName.setText(cityWeatherDetails.getCityName());
        currentTemp.setText(cityWeatherDetails.getCurrentTemp()+" °C");
        currentHumidity.setText("Humidity is "+cityWeatherDetails.getCurrentHumidity()+" %");
        mainCurrentDescription.setText("Weather is "+cityWeatherDetails.getMainCurrentDescription()+"");
        detailedCurrentDescription.setText("Detailed weather is "+cityWeatherDetails.getDetailedCurrentDescription()+"");
        feelsLike.setText("Temp feels like "+cityWeatherDetails.getFeelsLike()+" °C");
        windSpeed.setText("Gust speed is "+cityWeatherDetails.getWindSpeed()+" miph");
        if(cityWeatherDetails.getDetailedCurrentDescription().toLowerCase().contains("cloud"))
            imageView.setBackgroundResource(R.drawable.ic_undraw_autumn_re_rwy0);
        else
            imageView.setBackgroundResource(R.drawable.ic_undraw_sunny_day_re_gyxr);


    }
}