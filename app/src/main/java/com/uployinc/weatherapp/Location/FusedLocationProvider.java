package com.uployinc.weatherapp.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.uployinc.weatherapp.App;

public class FusedLocationProvider implements ILocationModule {
    private static FusedLocationProvider instance;
    private final FusedLocationProviderClient client;

    public static synchronized FusedLocationProvider GetInstance(Context context){
        if(instance == null) instance = new FusedLocationProvider(context);
        return instance;
    }

    private FusedLocationProvider(Context context) {
        client = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public Task<Location> GetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //should not happen
            return null;
        }
        return client.getLastLocation();
    }
}
