package com.ikozm.javatesttask.service;

import com.ikozm.javatesttask.model.MonoExchangeRate;

import java.util.List;

public interface MonobankService {

    /**
     * Retrieves the latest exchange rates from MonoBank API.
     *
     * @return a list of MonoExchangeRate objects representing the exchange rates.
     */
    List<MonoExchangeRate> getExchangeRates();

}
