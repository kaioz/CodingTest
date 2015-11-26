package com.cocosw.westpac.service;


/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public enum  MockWeather {

    NONMOCK("NONMOCK",null),
    EXCEPTION("Exception",null),
    ERROR("Error Json","error.json"),
    LA("LA","LA.json"),
    CHENGDU("Chengdu","Chengdu.json"),
    MELB("Melb","Melb.json");

    public final String name;
    public final String jsonfile;

    MockWeather(String name,String file){
        this.name = name;
        this.jsonfile = file;
    }

    @Override public String toString() {
        return name;
    }
}
