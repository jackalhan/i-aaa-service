package com.iaaa.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private String fatalNumber;

    @NotNull
    private int accidentNumber;

    @NotNull
    private double lon;

    @NotNull
    private double lat;

    @NotNull
    private Date accidentTime;


    private int numberOfKilled;


    private int numberOfInjured;


    private String weatherCondition;


    private String roadCondition;

    @NotNull
    private Date insertTime;


    private String location;


    private String formattedLocation;


    private String city;


    private String county;


    private String state;

    @Size(max = 4000)
    private String accidentScenario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFatalNumber() {
        return fatalNumber;
    }

    public void setFatalNumber(String fatalNumber) {
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

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public void setFormattedLocation(String formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    public String getAccidentScenario() {
        return accidentScenario;
    }

    public void setAccidentScenario(String accidentScenario) {
        this.accidentScenario = accidentScenario;
    }

    @Override
    public String toString() {
        return "AccidentHistory{" +
                "id=" + id +
                ", fatalNumber=" + fatalNumber +
                ", accidentNumber=" + accidentNumber +
                ", lon=" + lon +
                ", lat=" + lat +
                ", accidentTime=" + accidentTime +
                ", numberOfKilled=" + numberOfKilled +
                ", numberOfInjured=" + numberOfInjured +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", roadCondition='" + roadCondition + '\'' +
                ", insertTime=" + insertTime +
                ", location='" + location + '\'' +
                ", formattedLocation='" + formattedLocation + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", accidentScenario='" + accidentScenario + '\'' +
                '}';
    }

    public AccidentHistory() {
    }

    public AccidentHistory(long id) {
        this.id = id;
    }

    public AccidentHistory(String fatalNumber, int accidentNumber, String accidentScenario, double lon, double lat, String formattedLocation, Date accidentTime, int numberOfKilled, int numberOfInjured, String weatherCondition, String roadCondition, Date insertTime, String location, String city, String county, String state) {
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
        this.formattedLocation = formattedLocation;
        this.city = city;
        this.county = county;
        this.state = state;
        this.accidentScenario = accidentScenario;
    }
}
