package com.uployinc.weatherapp.Models;

import android.util.Pair;

public abstract class Location {
    private double latitude;
    private double longitude;
    private String name;
    private String description;

    public Location(double latitude, double longitude, String name, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
    }
    public Location(double latitude, double longitude, String name) {
        this(latitude, longitude, name, null);
    }

    public Pair<Double, Double> getCoordinates() {
        return new Pair<>(latitude, longitude);
    }

    public void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
