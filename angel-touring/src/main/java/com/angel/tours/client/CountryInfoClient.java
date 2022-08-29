package com.angel.tours.client;

import com.angel.tours.dto.CountryInfoResponse;
import com.angel.tours.dto.AllCountries;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CountryInfoClient {

    public static final String HTTP = "http://";
    public static final String ROOT = "api.geonames.org/";
    public static final String NEIGHBOUR_PATH = "neighboursJSON?formatted=true";
    public static final String ALL_PATH = "countryInfoJSON?formatted=true";
    public static final String COUNTRY = "&countryCode=";
    public static final String USER = "&username=";
    @Value("${countryApi.username}")
    private String username;

    ObjectMapper objectMapper = new ObjectMapper();

    public CountryInfoResponse getCountryNeighbourInfo(String countryCode) throws IOException, JSONException {

        String urlPath = HTTP + ROOT + NEIGHBOUR_PATH + COUNTRY + countryCode + USER + username;

        StringBuffer response = getResponse(urlPath);
        JSONObject jsonResponse = new JSONObject(response.toString());
        return objectMapper.readValue(jsonResponse.toString(), CountryInfoResponse.class);
    }

    public AllCountries getAllCountryInfo() throws IOException, JSONException {

        String urlPath = HTTP + ROOT + ALL_PATH + USER + username;

        StringBuffer response = getResponse(urlPath);
        JSONObject jsonResponse = new JSONObject(response.toString());


        AllCountries allCountries = objectMapper.readValue(jsonResponse.toString(), AllCountries.class);
        return allCountries;
    }

    private StringBuffer getResponse(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response;
    }
}
