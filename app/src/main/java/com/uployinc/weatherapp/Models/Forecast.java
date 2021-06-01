package com.uployinc.weatherapp.Models;

import java.time.Instant;

public abstract class Forecast {
    protected Instant targetTime;
    protected double Temperature;
    protected double FeelsLike;
    protected double Pressure;
    protected double RelativeHumidity;
    protected int UVIndex;
    protected double CloudCover;
    protected double WindSpeed;
    protected double WindDirection;
    protected double ProbabilityForPrecipitation;
    protected double Precipitation;

    public Instant getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Instant targetTime) {
        this.targetTime = targetTime;
    }

    public double getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }

    public double getFeelsLike() {
        return FeelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        FeelsLike = feelsLike;
    }

    public double getPressure() {
        return Pressure;
    }

    public void setPressure(double pressure) {
        Pressure = pressure;
    }

    public double getRelativeHumidity() {
        return RelativeHumidity;
    }

    public void setRelativeHumidity(double relativeHumidity) {
        RelativeHumidity = relativeHumidity;
    }

    public int getUVIndex() {
        return UVIndex;
    }

    public void setUVIndex(int UVIndex) {
        this.UVIndex = UVIndex;
    }

    public double getCloudCover() {
        return CloudCover;
    }

    public void setCloudCover(double cloudCover) {
        CloudCover = cloudCover;
    }

    public double getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        WindSpeed = windSpeed;
    }

    public double getWindDirection() {
        return WindDirection;
    }

    public void setWindDirection(double windDirection) {
        WindDirection = windDirection;
    }

    public double getProbabilityForPrecipitation() {
        return ProbabilityForPrecipitation;
    }

    public void setProbabilityForPrecipitation(double probabilityForPrecipitation) {
        ProbabilityForPrecipitation = probabilityForPrecipitation;
    }

    public double getPrecipitation() {
        return Precipitation;
    }

    public void setPrecipitation(double precipitation) {
        Precipitation = precipitation;
    }
}
