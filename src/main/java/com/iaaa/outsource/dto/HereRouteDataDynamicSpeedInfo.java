package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jackalhan on 3/24/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HereRouteDataDynamicSpeedInfo {
    private double jamFactor;

    private double baseSpeed;

    private int trafficTime;

    private int baseTime;

    private double trafficSpeed;

    public HereRouteDataDynamicSpeedInfo() {
    }

    public HereRouteDataDynamicSpeedInfo(double jamFactor, double baseSpeed, int trafficTime, int baseTime, double trafficSpeed) {
        this.jamFactor = jamFactor;
        this.baseSpeed = baseSpeed;
        this.trafficTime = trafficTime;
        this.baseTime = baseTime;
        this.trafficSpeed = trafficSpeed;
    }

    public double getJamFactor() {
        return jamFactor;
    }

    public void setJamFactor(double jamFactor) {
        this.jamFactor = jamFactor;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getTrafficTime() {
        return trafficTime;
    }

    public void setTrafficTime(int trafficTime) {
        this.trafficTime = trafficTime;
    }

    public int getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(int baseTime) {
        this.baseTime = baseTime;
    }

    public double getTrafficSpeed() {
        return trafficSpeed;
    }

    public void setTrafficSpeed(double trafficSpeed) {
        this.trafficSpeed = trafficSpeed;
    }

    @Override
    public String toString() {
        return "HereRouteDataDynamicSpeedInfo{" +
                "jamFactor=" + jamFactor +
                ", baseSpeed=" + baseSpeed +
                ", trafficTime=" + trafficTime +
                ", baseTime=" + baseTime +
                ", trafficSpeed=" + trafficSpeed +
                '}';
    }
}
