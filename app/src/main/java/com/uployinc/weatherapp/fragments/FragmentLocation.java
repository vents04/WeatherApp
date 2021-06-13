package com.uployinc.weatherapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uployinc.weatherapp.App;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.R;
import com.uployinc.weatherapp.adapters.HoursWeatherRecyclerViewAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentLocation extends Fragment {

    private ArrayList<String> hours;
    private ArrayList<Bitmap> images;

    Location location;

    private TextView locationTv, dateTv, temperatureTv, humidityTv, precipitationChanceTv, windSpeedTv, weatherDescriptionTv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initHoursList();
        initImagesList();
        getLocation();
        initHoursWeatherRecyclerView(view);

        locationTv = view.findViewById(R.id.location);
        dateTv = view.findViewById(R.id.date);
        temperatureTv = view.findViewById(R.id.currentTemperatureText);
        windSpeedTv = view.findViewById(R.id.currentWindSpeedText);
        humidityTv = view.findViewById(R.id.currentHumidityText);
        precipitationChanceTv = view.findViewById(R.id.currentPrecipitationChanceText);
        weatherDescriptionTv = view.findViewById(R.id.currentWeatherDescriptionText);

        setDateTvText();
        setCurrentWeatherInformationTvs();
        setLocationTvText();
    }

    public void setCurrentWeatherInformationTvs(){
        App.getWeatherApiComm().GetCurrentForecast(location, new ICallback<DayForecast>() {
            @Override
            public void onResponse(DayForecast response) {
                String temperatureStr = Math.round(response.getTemperature()) + " °C";
                String humidityStr = Math.round(response.getRelativeHumidity()) + "%";
                String windSpeedStr = Math.round(response.getWindSpeed() * 3.6) + " km/h";
                String precipitationChanceStr = Math.round(response.getProbabilityForPrecipitation()) + "%";
                String weatherDescriptionStr = response.getWeatherDescription();
                temperatureTv.setText(temperatureStr);
                humidityTv.setText(humidityStr);
                precipitationChanceTv.setText(precipitationChanceStr);
                windSpeedTv.setText(windSpeedStr);
                weatherDescriptionTv.setText(weatherDescriptionStr);
            }

            @Override
            public void onError(Exception exception) {
                Log.e("error caught", exception.getMessage());
            }
        });
    }

    public void setLocationTvText(){
        App.getGeoCodingApiComm().GetCityByCoordinates(location, new ICallback<String>() {
            @Override
            public void onResponse(String response) {
                locationTv.setText(response);
            }

            @Override
            public void onError(Exception exception) {
                Log.e("error caught", exception.getMessage());
            }
        });
    }

    public void getLocation() {
        App.getLocationModule().GetCurrentLocation().addOnSuccessListener((location) -> {
            if (location != null) {
                this.location = location;
            }else{
                Log.e("error", "location was null");
            }
        });
    }

    public void setDateTvText() {
        LocalDate today = LocalDate.now();
        String formattedDate = today.getDayOfMonth() + " " + today.getMonth() + " " + today.getYear();
        dateTv.setText(formattedDate);
    }

    public void initHoursList() {
        hours = new ArrayList<>();
        int currentHourIn24Format = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        for (int index = currentHourIn24Format; index <= currentHourIn24Format + 24; index++) {
            hours.add((index % 24 < 10) ? "0" + index % 24 + ":00" : index % 24 + ":00");
        }
    }

    public void initImagesList() {
        images = new ArrayList<>();
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.weather_sunny_cloudy_rainy);
        for (int index = 0; index <= 24; index++) {
            images.add(icon);
        }
    }

    private void initHoursWeatherRecyclerView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.hoursWeatherRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        HoursWeatherRecyclerViewAdapter hoursWeatherRecyclerViewAdapter = new HoursWeatherRecyclerViewAdapter(hours, images, getContext());
        recyclerView.setAdapter(hoursWeatherRecyclerViewAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  (ViewGroup) inflater.inflate(R.layout.fragment_location, container, false);
    }
}
