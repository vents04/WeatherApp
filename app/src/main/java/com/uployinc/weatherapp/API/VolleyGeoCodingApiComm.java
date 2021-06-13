package com.uployinc.weatherapp.API;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Pair;

import com.android.volley.toolbox.JsonObjectRequest;
import com.uployinc.weatherapp.Common.ICallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import static com.android.volley.Request.Method.GET;

public class VolleyGeoCodingApiComm extends VolleyApiComm implements IGeoCodingApiComm{
    //this isn't truly a leak (sources: https://developer.android.com/training/volley/requestqueue#singleton; https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor)
    @SuppressLint("StaticFieldLeak")
    private static VolleyGeoCodingApiComm instance;
    private String getLocationRoute(Location location){
        return String.format(Locale.getDefault(),"/json?latlng=%f,%f&key=%s", location.getLatitude(), location.getLongitude(), apiKey);
    }

    private VolleyGeoCodingApiComm(Context context, String apiUrl, String apiKey) {
        super(context, apiUrl, apiKey);
    }

    public static synchronized VolleyGeoCodingApiComm GetInstance(Context context, String apiUrl, String apiKey){
        if(instance == null){
            instance = new VolleyGeoCodingApiComm(context, apiUrl, apiKey);
        }
        return instance;
    }

    @Override
    public Pair<Double, Double> GetCityCoordinates(String cityName) {
        return null;
    }

    @Override
    public void GetCityByCoordinates(Location location, ICallback<String> callback) {
        addToRequestQueue(new JsonObjectRequest(GET, apiUrl + getLocationRoute(location), new JSONObject(), response -> {
            try {
                JSONArray results = response.getJSONArray("results");
                JSONObject result = (JSONObject) results.get(results.length() - 3);
                String locationName = result.get("formatted_address").toString();
                callback.onResponse(locationName);
            }catch(Exception e){
                callback.onError(e);
            }
        }, callback::onError));
    }
}
