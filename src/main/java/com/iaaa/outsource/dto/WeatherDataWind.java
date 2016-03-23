package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by txcakaloglu on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataWind {
    private double speed;
    private long deg;

    public WeatherDataWind() {
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getDeg() {
        return deg;
    }

    public void setDeg(long deg) {
        this.deg = deg;
    }

    @Override
    public String toString() {
        return "WeatherDataWind{" +
                "speed=" + speed +
                ", deg=" + deg +
                '}';
    }
}
