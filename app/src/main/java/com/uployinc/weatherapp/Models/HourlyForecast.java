package com.uployinc.weatherapp.Models;

import java.util.ArrayList;

public class HourlyForecast {
    private ArrayList<HourForecast> list = new ArrayList<>();

    public ArrayList<HourForecast> getList() {
        return list;
    }

    public void add(HourForecast hourForecast) {
        list.add(hourForecast);
    }
}
