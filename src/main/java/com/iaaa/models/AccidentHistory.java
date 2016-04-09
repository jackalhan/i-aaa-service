package com.iaaa.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jackalhan on 4/8/16.
 */
@Entity
@Table(name = "accidentHistory")
public class AccidentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private int fatalNumber;

    @NotNull
    private int accidentNumber;

    @NotNull
    private double lon;

    @NotNull
    private double lat;

    @NotNull
    private Date accidentTime;

    @NotNull
    private int numberOfKilled;

    @NotNull
    private int numberOfInjured;

    @NotNull
    private String weatherCondition;

    @NotNull
    private String roadCondition;

    @NotNull
    private Date insertTime;

    @NotNull
    private String location;

    @NotNull
    private String city;

    @NotNull
    private String county;

    @NotNull
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFatalNumber() {
        return fatalNumber;
    }

    public void setFatalNumber(int fatalNumber) {
        this.fatalNumber = fatalNumber;
    }

    public int getAccidentNumber() {
        return accidentNumber;
    }

    public void setAccidentNumber(int accidentNumber) {
        this.accidentNumber = accidentNumber;
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

    public Date getAccidentTime() {
        return accidentTime;
    }

    public void setAccidentTime(Date accidentTime) {
        this.accidentTime = accidentTime;
    }

    public int getNumberOfKilled() {
        return numberOfKilled;
    }

    public void setNumberOfKilled(int numberOfKilled) {
        this.numberOfKilled = numberOfKilled;
    }

    public int getNumberOfInjured() {
        return numberOfInjured;
    }

    public void setNumberOfInjured(int numberOfInjured) {
        this.numberOfInjured = numberOfInjured;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getRoadCondition() {
        return roadCondition;
    }

    public void setRoadCondition(String roadCondition) {
        this.roadCondition = roadCondition;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AccidentHistory{" +
                "state='" + state + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", insertTime=" + insertTime +
                ", roadCondition='" + roadCondition + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", numberOfInjured=" + numberOfInjured +
                ", numberOfKilled=" + numberOfKilled +
                ", accidentTime=" + accidentTime +
                ", lat=" + lat +
                ", lon=" + lon +
                ", accidentNumber=" + accidentNumber +
                ", fatalNumber=" + fatalNumber +
                ", id=" + id +
                '}';
    }

    public AccidentHistory() {
    }

    public AccidentHistory(long id) {
        this.id = id;
    }

    public AccidentHistory(int fatalNumber, int accidentNumber, double lon, double lat, Date accidentTime, int numberOfKilled, int numberOfInjured, String weatherCondition, String roadCondition, Date insertTime, String location, String city, String county, String state) {
        this.fatalNumber = fatalNumber;
        this.accidentNumber = accidentNumber;
        this.lon = lon;
        this.lat = lat;
        this.accidentTime = accidentTime;
        this.numberOfKilled = numberOfKilled;
        this.numberOfInjured = numberOfInjured;
        this.weatherCondition = weatherCondition;
        this.roadCondition = roadCondition;
        this.insertTime = insertTime;
        this.location = location;
        this.city = city;
        this.county = county;
        this.state = state;
    }
}
