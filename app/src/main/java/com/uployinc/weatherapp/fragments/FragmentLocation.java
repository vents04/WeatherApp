package com.uployinc.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.uployinc.weatherapp.App;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.City;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.R;
import com.uployinc.weatherapp.adapters.HoursWeatherRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentLocation extends Fragment {

    private ArrayList<String> hours;
    private ArrayList<Bitmap> images;

    private FusedLocationProviderClient fusedLocationClient;
    double latitude, longitude;

    private TextView locationTv, dateTv, temperatureTv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initHoursList();
        initImagesList();
        getLocation();
        initHoursWeatherRecyclerView(view);

        locationTv = view.findViewById(R.id.location);
        dateTv = view.findViewById(R.id.date);
        temperatureTv = view.findViewById(R.id.todayTemperature);

        initDate();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.d("ui","ui");
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("Lat", String.valueOf(latitude));
                    Log.d("Lng", String.valueOf(longitude));

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyAWnEcYShIKTy-c5rNsxlX3BZg86nt49Xw", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray results = response.getJSONArray("results");
                                JSONObject result = (JSONObject) results.get(results.length() - 3);

                                locationTv.setText(result.get("formatted_address").toString());

                                App.getWeatherApiComm().GetPresentDayForecast(new City(latitude, longitude, "Sofia"), new ICallback<DayForecast>() {
                                    @Override
                                    public void onResponse(DayForecast response) {
                                        String tempStr = Math.round(response.getTemperature())+" °C";
                                        temperatureTv.setText(tempStr);
                                    }

                                    @Override
                                    public void onError(Exception exception) {
                                        Log.e("error caught", exception.getMessage());
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }

    public void initDate() {
        LocalDate today = LocalDate.now();
        dateTv.setText(today.getDayOfMonth() + " " + today.getMonth() + " " + today.getYear());
    }

    public void initHoursList() {
        hours = new ArrayList<>();
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        for(int index = currentHourIn24Format; index < currentHourIn24Format + 24; index++) {
            hours.add((index%24 < 10) ? "0" + index%24 + ":00" : index%24 + ":00");
        }
    }

    public void initImagesList() {
        images = new ArrayList<>();
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.weather_sunny_cloudy_rainy);
        for(int index = 0; index <= 24; index++) {
            images.add(icon);
        }
    }

    private void initHoursWeatherRecyclerView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.hoursWeatherRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        HoursWeatherRecyclerViewAdapter hoursWeatherRecyclerViewAdapter = new HoursWeatherRecyclerViewAdapter(hours, images, getContext());
        recyclerView.setAdapter(hoursWeatherRecyclerViewAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_location, container, false);
        return rootView;
    }
}
