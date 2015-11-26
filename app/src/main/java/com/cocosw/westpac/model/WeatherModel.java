package com.cocosw.westpac.model;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public class WeatherModel {
    public String summary;
    public double temperature;
    public String icon;

    @Override
    public String toString() {
        return "WeatherModel{" +
                "summary='" + summary + '\'' +
                ", temperature=" + temperature +
                ", icon='" + icon + '\'' +
                '}';
    }
}
