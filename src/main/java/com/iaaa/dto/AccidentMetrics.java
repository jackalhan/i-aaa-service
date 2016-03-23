package com.iaaa.dto;

/**
 * Created by jackalhan on 3/22/16.
 */
public class AccidentMetrics implements Cloneable {

    private float latOfVehicle;
    private float longOfVehicle;
    private int speedOfVehicle;
    private String weatherCondition;
    private String roadCondition;
    private int speedLimitOfRoad;

    public float getLatOfVehicle() {
        return latOfVehicle;
    }

    public void setLatOfVehicle(float latOfVehicle) {
        this.latOfVehicle = latOfVehicle;
    }

    public float getLongOfVehicle() {
        return longOfVehicle;
    }

    public void setLongOfVehicle(float longOfVehicle) {
        this.longOfVehicle = longOfVehicle;
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
        this.roadCondition = weatherCondition;
    }

    public String getRoadCondition() {
        return roadCondition;
    }

    public int getSpeedLimitOfRoad() {
        return speedLimitOfRoad;
    }

    public void setSpeedLimitOfRoad(int speedLimitOfRoad) {
        this.speedLimitOfRoad = speedLimitOfRoad;
    }

    public AccidentMetrics(float latOfVehicle, float longOfVehicle, int speedOfVehicle) {
        this.latOfVehicle = latOfVehicle;
        this.longOfVehicle = longOfVehicle;
        this.speedOfVehicle = speedOfVehicle;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "AccidentMetrics{" +
                "latOfVehicle=" + latOfVehicle +
                ", longOfVehicle=" + longOfVehicle +
                ", speedOfVehicle=" + speedOfVehicle +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", roadCondition='" + roadCondition + '\'' +
                ", speedLimitOfRoad=" + speedLimitOfRoad +
                '}';
    }
}
