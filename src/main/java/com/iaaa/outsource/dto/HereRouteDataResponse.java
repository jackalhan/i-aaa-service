package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/**
 * Created by jackalhan on 3/24/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HereRouteDataResponse {

    private HereRouteDataMetaInfo metaInfo;

    private HereRouteDataLink link[];

    public HereRouteDataResponse() {
    }

    public HereRouteDataResponse(HereRouteDataMetaInfo metaInfo, HereRouteDataLink[] link) {
        this.metaInfo = metaInfo;
        this.link = link;
    }

    public HereRouteDataMetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(HereRouteDataMetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public HereRouteDataLink[] getLink() {
        return link;
    }

    public void setLink(HereRouteDataLink[] link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "HereRouteDataResponse{" +
                "metaInfo=" + metaInfo +
                ", link=" + Arrays.toString(link) +
                '}';
    }
}
