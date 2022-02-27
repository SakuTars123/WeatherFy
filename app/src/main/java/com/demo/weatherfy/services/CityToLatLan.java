package com.demo.weatherfy.services;

import com.android.volley.RequestQueue;
import com.demo.weatherfy.models.Location;
/*
* Interface to implement loose coupling*/
public interface CityToLatLan {

    public void GetLatLanFromCity(String cityName);

}
