package com.cocosw.westpac.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public interface LocationService {

    /**
     * Get current location, don't use this in UI thread
     */
    @WorkerThread
    @Nullable  Location getCurrentLocation();
}
