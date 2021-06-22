package com.uployinc.weatherapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uployinc.weatherapp.App;
import com.uployinc.weatherapp.Common.ICallback;
import com.uployinc.weatherapp.Models.DayForecast;
import com.uployinc.weatherapp.Models.HourForecast;
import com.uployinc.weatherapp.Models.HourlyForecast;
import com.uployinc.weatherapp.R;
import com.uployinc.weatherapp.adapters.HoursWeatherRecyclerViewAdapter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentLocation extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setDateViewText(view.findViewById(R.id.date));

        App.getLocationModule().GetCurrentLocation().addOnSuccessListener((location) -> {
            if (location != null) {
                initHourlyWeatherRecyclerView(view.findViewById(R.id.hourlyWeatherRecyclerView), location);

                setLocationViewText(view.findViewById(R.id.location), location);

                setCurrentWeatherView(view.findViewById(R.id.currentTemperatureText),
                        view.findViewById(R.id.currentHumidityText),
                        view.findViewById(R.id.currentPrecipitationChanceText),
                        view.findViewById(R.id.currentWindSpeedText),
                        view.findViewById(R.id.currentWeatherDescriptionText),
                        view.findViewById(R.id.weatherNowIcon),
                        location);
            }else{
                Log.e("error", "location was null");
            }
        });
    }

    private void setCurrentWeatherView(TextView temperatureView, TextView humidityView, TextView precipitationChanceView, TextView windSpeedView, TextView weatherDescriptionView, ImageView icon, Location location) {
        App.getWeatherApiComm().GetCurrentForecast(location, new ICallback<DayForecast>() {
            @Override
            public void onResponse(DayForecast response) {
                String temperatureStr = Math.round(response.getTemperature()) + " °C";
                String humidityStr = Math.round(response.getRelativeHumidity()) + "%";
                String windSpeedStr = Math.round(response.getWindSpeed() * 3.6) + " km/h";
                String precipitationChanceStr = Math.round(response.getProbabilityForPrecipitation()) + "%";
                String weatherDescriptionStr = response.getWeatherDescription();
                String weatherIconCode = response.getWeatherIconCode();
                int iconResourceId = App.getContext().getResources().getIdentifier("a"+weatherIconCode, "drawable", App.getContext().getPackageName());
                Bitmap weatherIcon = BitmapFactory.decodeResource(App.getContext().getResources(), iconResourceId);
                temperatureView.setText(temperatureStr);
                humidityView.setText(humidityStr);
                precipitationChanceView.setText(precipitationChanceStr);
                windSpeedView.setText(windSpeedStr);
                weatherDescriptionView.setText(weatherDescriptionStr);
                icon.setImageBitmap(weatherIcon);
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void setLocationViewText(TextView locationView, Location location) {
        App.getGeoCodingApiComm().GetCityByCoordinates(location, new ICallback<String>() {
            @Override
            public void onResponse(String response) {
                locationView.setText(response);
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void setDateViewText(TextView dateView) {
        LocalDate today = LocalDate.now();
        String formattedDate = today.getDayOfMonth() + " " + today.getMonth() + " " + today.getYear();
        dateView.setText(formattedDate);
    }

    private void initHourlyWeatherRecyclerView(RecyclerView recyclerView, Location location) {
        App.getWeatherApiComm().GetHourlyForecast(location, new ICallback<HourlyForecast>() {
            @Override
            public void onResponse(HourlyForecast response) {
                List<HourForecast> forecast = response.getList();
                ArrayList<String> hours = new ArrayList<>();
                ArrayList<String> temperatures = new ArrayList<>();
                ArrayList<Bitmap> images = new ArrayList<>();

                for (int i = 0; i <= 24; i++) {
                    HourForecast current = forecast.get(i);
                    String weatherIconCode = current.getWeatherIconCode();
                    int iconResourceId = App.getContext().getResources().getIdentifier("a"+weatherIconCode, "drawable", App.getContext().getPackageName());
                    Bitmap weatherIcon = BitmapFactory.decodeResource(App.getContext().getResources(), iconResourceId);
                    images.add(weatherIcon);

                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern( "HH:mm" )
                                    .withLocale( Locale.getDefault() )
                                    .withZone( ZoneId.systemDefault() );
                    hours.add(formatter.format(current.getTargetTime()));

                    temperatures.add(String.valueOf(Math.round(current.getTemperature()))+" °C");
                }
                Log.d("asdf", hours.get(0));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                HoursWeatherRecyclerViewAdapter hoursWeatherRecyclerViewAdapter = new HoursWeatherRecyclerViewAdapter(hours, temperatures, images, getContext());
                recyclerView.setAdapter(hoursWeatherRecyclerViewAdapter);
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  (ViewGroup) inflater.inflate(R.layout.fragment_location, container, false);
    }
}
