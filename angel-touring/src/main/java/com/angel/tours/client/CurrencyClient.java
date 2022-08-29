package com.angel.tours.client;

import com.angel.tours.dto.Budget;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class CurrencyClient {

    @Value("${exchangerate.apikey.value}")
    private String key;
    @Value("${exchangerate.apikey}")
    private String apiKey;

    ObjectMapper objectMapper = new ObjectMapper();
    public static final String HTTPS = "https://";
    public static final String ROOT = "api.apilayer.com/currency_data/convert?to=";
    public static final String FROM = "&from=";
    public static final String AMT = "&amount=";
    public double convertCurrency(String source, String destination, double amount) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(HTTPS + ROOT + destination + FROM + source + AMT + amount)
                .addHeader(apiKey, key)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        Budget budgetInDestinationCurrency = objectMapper.readValue(jsonObject.toString(), Budget.class);
        return budgetInDestinationCurrency.getResult();


    }
}
