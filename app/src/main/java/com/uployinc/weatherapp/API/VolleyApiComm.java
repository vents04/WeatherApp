package com.uployinc.weatherapp.API;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyApiComm extends AppCompatActivity {
    protected Context context;
    protected RequestQueue requestQueue;
    protected String apiUrl;

    protected VolleyApiComm(Context context, String apiUrl) {
        this.context = context;
        this.apiUrl = apiUrl;
        requestQueue = getRequestQueue();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
