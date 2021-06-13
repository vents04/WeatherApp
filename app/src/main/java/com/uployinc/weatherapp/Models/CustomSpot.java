package com.uployinc.weatherapp.Models;

import java.util.UUID;

public class CustomSpot extends Spot {
    private UUID userId;

    public CustomSpot(double latitude, double longitude, String name, String description, UUID userId) {
        super(latitude, longitude, name, description);
        this.userId = userId;
    }

    public CustomSpot(double latitude, double longitude, String name, UUID userId) {
        this(latitude, longitude, name, null, userId);
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
