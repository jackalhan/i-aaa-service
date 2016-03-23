package com.iaaa.outsource;

import com.iaaa.dto.Coordinates;
import com.iaaa.outsource.dto.WeatherDataApiResponse;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jackalhan on 3/22/16.
 */
public class WeatherDataApi {

    private Coordinates coordinates;
    private float radius = 0.1f; //Read from properties.

    private String baseLink = "http://api.openweathermap.org/data/2.5/weather?lat=#lat#&lon=#lot#&appid=65eed688d690347bc63f25c8cfbb0bee&units=imperial";

    public WeatherDataApi(Coordinates coordinates) {
        this.coordinates = coordinates;
        setBaseLink(baseLink.replace("#lat#", String.valueOf(coordinates.getLat())).replace("#lot#",String.valueOf(coordinates.getLon())));
    }

    public WeatherDataApiResponse query()
    {
        RestTemplate restTemplate = new RestTemplate();
        WeatherDataApiResponse wdr = restTemplate.getForObject(getBaseLink(), WeatherDataApiResponse.class);
        return wdr;
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
