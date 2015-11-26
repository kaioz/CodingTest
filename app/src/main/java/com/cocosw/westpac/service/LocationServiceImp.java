package com.cocosw.westpac.service;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
class LocationServiceImp implements LocationService {
    private static final long TIMEOUT = 30;
    private LocationManager locationManager;

    public LocationServiceImp(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
    }

    @Nullable
    @Override
    public Location getCurrentLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setCostAllowed(true);
        final String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null)
            return location;
        else {
            final CountDownLatch latch = new CountDownLatch(2);
            final Location[] result = new Location[1];
            Thread asyncTask = new Thread(){
                @Override
                public void run(){
                    Looper.prepare();
                    latch.countDown();
                    locationManager.requestSingleUpdate(provider, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            result[0] = location;
                            latch.countDown();
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }, null);
                }
            };
            asyncTask.start();
            try {
                latch.await(TIMEOUT,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                return null;
            }
            return result[0];
        }
    }
}
