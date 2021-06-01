package com.uployinc.weatherapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.uployinc.weatherapp.MainActivity;
import com.uployinc.weatherapp.R;
import com.uployinc.weatherapp.adapters.HoursWeatherRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentLocation extends Fragment {

    private ArrayList<String> hours;
    private ArrayList<Bitmap> images;

    private FusedLocationProviderClient fusedLocationClient;
    final private int REQUEST_LOCATION = 1001;
    double latitude, longitude;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initHoursList();
        initImagesList();
        getLocation();
        initHoursWeatherRecyclerView(view);
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("LAT", String.valueOf(latitude));
                    Log.d("LNG", String.valueOf(longitude));
                }
            }
        });
    }

    public void initHoursList() {
        hours = new ArrayList<>();
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        for(int index = currentHourIn24Format; index <= currentHourIn24Format + 24; index++) {
            hours.add((index%24 < 10) ? "0" + String.valueOf(index%24) + ":00" : String.valueOf(index%24) + ":00");
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
