package com.uployinc.weatherapp.API;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;

public class VolleyGeoCodingApiComm extends VolleyApiComm implements IGeoCodingApiComm{
    //this isn't truly a leak (sources: https://developer.android.com/training/volley/requestqueue#singleton; https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor)
    @SuppressLint("StaticFieldLeak")
    private static VolleyGeoCodingApiComm instance;
    private final String somethingRoute = "/something/route";

    private VolleyGeoCodingApiComm(Context context, String apiUrl) {
        super(context, apiUrl);
    }

    public static synchronized VolleyGeoCodingApiComm GetInstance(Context context, String apiUrl){
        if(instance == null){
            instance = new VolleyGeoCodingApiComm(context, apiUrl);
        }
        return instance;
    }

    @Override
    public Pair<Double, Double> GetCityCoordinates(String cityName) {
        return null;
    }

    @Override
    public String GetCityByCoordinates(double latitude, double longitude) {
        return null;
    }
}
