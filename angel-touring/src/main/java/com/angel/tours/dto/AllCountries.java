package com.angel.tours.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllCountries {
    private List<GeoName> geonames = null;

    public List<GeoName> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<GeoName> geonames) {
        this.geonames = geonames;
    }
}
