package com.angel.tours.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfoResponse {

    private int totalResultsCount;
    private List<GeoName> geonames = null;

    public int getTotalResultsCount() {
        return totalResultsCount;
    }

    public void setTotalResultsCount(int totalResultsCount) {
        this.totalResultsCount = totalResultsCount;
    }

    public List<GeoName> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<GeoName> geonames) {
        this.geonames = geonames;
    }
}
