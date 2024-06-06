package com.example.demo.service;

import com.example.demo.model.CurrencyConversion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyService {
    private static final String API_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/myr.json";

    public CurrencyConversion getMyrCurrencyRates() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(connection.getInputStream(), CurrencyConversion.class);
    }
}
