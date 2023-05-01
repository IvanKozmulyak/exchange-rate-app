package com.ikozm.javatesttask.service.impl;

import com.ikozm.javatesttask.model.ExchangeRateSource;
import com.ikozm.javatesttask.model.MonoExchangeRate;
import com.ikozm.javatesttask.service.MonobankService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonobankServiceImpl implements MonobankService {

    private final        RestTemplate restTemplate;
    private static final Logger       log = LoggerFactory.getLogger(MonobankServiceImpl.class);

    public MonobankServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MonoExchangeRate> getExchangeRates(){
        log.info("Retrieving exchange rates from MonoBank");
        ResponseEntity<List<MonoExchangeRate>> exchange = null;
        try {
            exchange = restTemplate.exchange(ExchangeRateSource.MONOBANK.apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        } catch (Exception e) {
            log.warn("Error while processing request: {}", e.getMessage());
        }
        if (exchange == null) {
            return new ArrayList<>();
        }
        List<MonoExchangeRate> exchangeRates = exchange.getBody();
        log.debug("Retrieved exchange rates from MonoBank: {}", exchangeRates);
        return exchangeRates;

    }
}
