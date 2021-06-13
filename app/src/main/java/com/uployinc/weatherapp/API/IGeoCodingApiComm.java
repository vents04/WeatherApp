package com.uployinc.weatherapp.API;

import android.location.Location;
import android.util.Pair;

import com.uployinc.weatherapp.Common.ICallback;

public interface IGeoCodingApiComm {
    Pair<Double, Double> GetCityCoordinates(String cityName);
    void GetCityByCoordinates(Location location, ICallback<String> callback);
}
