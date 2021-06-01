package com.uployinc.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.uployinc.weatherapp.fragments.FragmentLocation;
import com.uployinc.weatherapp.fragments.FragmentLocationDetails;
import com.uployinc.weatherapp.fragments.FragmentLocations;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    final private int REQUEST_LOCATION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentLocations());
        fragmentList.add(new FragmentLocation());
        fragmentList.add(new FragmentLocationDetails());

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), fragmentList);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

        DotsIndicator dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_LOCATION) {

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Location permissions granted, starting location");
            } else {
                Log.d("TUKA", "SUM");
                this.finish();
                System.exit(0);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}