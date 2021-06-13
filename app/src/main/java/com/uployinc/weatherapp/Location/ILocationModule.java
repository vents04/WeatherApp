package com.uployinc.weatherapp.Location;

import android.location.Location;

import com.google.android.gms.tasks.Task;

public interface ILocationModule {
    Task<Location> GetCurrentLocation();
}
