package com.iaaa.dto;

/**
 * Created by jackalhan on 3/22/16.
 */
public class AnyAccidentResponse {
    private long id;
    private boolean anyAccident;
    private int numberOfAccidents;
    private int numberOfInjuredPeople;
    private int numberOfKilledPeople;
    private String text;

    public AnyAccidentResponse(long id, boolean anyAccident, int numberOfAccidents, int numberOfInjuredPeople, int numberOfKilledPeople, String text) {
        this.id = id;
        this.anyAccident = anyAccident;
        this.numberOfAccidents = numberOfAccidents;
        this.numberOfInjuredPeople = numberOfInjuredPeople;
        this.numberOfKilledPeople = numberOfKilledPeople;
        this.text = text;
    }


    public AnyAccidentResponse(boolean anyAccident, int numberOfAccidents, int numberOfInjuredPeople, int numberOfKilledPeople, String text) {

        this.anyAccident = anyAccident;
        this.numberOfAccidents = numberOfAccidents;
        this.numberOfInjuredPeople = numberOfInjuredPeople;
        this.numberOfKilledPeople = numberOfKilledPeople;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnyAccidentResponse(long id, boolean anyAccident) {

        this.id = id;
        this.anyAccident = anyAccident;
    }

    public AnyAccidentResponse(String text) {
        this.text = text;
    }

    public AnyAccidentResponse() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAnyAccident() {
        return anyAccident;
    }

    public void setAnyAccident(boolean anyAccident) {
        this.anyAccident = anyAccident;
    }

    public int getNumberOfAccidents() {
        return numberOfAccidents;
    }

    public void setNumberOfAccidents(int numberOfAccidents) {
        this.numberOfAccidents = numberOfAccidents;
    }

    public int getNumberOfInjuredPeople() {
        return numberOfInjuredPeople;
    }

    public void setNumberOfInjuredPeople(int numberOfInjuredPeople) {
        this.numberOfInjuredPeople = numberOfInjuredPeople;
    }

    public int getNumberOfKilledPeople() {
        return numberOfKilledPeople;
    }

    public void setNumberOfKilledPeople(int numberOfKilledPeople) {
        this.numberOfKilledPeople = numberOfKilledPeople;
    }
}
