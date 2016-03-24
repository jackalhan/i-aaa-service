package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jackalhan on 3/24/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HereRouteDataMetaInfo {
    private String mapVersion;
    private String moduleVersion;
    private String interfaceVersion;
    private String timestamp;

    public HereRouteDataMetaInfo() {
    }

    public HereRouteDataMetaInfo(String mapVersion, String moduleVersion, String interfaceVersion, String timestamp) {
        this.mapVersion = mapVersion;
        this.moduleVersion = moduleVersion;
        this.interfaceVersion = interfaceVersion;
        this.timestamp = timestamp;
    }

    public String getMapVersion() {
        return mapVersion;
    }

    public void setMapVersion(String mapVersion) {
        this.mapVersion = mapVersion;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "HereRouteDataMetaInfo{" +
                "mapVersion='" + mapVersion + '\'' +
                ", moduleVersion='" + moduleVersion + '\'' +
                ", interfaceVersion='" + interfaceVersion + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
