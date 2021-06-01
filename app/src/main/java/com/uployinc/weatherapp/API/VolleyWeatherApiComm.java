package com.uployinc.weatherapp.API;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;

import com.android.volley.toolbox.JsonObjectRequest;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.Models.Forecast;
import com.uployinc.weatherapp.Models.Location;
import com.uployinc.weatherapp.Models.WeekForecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class VolleyWeatherApiComm extends VolleyApiComm implements IWeatherApiComm {
    //this isn't truly a leak (sources: https://developer.android.com/training/volley/requestqueue#singleton; https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor)
    @SuppressLint("StaticFieldLeak")
    private static VolleyWeatherApiComm instance;
    private final String somethingRoute = "/something/route";
    private String locationRoute(Location location) {
        Pair<Double, Double> coordinates = location.getCoordinates();
        return String.format(Locale.getDefault(),"/onecall?units=metric&lat=%f&lon=%f&appid=%s", coordinates.first, coordinates.second, apiKey);
    }

    private VolleyWeatherApiComm(Context context, String apiUrl, String apiKey) {
        super(context, apiUrl, apiKey);
    }

    public static synchronized VolleyWeatherApiComm GetInstance(Context context, String apiUrl, String apiKey){
        if(instance == null){
            instance = new VolleyWeatherApiComm(context, apiUrl, apiKey);
        }
        return instance;
    }

    //TODO: implement the actual methods for interfacing with the api
    public void updateQuestionnaire(Forecast forecast, String token, ICallback<JSONObject> callback) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("title", forecast.toString());

            addToRequestQueue(new JsonObjectRequest(POST, apiUrl + somethingRoute, postData, callback::onResponse, callback::onError) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("x-auth-token", token);
                    return headers;
                }
            });
        } catch (JSONException e) {
            callback.onError(e);
        }
    }

    @Override
    public void GetPresentDayForecast(Location location, ICallback<DayForecast> callback) {
        addToRequestQueue(new JsonObjectRequest(GET, apiUrl + locationRoute(location), new JSONObject(), response -> {
            DayForecast forecast = new DayForecast();
            try {
                response = response.getJSONObject("current");
                forecast.setTargetTime(Instant.ofEpochSecond(response.getInt("dt")));
                forecast.setTemperature(response.getDouble("temp"));
                forecast.setFeelsLike(response.getDouble("feels_like"));
                forecast.setPressure(response.getDouble("pressure"));
                forecast.setRelativeHumidity(response.getDouble("humidity"));
                forecast.setUVIndex(response.getInt("uvi"));
                forecast.setCloudCover(response.getDouble("clouds"));
                forecast.setWindSpeed(response.getDouble("wind_speed"));
                forecast.setWindDirection(response.getDouble("wind_deg"));
            } catch (JSONException e) {
                callback.onError(e);
            }
            callback.onResponse(forecast);
        }, callback::onError));
    }

    @Override
    public void GetNextDayForecast(Location location, ICallback<DayForecast> callback) {

    }

    @Override
    public void GetWeekForecast(Location location, ICallback<WeekForecast> callback) {

    }
}