package com.uployinc.weatherapp.Models;

import java.time.LocalDateTime;

public abstract class Forecast {
    protected LocalDateTime targetTime;
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
}
