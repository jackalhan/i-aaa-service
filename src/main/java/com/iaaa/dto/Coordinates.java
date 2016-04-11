package com.iaaa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by txcakaloglu on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates implements Cloneable{
    private double lon;
    private double lat;

    public Coordinates(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Coordinates() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';

    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
