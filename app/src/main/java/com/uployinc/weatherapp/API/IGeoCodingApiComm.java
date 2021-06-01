package com.uployinc.weatherapp.API;

import android.util.Pair;

public interface IGeoCodingApiComm {
    Pair<Double, Double> GetCityCoordinates(String cityName);
    String GetCityByCoordinates(double latitude, double longitude);
}
