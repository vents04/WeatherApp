package com.uployinc.weatherapp.API;

import android.location.Location;

import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.Models.HourlyForecast;
import com.uployinc.weatherapp.Models.WeekForecast;

public interface IWeatherApiComm {
    void GetCurrentForecast(Location location, ICallback<DayForecast> callback);
    void GetNextDayForecast(Location location, ICallback<DayForecast> callback);
    void GetWeekForecast(Location location, ICallback<WeekForecast> callback);
    void GetHourlyForecast(Location location, ICallback<HourlyForecast> callback);
}
