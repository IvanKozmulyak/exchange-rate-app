package com.ikozm.javatesttask.model;

public enum ExchangeRateSource {
    MONOBANK("https://api.monobank.ua/bank/currency"), PRIVATBANK("https://api.privatbank.ua/p24api/pubinfo?exchange"), MINFIN("https://api.minfin.com.ua/nbu/");

    public final String apiUrl;

    ExchangeRateSource(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}