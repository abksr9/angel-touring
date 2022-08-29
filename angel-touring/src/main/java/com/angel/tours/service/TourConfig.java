package com.angel.tours.service;

import com.angel.tours.client.CountryInfoClient;
import com.angel.tours.client.CurrencyClient;
import com.angel.tours.dto.AllCountries;
import com.angel.tours.dto.GeoName;
import org.json.JSONException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class TourConfig {

    @Bean(name = "mapCodeBean")
    @Lazy
    public ConcurrentHashMap<String, GeoName> countryCodeAndInfoMap() throws IOException, JSONException {
        ConcurrentHashMap<String, GeoName> countryCodeAndInfoMap = new ConcurrentHashMap<>();
        AllCountries allCountryInfo = countryInfoClient().getAllCountryInfo();
        for (GeoName geoName : allCountryInfo.getGeonames()) {
            countryCodeAndInfoMap.put(geoName.getCountryCode().toLowerCase(), geoName);
        }
        return countryCodeAndInfoMap;
    }

    @Bean(name = "mapCountryBean")
    @Lazy
    public ConcurrentHashMap<String, GeoName> countryNameAndInfoMap() throws IOException, JSONException {
        ConcurrentHashMap<String, GeoName> countryNameAndInfoMap = new ConcurrentHashMap<>();
        AllCountries allCountryInfo = countryInfoClient().getAllCountryInfo();
        for (GeoName geoName : allCountryInfo.getGeonames()) {
            countryNameAndInfoMap.put(geoName.getCountryName().toLowerCase(), geoName);
        }
        return countryNameAndInfoMap;
    }

    @Bean
    @Lazy
    public CountryInfoClient countryInfoClient() {
       return new CountryInfoClient();
    }

    @Bean
    @Lazy
    public CurrencyClient currencyClient() {
        return new CurrencyClient();
    }
 }
