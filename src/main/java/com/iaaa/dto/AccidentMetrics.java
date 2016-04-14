package com.iaaa.dto;

import java.util.Random;

/**
 * Created by jackalhan on 3/22/16.
 */
public class AccidentMetrics implements Cloneable {

    private String[] roadConditionList = {"rain", "flood", "damp", "wet", "clear", "dry", "good", "fair", "grade", "pavement", "normal", "rual", "straight"
    , "snow","ic","mist","slush", "oily", "foggy", "west", "slick", "dark", "smoke", "curve", "gravel"};
    private Coordinates coordinates;
    private int speedOfVehicle;
    private String weatherCondition;
    private String roadCondition;
    private double speedLimitOfRoad;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getSpeedOfVehicle() {
        return speedOfVehicle;
    }

    public void setSpeedOfVehicle(int speedOfVehicle) {
        this.speedOfVehicle = speedOfVehicle;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
        this.roadCondition = roadConditionList[new Random().nextInt(roadConditionList.length)];
    }

    public String getRoadCondition() {
        return roadCondition;
    }

    public double getSpeedLimitOfRoad() {
        return speedLimitOfRoad;
    }

    public void setSpeedLimitOfRoad(double speedLimitOfRoad) {
        this.speedLimitOfRoad = speedLimitOfRoad;
    }

    public AccidentMetrics(Coordinates coordinates, int speedOfVehicle) {
        this.coordinates = coordinates;
        this.speedOfVehicle = speedOfVehicle;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "AccidentMetrics{" +
                "coordinates=" + coordinates +
                ", speedOfVehicle=" + speedOfVehicle +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", roadCondition='" + roadCondition + '\'' +
                ", speedLimitOfRoad=" + speedLimitOfRoad +
                '}';
    }
}
