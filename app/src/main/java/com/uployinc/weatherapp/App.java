package com.uployinc.weatherapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.uployinc.weatherapp.API.IGeoCodingApiComm;
import com.uployinc.weatherapp.API.IWeatherApiComm;
import com.uployinc.weatherapp.API.VolleyGeoCodingApiComm;
import com.uployinc.weatherapp.API.VolleyWeatherApiComm;

public class App extends Application {

    private static Application sApplication;
    private static final String weatherApiUrl = "https://api.openweathermap.org/data/2.5";
    private static final String weatherApiKey = ""; //TODO: take the key from a config file
    private static final String geoCodingApiUrl = "https://api.rateus.uploy.app";
    private static final String geoCodingApiKey = "";
    @SuppressLint("StaticFieldLeak")
    private static IWeatherApiComm weatherApiComm;
    private static IGeoCodingApiComm geoCodingApiComm;

    public static Application getApplication() {
        return sApplication;
    }

    public static IWeatherApiComm getWeatherApiComm(){return weatherApiComm;}
    public static IGeoCodingApiComm getGeoCodingApiComm(){return geoCodingApiComm;}

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        weatherApiComm = VolleyWeatherApiComm.GetInstance(getContext(), weatherApiUrl, weatherApiKey);
        geoCodingApiComm = VolleyGeoCodingApiComm.GetInstance(getContext(), geoCodingApiUrl, geoCodingApiKey);
    }
}
