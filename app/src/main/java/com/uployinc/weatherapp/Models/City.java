package com.uployinc.weatherapp.Models;

public class City extends Location {
    //TODo: add city-specific stuff

    public City(double latitude, double longitude, String name, String description) {
        super(latitude, longitude, name, description);
    }

    public City(double latitude, double longitude, String name) {
        this(latitude, longitude, name, null);
    }
}
