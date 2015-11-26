package com.cocosw.westpac.service;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cocosw.westpac.model.WeatherModel;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */

class WeatherServiceImp implements WeatherService {

    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds
    private static final String TAG = "WeatherServiceImp";


    WeatherServiceImp() {

    }

    @Override
    public WeatherModel getCurrentWeather(@NonNull Location location) throws Exception {
        final URL url = new URL("https://api.forecast.io/forecast/02d9ff590a598d7242a96b2ff45b91d0/" + location.getLatitude() + "," + location.getLongitude());
        InputStream stream = null;
        try {
            Log.i(TAG, "Streaming data from network: " + location);
            stream = downloadUrl(url);
            return streamToModel(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    WeatherModel streamToModel(InputStream stream) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        JSONObject jsonObject = new JSONObject(sb.toString()).getJSONObject("currently");
        WeatherModel model = new WeatherModel();
        model.icon = jsonObject.getString("icon");
        model.summary = jsonObject.optString("summary");
        model.temperature = jsonObject.getDouble("temperature");
        return model;
    }


    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    private InputStream downloadUrl(final URL url) throws IOException {
        disableConnectionReuseIfNecessary();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
        conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    /**
     * Workaround for bug pre-Froyo, see here for more info:
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

}
