package com.cocosw.westpac.service;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.cocosw.westpac.model.WeatherModel;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 25/11/2015.
 */
public interface WeatherService {

    /**
     * Get weather data from forecast.io with given location
     * @throws Exception
     */
    @WorkerThread
    WeatherModel getCurrentWeather(@NonNull Location location) throws Exception;
}
