package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jackalhan on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleRoadsSpeedLimits {
    private String placeId;
    private int speedLimit;
    private String unit;

    public GoogleRoadsSpeedLimits() {

    }

    public GoogleRoadsSpeedLimits(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "GoogleRoadsSpeedLimits{" +
                "placeId='" + placeId + '\'' +
                ", speedLimit=" + speedLimit +
                ", unit='" + unit + '\'' +
                '}';
    }
}
