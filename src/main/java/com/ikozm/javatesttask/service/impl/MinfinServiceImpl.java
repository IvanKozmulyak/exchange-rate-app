package com.ikozm.javatesttask.service.impl;

import com.ikozm.javatesttask.model.ExchangeRateSource;
import com.ikozm.javatesttask.model.MinfinExchangeRate;
import com.ikozm.javatesttask.service.MinfinService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MinfinServiceImpl implements MinfinService {

    private final        RestTemplate restTemplate;
    private static final Logger       log = LoggerFactory.getLogger(MinfinServiceImpl.class);

    @Value("${app.minfin.api-key}")
    private String apiKey;

    public MinfinServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MinfinExchangeRate> getExchangeRates(){
        log.info("Retrieving exchange rates from Minfin");
        ResponseEntity<Map<String, MinfinExchangeRate>> exchange = null;
        try {
            exchange = restTemplate.exchange(ExchangeRateSource.MINFIN.apiUrl + apiKey, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
        } catch (Exception e) {
            log.warn("Error while processing request: {}", e.getMessage());
        }
        if (exchange == null) {
            return new ArrayList<>();
        }
        List<MinfinExchangeRate> exchangeRates = new ArrayList<>(Objects.requireNonNull(exchange.getBody()).values());
        log.debug("Retrieved exchange rates from Minfin: {}", exchangeRates);
        return exchangeRates;
    }
}
