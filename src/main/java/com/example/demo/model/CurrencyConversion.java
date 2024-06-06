package com.example.demo.model;

import java.util.Map;

public class CurrencyConversion {
    private String date;
    private Map<String, Double> myr;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getMyr() {
        return myr;
    }

    public void setMyr(Map<String, Double> myr) {
        this.myr = myr;
    }

    public Map<String, Double> getRates() {
        return myr;
    }

}
