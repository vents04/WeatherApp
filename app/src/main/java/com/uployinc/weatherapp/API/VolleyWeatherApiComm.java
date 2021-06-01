package com.uployinc.weatherapp.API;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.Forecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class VolleyWeatherApiComm extends VolleyApiComm implements IWeatherApiComm {
    //this isn't truly a leak (sources: https://developer.android.com/training/volley/requestqueue#singleton; https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor)
    @SuppressLint("StaticFieldLeak")
    private static VolleyWeatherApiComm instance;
    private final String somethingRoute = "/something/route";

    private VolleyWeatherApiComm(Context context, String apiUrl) {
        super(context, apiUrl);
    }

    public static synchronized VolleyWeatherApiComm GetInstance(Context context, String apiUrl){
        if(instance == null){
            instance = new VolleyWeatherApiComm(context, apiUrl);
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
}