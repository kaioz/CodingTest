package com.cocosw.westpac.service;

import android.location.Location;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public enum  MockLocation {

    NONMOCK("NONMOCK",null),
    NULL("No Location",null),
    SYDNEY("Sydney",location(23,24)),
    CHENGDU("Chengdu",location(15,16));

    public final String name;
    public final Location location;

    MockLocation(String name,Location location){
        this.name = name;
        this.location = location;
    }

    @Override public String toString() {
        return name;
    }

    private static Location location(double lat,double lng) {
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }
}
