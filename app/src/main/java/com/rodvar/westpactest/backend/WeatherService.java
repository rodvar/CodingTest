package com.rodvar.westpactest.backend;

import android.content.Context;
import android.location.Location;
import android.os.Process;

import com.rodvar.westpactest.model.Weather;

/**
 * Created by rodrigo on 15/06/15.
 */
public class WeatherService {
    private final static String WEATHER_URL = "https://api.forecast.io/forecast/654dd002af4eb46a84c29a589639d5c8/##LAT##,##LON##";
    private static final long PERIOD = 1000l;

    private static final WeatherService instance = new WeatherService();
    private Weather weather;

    public static WeatherService getInstance() {
        return instance;
    }

    public void init(Weather weather) {
        this.weather = weather;
    }

    private WeatherService() {
    }

    public Weather getWeather() {
        this.checkInit();
        return this.weather;
    }

    public void updateWeather(final Context context, final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                new WeatherRequest(context, weather).execute(WEATHER_URL.replaceAll("##LAT##", String.valueOf(location.getLatitude())).replaceAll("##LON##", String.valueOf(location.getLongitude())));
            }
        }).start();
    }


    private void checkInit() {
        if (this.weather == null)
            throw new IllegalStateException("Service was not initiated");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("cannot clone singleton");
    }
}
