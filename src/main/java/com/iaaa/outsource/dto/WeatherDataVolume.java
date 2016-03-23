package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by txcakaloglu on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataVolume {
    private double volume;

    public WeatherDataVolume() {
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "WeatherDataVolume{" +
                "volume=" + volume +
                '}';
    }
}
