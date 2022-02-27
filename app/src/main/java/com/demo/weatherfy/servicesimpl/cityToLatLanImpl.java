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
import com.demo.weatherfy.adapters.WeatherAdapter;
import com.demo.weatherfy.models.Location;
import com.demo.weatherfy.services.CityToLatLan;
import com.demo.weatherfy.utils.LoadingBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*
 * Task Based Service*/
public class cityToLatLanImpl implements CityToLatLan {

    RequestQueue requestQueue;
    WeatherAdapter weatherAdapter;
    Context context;
    RecyclerView recyclerView;

    public cityToLatLanImpl(RequestQueue requestQueue, WeatherAdapter weatherAdapter, Context context, RecyclerView recyclerView) {
        this.requestQueue = requestQueue;
        this.weatherAdapter = weatherAdapter;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public void GetLatLanFromCity(String cityName) {
        fetchLocation(cityName,requestQueue);

    }


    public void fetchLocation(String cityName, RequestQueue requestQueue) {

        String url = "https://api.openweathermap.org/geo/1.0/direct?q="+cityName+"&limit=1&appid=d0df7760496a2f5f317cab2e0a3dc65b";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        int s1 = jsonObject.getInt("lat");
                        int s2 = jsonObject.getInt("lon");
                        String name = jsonObject.getString("name");

                        Boolean exactLocationFound = name.toLowerCase().equals(cityName.toLowerCase());

                        Location currentLocation = new Location(
                                s1,s2,name,exactLocationFound
                        );
                        WeatherImpl weather = new WeatherImpl(currentLocation,requestQueue, weatherAdapter, context, recyclerView);
                        weather.getHourlyDetails();


                    }catch (JSONException e){
                        LoadingBar.LoadingTime(context,context.getResources(),false);
                        Toast.makeText(context, "City Not Found. Please retry.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        requestQueue.add(jsonArrayRequest);

    }
}
