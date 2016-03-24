package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/**
 * Created by jackalhan on 3/24/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HereRouteDataLink {

    private String timezone;
    private String linkId;
    private int functionalClass;
    private int length;
    private HereRouteDataDynamicSpeedInfo dynamicSpeedInfo;
    private String roadNumber;
    private String shape[];
    private double speedLimit;
    private String roadName;


    public HereRouteDataLink() {
    }

    public HereRouteDataLink(String timezone, String linkId, int functionalClass, int length, HereRouteDataDynamicSpeedInfo dynamicSpeedInfo, String roadNumber, String[] shape, double speedLimit, String roadName) {
        this.timezone = timezone;
        this.linkId = linkId;
        this.functionalClass = functionalClass;
        this.length = length;
        this.dynamicSpeedInfo = dynamicSpeedInfo;
        this.roadNumber = roadNumber;
        this.shape = shape;
        this.speedLimit = speedLimit;
        this.roadName = roadName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public HereRouteDataDynamicSpeedInfo getDynamicSpeedInfo() {
        return dynamicSpeedInfo;
    }

    public void setDynamicSpeedInfo(HereRouteDataDynamicSpeedInfo dynamicSpeedInfo) {
        this.dynamicSpeedInfo = dynamicSpeedInfo;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String[] getShape() {
        return shape;
    }

    public void setShape(String[] shape) {
        this.shape = shape;
    }

    public int getFunctionalClass() {
        return functionalClass;
    }

    public void setFunctionalClass(int functionalClass) {
        this.functionalClass = functionalClass;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(double speedLimit) {
        this.speedLimit = Math.ceil(speedLimit * 2.23);
    }

    @Override
    public String toString() {
        return "HereRouteDataLink{" +
                "timezone='" + timezone + '\'' +
                ", linkId='" + linkId + '\'' +
                ", functionalClass=" + functionalClass +
                ", length=" + length +
                ", dynamicSpeedInfo=" + dynamicSpeedInfo +
                ", roadNumber='" + roadNumber + '\'' +
                ", shape=" + Arrays.toString(shape) +
                ", speedLimit=" + speedLimit +
                ", roadName='" + roadName + '\'' +
                '}';
    }
}
