package com.rodvar.westpactest.model;

import java.util.Observable;

/**
 * Created by rodrigo on 15/06/15.
 */
public class Weather extends Observable {

    private static final String CLEAR = "Clear";
    private static final String LIGH_RAINY = "Light Rain";
    private static final String RAINY = "Rain";
    private static final String SUNNY = "Sunny";

    private String location = "";
    private String description = "";
    private String temperature = "";
    private String humidity = "";
    private String windSpeed = "";
    private String pressure = "";

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setLocation(String location) {
        this.location = location;
        this.setChanged();
    }

    public void setDescription(String description) {
        this.description = description;
        this.setChanged();
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
        this.setChanged();
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
        this.setChanged();
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
        this.setChanged();
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
        this.setChanged();
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public boolean isClear() {
        return CLEAR.equals(this.description);
    }

    public boolean isRainy() {
        return RAINY.equals(this.description) || LIGH_RAINY.equals(this.description);
    }

    public boolean isSunny() {
        return SUNNY.equals(this.description);
    }
}
