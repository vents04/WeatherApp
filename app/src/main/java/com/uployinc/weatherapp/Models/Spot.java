package com.uployinc.weatherapp.Models;

import android.util.Pair;

import com.uployinc.weatherapp.Common.IIndexable;

import java.util.UUID;

public abstract class Spot implements IIndexable<UUID> {
    protected final UUID id;
    protected double latitude;
    protected double longitude;
    protected String name;
    protected String description;

    public Spot(double latitude, double longitude, String name, String description) {
        this.id=UUID.randomUUID();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
    }
    public Spot(double latitude, double longitude, String name) {
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

    public UUID getId() {
        return id;
    }
}
