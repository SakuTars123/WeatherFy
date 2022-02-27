package com.demo.weatherfy.servicesimpl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.demo.weatherfy.adapters.WeatherAdapter;
import com.demo.weatherfy.models.CityWeatherDetails;
import com.demo.weatherfy.models.Location;
import com.demo.weatherfy.services.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/*
 * Weather fetching Task Based Service*/
public class WeatherImpl implements Weather {

    Location location;
    RequestQueue requestqueue;
    WeatherAdapter weatherAdapter;
    Context context;
    RecyclerView recyclerView;

    public WeatherImpl(Location location, RequestQueue requestqueue, WeatherAdapter weatherAdapter, Context context, RecyclerView recyclerView) {
        this.location = location;
        this.requestqueue = requestqueue;
        this.weatherAdapter = weatherAdapter;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public void getHourlyDetails() {
        fetchWeather(requestqueue);
        return ;
    }

    public void fetchWeather(RequestQueue requestQueue) {

        int latitude = location.getLatitude();
        int longitude = location.getLongitude();
        String cityName = location.getName();
        Boolean exactFound = location.isExactLocationFound();
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&exclude=minutely,daily&appid=d0df7760496a2f5f317cab2e0a3dc65b";


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CityWeatherDetails currentDetails = new CityWeatherDetails();
                List<CityWeatherDetails> cityWeatherDetailsList = new ArrayList<>();

                try {

                    Log.i("GettingLatitude","Inside weather");
                    JSONObject jsonObject = response.getJSONObject("current");
                    int  currentTemp= jsonObject.getInt("temp");
                    currentTemp=currentTemp-273;
                    int currentHumidity = jsonObject.getInt("humidity");
                    int feelsLike = jsonObject.getInt("feels_like");
                    feelsLike=feelsLike-273;
                    int windSpeed = jsonObject.getInt("wind_gust");
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    JSONObject weatherObject = (JSONObject) weatherArray.get(0);
                    String mainDescription = weatherObject.getString("main");
                    String detailedDescription = weatherObject.getString("description");


                    String currentLocationName = cityName;

                    currentDetails = new CityWeatherDetails(
                            currentLocationName,currentTemp,currentHumidity,mainDescription,detailedDescription
                            ,feelsLike,windSpeed, exactFound);

                    cityWeatherDetailsList = new ArrayList<>();
                    JSONArray hourlyData = response.getJSONArray("hourly");

                    for(int i=0; i<hourlyData.length();i++){
                        JSONObject hourlyWeatherData = (JSONObject) hourlyData.get(i);
                        int  hourlyCurrentTemp= hourlyWeatherData.getInt("temp");
                        hourlyCurrentTemp=hourlyCurrentTemp-273;
                        int hourlyCurrentHumidity = hourlyWeatherData.getInt("humidity");
                        int hourlyFeelsLike = hourlyWeatherData.getInt("feels_like");
                        hourlyFeelsLike=hourlyFeelsLike-273;
                        int hourlyWindSpeed = hourlyWeatherData.getInt("wind_gust");
                        JSONArray hourlyWeatherArray = hourlyWeatherData.getJSONArray("weather");
                        JSONObject hourlyWeatherObject = (JSONObject) hourlyWeatherArray.get(0);
                        String hourlyMainDescription = hourlyWeatherObject.getString("main");
                        String hourlyDetailedDescription = hourlyWeatherObject.getString("description");

                        CityWeatherDetails hourlyDetails = new CityWeatherDetails(
                                currentLocationName,hourlyCurrentTemp,hourlyCurrentHumidity,hourlyMainDescription,hourlyDetailedDescription
                                ,hourlyFeelsLike,hourlyWindSpeed, exactFound);

                        cityWeatherDetailsList.add(hourlyDetails);
                    }



                } catch (JSONException e) {
                    Toast.makeText(context, "Some error. Please try again.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                    weatherAdapter = new WeatherAdapter(cityWeatherDetailsList,currentDetails,context);
                    recyclerView.setAdapter(weatherAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);

    }
}
