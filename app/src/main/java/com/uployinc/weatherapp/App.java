package com.uployinc.weatherapp;

import android.app.Application;
import android.content.Context;

import com.uployinc.weatherapp.API.IGeoCodingApiComm;
import com.uployinc.weatherapp.API.IWeatherApiComm;
import com.uployinc.weatherapp.API.VolleyGeoCodingApiComm;
import com.uployinc.weatherapp.API.VolleyWeatherApiComm;
import com.uployinc.weatherapp.DataAccess.PostgreDBService;
import com.uployinc.weatherapp.Location.FusedLocationProvider;
import com.uployinc.weatherapp.Location.ILocationModule;

public class App extends Application {

    private static final String weatherApiUrl = "https://api.openweathermap.org/data/2.5";
    private static final String weatherApiKey = ""; //TODO: take the key from a config file
    private static final String geoCodingApiUrl = "https://maps.googleapis.com/maps/api/geocode";
    private static final String geoCodingApiKey = "";

    private static Application sApplication;
    private static IWeatherApiComm weatherApiComm;
    private static IGeoCodingApiComm geoCodingApiComm;
    private static ILocationModule locationModule;
    private static PostgreDBService DBService;
    private static final String SQLServerUsername = "postgres";
    private static final String SQLServerPassword = "admin";
    private static final String SQLServerAddress = "127.0.0.1:5432";

    public static Application getApplication(){return sApplication;}
    public static IWeatherApiComm getWeatherApiComm(){return weatherApiComm;}
    public static IGeoCodingApiComm getGeoCodingApiComm(){return geoCodingApiComm;}
    public static ILocationModule getLocationModule(){return locationModule;}
    public static PostgreDBService getDBService(){return DBService;}

    public static Context getContext(){return getApplication().getApplicationContext();}

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        weatherApiComm = VolleyWeatherApiComm.GetInstance(getContext(), weatherApiUrl, weatherApiKey);
        geoCodingApiComm = VolleyGeoCodingApiComm.GetInstance(getContext(), geoCodingApiUrl, geoCodingApiKey);
        locationModule = FusedLocationProvider.GetInstance(getContext());
        try {
            DBService = PostgreDBService.GetInstance(SQLServerUsername, SQLServerPassword, SQLServerAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
