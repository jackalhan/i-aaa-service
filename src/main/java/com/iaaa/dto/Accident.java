package com.iaaa.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by jackalhan on 3/21/16.
 */

public class Accident {

    private String fatalNumber;
    private String accidentNumber;
    private String accidentDate;
    private String accidentTime;
    private String numberOfKilled;
    private String numberOfInjured;
    private String location;
    private String formattedLocation;
    private String city;
    private String county;
    private String state;
    private String locationLat;
    private String locationLong;
    private List<Person> killedPeople;
    private List<Person> injuredPeople;
    private List<Vehicle> vehicleList;
    private String accidentScenario;
    private String weatherCondition;
    private String roadCondition;
    private String bodyHeldAt;
    private String investigatingOfficer;
    private String agency;

    public Accident() {
    }

    public String getFatalNumber() {
        return fatalNumber;
    }

    public void setFatalNumber(String fatalNumber) {
        this.fatalNumber = fatalNumber;
    }

    public String getAccidentNumber() {
        return accidentNumber;
    }

    public void setAccidentNumber(String accidentNumber) {
        this.accidentNumber = accidentNumber;
    }

    public String getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(String accidentDate) {
        this.accidentDate = accidentDate;
    }

    public String getAccidentTime() {
        return accidentTime;
    }

    public void setAccidentTime(String accidentTime) {
        this.accidentTime = accidentTime;
    }

    public String getNumberOfKilled() {
        return numberOfKilled;
    }

    public void setNumberOfKilled(String numberOfKilled) {
        this.numberOfKilled = numberOfKilled;
    }

    public String getNumberOfInjured() {
        return numberOfInjured;
    }

    public void setNumberOfInjured(String numberOfInjured) {
        this.numberOfInjured = numberOfInjured;
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

    public List<Person> getKilledPeople() {
        return killedPeople;
    }

    public void setKilledPeople(List<Person> killedPeople) {
        this.killedPeople = killedPeople;
    }

    public List<Person> getInjuredPeople() {
        return injuredPeople;
    }

    public void setInjuredPeople(List<Person> injuredPeople) {
        this.injuredPeople = injuredPeople;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public String getAccidentScenario() {
        return accidentScenario;
    }

    public void setAccidentScenario(String accidentScenario) {
        this.accidentScenario = accidentScenario;
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

    public String getBodyHeldAt() {
        return bodyHeldAt;
    }

    public void setBodyHeldAt(String bodyHeldAt) {
        this.bodyHeldAt = bodyHeldAt;
    }

    public String getInvestigatingOfficer() {
        return investigatingOfficer;
    }

    public void setInvestigatingOfficer(String investigatingOfficer) {
        this.investigatingOfficer = investigatingOfficer;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }


    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public void setFormattedLocation(String formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    public Accident(String fatalNumber, String accidentNumber, String accidentDate, String accidentTime, String numberOfKilled, String numberOfInjured, String location, String formattedLocation, String city, String county, String state, String locationLat, String locationLong, List<Person> killedPeople, List<Person> injuredPeople, List<Vehicle> vehicleList, String accidentScenario, String weatherCondition, String roadCondition, String bodyHeldAt, String investigatingOfficer, String agency) {
        this.fatalNumber = fatalNumber;
        this.accidentNumber = accidentNumber;
        this.accidentDate = accidentDate;
        this.accidentTime = accidentTime;
        this.numberOfKilled = numberOfKilled;
        this.numberOfInjured = numberOfInjured;
        this.location = location;
        this.formattedLocation = formattedLocation;
        this.city = city;
        this.county = county;
        this.state = state;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.killedPeople = killedPeople;
        this.injuredPeople = injuredPeople;
        this.vehicleList = vehicleList;
        this.accidentScenario = accidentScenario;
        this.weatherCondition = weatherCondition;
        this.roadCondition = roadCondition;
        this.bodyHeldAt = bodyHeldAt;
        this.investigatingOfficer = investigatingOfficer;
        this.agency = agency;
    }

    @Override
    public String toString() {
        return "Accident{" +
                "fatalNumber='" + fatalNumber + '\'' +
                ", accidentNumber='" + accidentNumber + '\'' +
                ", accidentDate='" + accidentDate + '\'' +
                ", accidentTime='" + accidentTime + '\'' +
                ", numberOfKilled='" + numberOfKilled + '\'' +
                ", numberOfInjured='" + numberOfInjured + '\'' +
                ", location='" + location + '\'' +
                ", formattedLocation='" + formattedLocation + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", locationLat='" + locationLat + '\'' +
                ", locationLong='" + locationLong + '\'' +
                ", killedPeople=" + killedPeople +
                ", injuredPeople=" + injuredPeople +
                ", vehicleList=" + vehicleList +
                ", accidentScenario='" + accidentScenario + '\'' +
                ", weatherCondition='" + weatherCondition + '\'' +
                ", roadCondition='" + roadCondition + '\'' +
                ", bodyHeldAt='" + bodyHeldAt + '\'' +
                ", investigatingOfficer='" + investigatingOfficer + '\'' +
                ", agency='" + agency + '\'' +
                '}';
    }
}
