package com.iaaa.outsource;

import com.iaaa.dto.Coordinates;
import com.iaaa.outsource.dto.GoogleRoadsApiResponse;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jackalhan on 3/23/16.
 */
public class GoogleRoadsApi {

    //https://roads.googleapis.com/v1/speedLimits?parameters&key=AIzaSyDZao4FbodjtM4xsbCMAkESki8Mc4lYy3U
    private Coordinates coordinates;

    private String baseLink = "https://roads.googleapis.com/v1/speedLimits?path=#lat#,#lon#&key=AIzaSyDZao4FbodjtM4xsbCMAkESki8Mc4lYy3U&units=mph";

    public GoogleRoadsApi(Coordinates coordinates) {
        this.coordinates = coordinates;
        setBaseLink(baseLink.replace("#lat#", String.valueOf(coordinates.getLat())).replace("#lon#",String.valueOf(coordinates.getLon())));
    }

    public GoogleRoadsApiResponse query()
    {
        RestTemplate restTemplate = new RestTemplate();
        GoogleRoadsApiResponse googleRoadsApiResponse = restTemplate.getForObject(getBaseLink(), GoogleRoadsApiResponse.class);
        return googleRoadsApiResponse;
    }

    private Coordinates getCoordinates() {
        return coordinates;
    }

    private void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    private String getBaseLink() {
        return baseLink;
    }

    private void setBaseLink(String baseLink) {
        this.baseLink = baseLink;
    }
}
