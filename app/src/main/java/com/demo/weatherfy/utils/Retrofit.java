package com.demo.weatherfy.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/*
Singleton class to make only one instance at any time.
 */
public class Retrofit {

    private RequestQueue requestQueue;
    private static  Retrofit mInstance;


    private Retrofit(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized Retrofit getmInstance(Context context){
        if (mInstance == null){
            mInstance = new Retrofit(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){return requestQueue;}


}
