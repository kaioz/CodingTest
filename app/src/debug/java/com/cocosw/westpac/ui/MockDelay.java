package com.cocosw.westpac.ui;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public enum MockDelay {

    NONE("No Dealy",0),
    TWO("2000ms",2000),
    FIVE("5000ms",5000),
    TEN("10000ms",10000);

    private final String name;
    public final int ms;

    MockDelay(String name, int ms) {
        this.name = name;
        this.ms = ms;
    }

    @Override
    public String toString() {
        return name;
    }
}
