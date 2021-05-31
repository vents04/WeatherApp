package com.uployinc.weatherapp.API;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.Forecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class VolleyApiComm extends AppCompatActivity implements IApiComm {
    //this isn't truly a leak (sources: https://developer.android.com/training/volley/requestqueue#singleton; https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor)
    @SuppressLint("StaticFieldLeak")
    private static VolleyApiComm instance;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private RequestQueue requestQueue;
    private static String apiUrl;
    private static final String somethingRoute = "/api/something";

    public static String getApiUrl() {
        return apiUrl;
    }

    public static void setApiUrl(String apiUrl) {
        VolleyApiComm.apiUrl = apiUrl;
    }

    private VolleyApiComm(Context context, String apiUrl) {
        VolleyApiComm.context = context;
        VolleyApiComm.apiUrl = apiUrl;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static synchronized VolleyApiComm getInstance(Context context, String apiUrl) {
        if (instance == null) instance = new VolleyApiComm(context, apiUrl);
        return instance;
    }

    //TODO: implement the actual methods for interfacing with the api
    public void updateQuestionnaire(Forecast forecast, String token, ICallback<JSONObject> callback) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("title", forecast.toString());
            JSONObject questions = new JSONObject();
            postData.put("questions", questions);

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