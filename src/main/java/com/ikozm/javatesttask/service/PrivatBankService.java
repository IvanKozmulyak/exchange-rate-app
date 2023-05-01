package com.ikozm.javatesttask.service;

import com.ikozm.javatesttask.model.PrivatExchangeRate;

import java.util.List;

public interface PrivatBankService {

    /**
     * Retrieves the latest exchange rates from PrivatBank API.
     *
     * @return a list of PrivatExchangeRate objects representing the exchange rates.
     */
    List<PrivatExchangeRate> getExchangeRates();
}
