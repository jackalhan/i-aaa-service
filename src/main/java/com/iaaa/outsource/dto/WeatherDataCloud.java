package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by txcakaloglu on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataCloud {
    private long all;

    public WeatherDataCloud() {
    }

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "WeatherDataCloud{" +
                "all=" + all +
                '}';
    }
}

