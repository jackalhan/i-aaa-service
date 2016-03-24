package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iaaa.dto.Coordinates;

/**
 * Created by jackalhan on 3/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleRoadsSnappePoints {
    private Coordinates coordinates;
    private int originalIndex;
    private String placeId;

    public GoogleRoadsSnappePoints() {
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(int originalIndex) {
        this.originalIndex = originalIndex;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return "GoogleRoadsSnappePoints{" +
                "coordinates=" + coordinates +
                ", originalIndex=" + originalIndex +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}
