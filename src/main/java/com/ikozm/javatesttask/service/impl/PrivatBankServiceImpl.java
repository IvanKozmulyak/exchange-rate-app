package com.ikozm.javatesttask.service.impl;

import com.ikozm.javatesttask.model.ExchangeRateSource;
import com.ikozm.javatesttask.model.PrivatExchangeRate;
import com.ikozm.javatesttask.service.PrivatBankService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link PrivatBankService} interface.
 */
@Service
public class PrivatBankServiceImpl implements PrivatBankService {

    private final        RestTemplate restTemplate;
    private static final Logger       log = LoggerFactory.getLogger(PrivatBankServiceImpl.class);

    public PrivatBankServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<PrivatExchangeRate> getExchangeRates(){
        log.info("Retrieving exchange rates from PrivatBank");
        ResponseEntity<List<PrivatExchangeRate>> exchange = null;
        try {
            exchange = restTemplate.exchange(ExchangeRateSource.PRIVATBANK.apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        } catch (Exception e) {
            log.warn("Error while processing request: {}", e.getMessage());
        }
        if (exchange == null) {
            return new ArrayList<>();
        }
        List<PrivatExchangeRate> exchangeRates = exchange.getBody();
        log.debug("Retrieved exchange rates from PrivatBank: {}", exchangeRates);
        return exchangeRates;
    }
}
