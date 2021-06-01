package com.uployinc.weatherapp.Models;

public class WeekForecast {
    private final int daysInWeek = 7;
    private DayForecast[] days = new DayForecast[daysInWeek];

    public WeekForecast(DayForecast[] days) {
        this.days = days;
    }

    public double getAverageTemperature() {
        double average = 0;
        for(int i=0;i<daysInWeek;i++){
            average+=days[i].Temperature/daysInWeek;
        }
        return average;
    }

    public double getTotalPrecipitation() {
        double sum = 0;
        for(int i=0;i<daysInWeek;i++){
            sum+=days[i].Precipitation;
        }
        return sum;
    }
}
