package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jackalhan on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleRoadsApiResponse {

    private GoogleRoadsSnappePoints googleRoadsSnappePoints;
    private GoogleRoadsSpeedLimits googleRoadsSpeedLimits;

    public GoogleRoadsApiResponse() {
    }

    public GoogleRoadsApiResponse(GoogleRoadsSpeedLimits googleRoadsSpeedLimits) {
        this.googleRoadsSpeedLimits = googleRoadsSpeedLimits;
    }

    public GoogleRoadsSnappePoints getGoogleRoadsSnappePoints() {
        return googleRoadsSnappePoints;
    }

    public void setGoogleRoadsSnappePoints(GoogleRoadsSnappePoints googleRoadsSnappePoints) {
        this.googleRoadsSnappePoints = googleRoadsSnappePoints;
    }

    public GoogleRoadsSpeedLimits getGoogleRoadsSpeedLimits() {
        return googleRoadsSpeedLimits;
    }

    public void setGoogleRoadsSpeedLimits(GoogleRoadsSpeedLimits googleRoadsSpeedLimits) {
        this.googleRoadsSpeedLimits = googleRoadsSpeedLimits;
    }


    @Override
    public String toString() {
        return "GoogleRoadsApiResponse{" +
                "googleRoadsSpeedLimits=" + googleRoadsSpeedLimits +
                ", googleRoadsSnappePoints=" + googleRoadsSnappePoints +
                '}';
    }

}
