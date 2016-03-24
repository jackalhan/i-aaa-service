package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by jackalhan on 3/24/16.
 */
@ResponseBody
@JsonIgnoreProperties(ignoreUnknown = true)
public class HereRouteDataApiResponse {

    private HereRouteDataResponse response;

    public HereRouteDataApiResponse() {
    }

    public HereRouteDataApiResponse(HereRouteDataResponse response) {
        this.response = response;
    }

    public HereRouteDataResponse getResponse() {
        return response;
    }

    public void setResponse(HereRouteDataResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "HereRouteDataApiResponse{" +
                "response=" + response +
                '}';
    }
}
