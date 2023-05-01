package com.ikozm.javatesttask.service;

import com.ikozm.javatesttask.model.MinfinExchangeRate;

import java.util.List;

public interface MinfinService {

    /**
     * Retrieves the latest exchange rates from Minfin API.
     *
     * @return a list of MinfinExchangeRate objects representing the exchange rates.
     */
    List<MinfinExchangeRate> getExchangeRates();
}
