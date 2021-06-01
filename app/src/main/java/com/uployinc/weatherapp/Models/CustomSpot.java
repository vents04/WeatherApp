package com.uployinc.weatherapp.Models;

public class CustomSpot extends Location{
    private String userId;

    public CustomSpot(double latitude, double longitude, String name, String description, String userId) {
        super(latitude, longitude, name, description);
        this.userId = userId;
    }

    public CustomSpot(double latitude, double longitude, String name, String userId) {
        this(latitude, longitude, name, null, userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
