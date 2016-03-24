package com.iaaa.outsource;

import com.iaaa.dto.Coordinates;
import com.iaaa.outsource.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by jackalhan on 3/24/16.
 */
public class HereRouteApi {

    //https://route.cit.api.here.com/routing/7.2/getlinkinfo.json?app_id=Ip4TNONaJ7MqupqRoIql&app_code=cwrdCEXOOiJoYT8Uj54j8Q&mode=car&waypoint=60.170879,24.942796&linkattributes=all
    private Coordinates coordinates;


    private String baseLink = "http://route.cit.api.here.com/routing/7.2/getlinkinfo.json?app_id=Ip4TNONaJ7MqupqRoIql&app_code=cwrdCEXOOiJoYT8Uj54j8Q&mode=car&waypoint=#lat#,#lon#&linkattributes=all";
    public HereRouteApi(Coordinates coordinates) {
        this.coordinates = coordinates;
        setBaseLink(baseLink.replace("#lat#", String.valueOf(coordinates.getLat())).replace("#lon#",String.valueOf(coordinates.getLon())));
    }

   public HereRouteDataApiResponse query()
    {
        System.out.println("Rest Api Query Link......................");
        System.out.println(getBaseLink());
        RestTemplate restTemplate = new RestTemplate();
        HereRouteDataApiResponse hereRouteDataApiResponse = restTemplate.getForObject(getBaseLink(), HereRouteDataApiResponse.class);
        System.out.println("Rest Api Response........................");
        System.out.println(hereRouteDataApiResponse.toString());
        return hereRouteDataApiResponse;
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

