package com.cocosw.westpac.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.cocosw.westpac.R;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public enum Weather {

    CLEAR_DAY("clear-day", R.drawable.clearday,R.color.skyBlueColor),
    CLEAR_NIGHT("clear-night", R.drawable.clearnight,R.color.midnightBlueColor),
    RAIN("rain", R.drawable.rain,R.color.mandarinColor),
    SNOW("snow", R.drawable.snow,R.color.oliveColor),
    SLEET("sleet", R.drawable.snow,R.color.Primary_Teal_700),
    WIND("wind", R.drawable.wind,R.color.Primary_Pink_700),
    FOG("fog", R.drawable.wind,R.color.Primary_Deep_Purple_700),
    CLOUDY("cloudy", R.drawable.cloudy,R.color.raspberryColor),
    PARTLY_CLOUDY_DAY("partly-cloudy-day", R.drawable.partlycloudyday,R.color.waveColor),
    PARTLY_CLOUDY_NIGHT("partly-cloudy-night", R.drawable.partlycloudynight,R.color.Primary_Brown_600);

    public final String value;
    @DrawableRes
    public final int icon;
    public final int bg;

    Weather(String value, @DrawableRes int icon,@ColorRes int bg) {
        this.value = value;
        this.icon = icon;
        this.bg = bg;
    }

    public static Weather getWeatherType(String weatherType) {
        for (Weather weather : Weather.values()) {
            if (weather.value.equalsIgnoreCase(weatherType))
                return weather;
        }
        return CLEAR_DAY;
    }
}

