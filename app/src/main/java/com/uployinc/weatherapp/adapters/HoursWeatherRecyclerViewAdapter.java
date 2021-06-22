package com.uployinc.weatherapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uployinc.weatherapp.R;

import java.util.ArrayList;

public class HoursWeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> hours;
    private ArrayList<String> temperatures;
    private ArrayList<Bitmap> images;
    private Context context;

    public HoursWeatherRecyclerViewAdapter(ArrayList<String> hours, ArrayList<String> temperatures, ArrayList<Bitmap> images,  Context context) {
        this.hours = hours;
        this.temperatures = temperatures;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).hour.setText(this.hours.get(position));
        ((ViewHolder) holder).temperature.setText(this.temperatures.get(position));
        ((ViewHolder) holder).image.setImageBitmap(this.images.get(position));
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hour;
        public TextView temperature;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hour);
            temperature = itemView.findViewById(R.id.temperature);
            image = itemView.findViewById(R.id.image);
        }
    }

}
