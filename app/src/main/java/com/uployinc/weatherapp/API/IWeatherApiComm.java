package com.uployinc.weatherapp.API;

import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.Models.Location;
import com.uployinc.weatherapp.Models.WeekForecast;

public interface IWeatherApiComm {
    void GetPresentDayForecast(Location location, ICallback<DayForecast> callback);
    void GetNextDayForecast(Location location, ICallback<DayForecast> callback);
    void GetWeekForecast(Location location, ICallback<WeekForecast> callback);
}
