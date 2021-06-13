package com.uployinc.weatherapp.API;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.android.volley.toolbox.JsonObjectRequest;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.Models.HourForecast;
import com.uployinc.weatherapp.Models.HourlyForecast;
import com.uployinc.weatherapp.Models.WeekForecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Locale;

import static com.android.volley.Request.Method.GET;

public class VolleyWeatherApiComm extends VolleyApiComm implements IWeatherApiComm {
    //this isn't truly a leak (sources: https://developer.android.com/training/volley/requestqueue#singleton; https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor)
    @SuppressLint("StaticFieldLeak")
    private static VolleyWeatherApiComm instance;
    private final String somethingRoute = "/something/route";
    private String locationRoute(Location location) {
        return String.format(Locale.getDefault(),"/onecall?units=metric&lat=%f&lon=%f&appid=%s", location.getLatitude(), location.getLongitude(), apiKey);
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

    @Override
    public void GetCurrentForecast(Location location, ICallback<DayForecast> callback) {
        addToRequestQueue(new JsonObjectRequest(GET, apiUrl + locationRoute(location), new JSONObject(), response -> {
            DayForecast forecast = new DayForecast();
            try {
                JSONObject current = response.getJSONObject("current");
                forecast.setTargetTime(Instant.ofEpochSecond(current.getInt("dt")));
                forecast.setTemperature(current.getDouble("temp"));
                forecast.setFeelsLike(current.getDouble("feels_like"));
                forecast.setPressure(current.getDouble("pressure"));
                forecast.setRelativeHumidity(current.getDouble("humidity"));
                forecast.setUVIndex(current.getInt("uvi"));
                forecast.setCloudCover(current.getDouble("clouds"));
                forecast.setWindSpeed(current.getDouble("wind_speed"));
                forecast.setWindDirection(current.getDouble("wind_deg"));
                forecast.setProbabilityOfPrecipitation(current.getDouble("pop"));
                JSONObject weather = current.getJSONObject("weather");
                forecast.setWeatherDescription(weather.getString("description"));
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

    @Override
    public void GetHourlyForecast(Location location, ICallback<HourlyForecast> callback) {
        addToRequestQueue(new JsonObjectRequest(GET, apiUrl + locationRoute(location), new JSONObject(), response -> {
            HourlyForecast forecast = new HourlyForecast();
            try {
                JSONArray daysList = response.getJSONArray("daily");
                for(int i=0;i<daysList.length();i++){
                    JSONObject current = daysList.getJSONObject(i);
                    HourForecast hourForecast = new HourForecast();
                    hourForecast.setTargetTime(Instant.ofEpochSecond(current.getInt("dt")));
                    hourForecast.setTemperature(current.getDouble("temp"));
                    hourForecast.setFeelsLike(current.getDouble("feels_like"));
                    hourForecast.setPressure(current.getDouble("pressure"));
                    hourForecast.setRelativeHumidity(current.getDouble("humidity"));
                    hourForecast.setUVIndex(current.getInt("uvi"));
                    hourForecast.setCloudCover(current.getDouble("clouds"));
                    hourForecast.setWindSpeed(current.getDouble("wind_speed"));
                    hourForecast.setWindDirection(current.getDouble("wind_deg"));
                    hourForecast.setProbabilityOfPrecipitation(current.getDouble("pop"));
                    JSONObject weather = current.getJSONObject("weather");
                    hourForecast.setWeatherDescription(weather.getString("description"));
                    forecast.add(hourForecast);
                }
            } catch (JSONException e) {
                callback.onError(e);
            }
            callback.onResponse(forecast);
        }, callback::onError));
    }
}