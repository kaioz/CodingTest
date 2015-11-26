package com.cocosw.westpac.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cocosw.westpac.model.WeatherModel;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public class ServiceProvider implements LocationService,WeatherService{

    protected final Context context;
    private LocationService locationService;

    public ServiceProvider(@NonNull Context context) {
        this.context = context.getApplicationContext();
        locationService = new LocationServiceImp(context);
    }

    @Nullable
    @Override
    public Location getCurrentLocation() {
        return locationService.getCurrentLocation();
    }

    @Override
    public @Nullable WeatherModel getCurrentWeather(@NonNull Location location) throws Exception {
        return new WeatherServiceImp().getCurrentWeather(location);
    }
}
