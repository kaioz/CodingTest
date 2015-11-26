package com.cocosw.westpac.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cocosw.westpac.model.WeatherModel;
import com.cocosw.westpac.ui.MockDelay;

import java.util.LinkedHashMap;
import java.util.Map;

import static android.os.SystemClock.sleep;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public class MockServiceProvider extends ServiceProvider {

    private final SharedPreferences preferences;

    private final Map<Class<? extends Enum<?>>, Enum<?>> responses = new LinkedHashMap<>();

    public MockServiceProvider(Context context) {
        super(context);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Initialize mock responses.
        loadResponse(MockLocation.class, MockLocation.NONMOCK);
        loadResponse(MockWeather.class,MockWeather.NONMOCK);
        loadResponse(MockDelay.class,MockDelay.NONE);
    }

    /**
     * Initializes the current response for {@code responseClass} from {@code SharedPreferences}, or
     * uses {@code defaultValue} if a response was not found.
     */
    private <T extends Enum<T>> void loadResponse(Class<T> responseClass, T defaultValue) {
        responses.put(responseClass, EnumPreferences.getEnumValue(preferences, responseClass, //
                responseClass.getCanonicalName(), defaultValue));
    }


    public <T extends Enum<T>> T getResponse(Class<T> responseClass) {
        return responseClass.cast(responses.get(responseClass));
    }


    public <T extends Enum<T>> void setResponse(Class<T> responseClass, T value) {
        responses.put(responseClass, value);
        EnumPreferences.saveEnumValue(preferences, responseClass.getCanonicalName(), value);
    }

    @Nullable
    @Override
    public Location getCurrentLocation() {
        final MockLocation mock = getResponse(MockLocation.class);
        switch (mock) {
            case NONMOCK:
                return super.getCurrentLocation();
            default: {
                MockDelay delay = getResponse(MockDelay.class);
                sleep(delay.ms);
                return mock.location;
            }
        }
    }

    @Nullable
    @Override
    public WeatherModel getCurrentWeather(@NonNull Location location) throws Exception {
        MockWeather mock = getResponse(MockWeather.class);
        switch (mock) {
            case NONMOCK:
                return super.getCurrentWeather(location);
            case EXCEPTION:
                throw new IllegalStateException("Mock Exception");
            default: {
                MockDelay delay = getResponse(MockDelay.class);
                sleep(delay.ms);
                return new WeatherServiceImp().streamToModel(context.getResources().getAssets().open(mock.jsonfile));
            }
        }
    }
}
