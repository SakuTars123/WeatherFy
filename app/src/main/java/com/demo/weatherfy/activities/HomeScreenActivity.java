package com.demo.weatherfy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.demo.weatherfy.R;
import com.demo.weatherfy.adapters.WeatherAdapter;
import com.demo.weatherfy.servicesimpl.cityToLatLanImpl;
import com.demo.weatherfy.utils.LoadingBar;
import com.demo.weatherfy.utils.Retrofit;
/*
 * Main working class*/
public class HomeScreenActivity extends AppCompatActivity {

    SearchView cityNameSearch;
    static RecyclerView recyclerViewCityData;
    RequestQueue requestQueue;
    View currentTemperatureView;
    static TextView currentTemperatureTextView;
    static TextView currentLocationTextView;
    static TextView currentFeelsLikeTextView;
    WeatherAdapter weatherAdapter;
    Context context;
    static ImageView weatherImage;
    static TextView humidityTextView;
    static TextView windSpeedTextView;
    static TextView hourlyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCityData = findViewById(R.id.recyclerViewCityData);
        cityNameSearch = findViewById(R.id.searchViewCity);
        currentTemperatureView=findViewById(R.id.includeCurrentDetails);
        requestQueue = Retrofit.getmInstance(this).getRequestQueue();
        context = HomeScreenActivity.this;
        weatherImage=findViewById(R.id.watherImageView);
        humidityTextView=findViewById(R.id.currentHumidityTextBox);
        windSpeedTextView=findViewById(R.id.currentWindSpeedTextBox);
        hourlyText=findViewById(R.id.HourlyTextView);
        recyclerViewCityData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        SetCurrentTemperatureView();
        SetSearchView();
        

    }

    private void SetCurrentTemperatureView() {
        currentTemperatureTextView = currentTemperatureView.findViewById(R.id.currentTempTextView);
        currentLocationTextView = currentTemperatureView.findViewById(R.id.locationTextView);
        currentFeelsLikeTextView = currentTemperatureView.findViewById(R.id.feelsLikeTextView);
        currentTemperatureTextView.setText("Search");
        weatherImage.setBackgroundResource(R.drawable.ic_undraw_current_location_re_j130);

    }

    private void SetSearchView() {
        cityNameSearch.setQueryHint("Search");
        cityNameSearch.setFocusable(false);
        cityNameSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String city) {
                cityNameSearch.clearFocus();
                cityToLatLanImpl cityToLatLan = new cityToLatLanImpl(requestQueue, weatherAdapter,context,recyclerViewCityData);
                LoadingBar.LoadingTime(context,getResources(),true);
                cityToLatLan.GetLatLanFromCity(city);
                recyclerViewCityData.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentTemperatureTextView.setText("Search");
                weatherImage.setBackgroundResource(R.drawable.ic_undraw_current_location_re_j130);
                currentLocationTextView.setText("");
                humidityTextView.setText("");
                windSpeedTextView.setText("");
                currentFeelsLikeTextView.setText("");
                hourlyText.setVisibility(View.GONE);
                recyclerViewCityData.setVisibility(View.GONE);
                return false;
            }
        });
    }

    public static void SetCurrentDetails(int currentTemperature, int feelsLike, String currentLocation, boolean cloudInday, int humidity, int windSpeed, Boolean exactNameFound){
        recyclerViewCityData.setVisibility(View.VISIBLE);
        hourlyText.setVisibility(View.VISIBLE);
        if(exactNameFound)
        currentLocationTextView.setText(currentLocation);
        else {
            currentLocationTextView.setTextSize(20);
            currentLocationTextView.setText("Did you mean? "+currentLocation);
        }
        currentTemperatureTextView.setText(currentTemperature+" °C");
        currentFeelsLikeTextView.setText("Feels Like: "+feelsLike+" °C");
        if(cloudInday)
             weatherImage.setBackgroundResource(R.drawable.ic_undraw_autumn_re_rwy0);
        else
            weatherImage.setBackgroundResource(R.drawable.ic_undraw_sunny_day_re_gyxr);
        humidityTextView.setText("Humidity is "+humidity+ "%");
        windSpeedTextView.setText("Wind speed is "+windSpeed+" miph");

    }
}