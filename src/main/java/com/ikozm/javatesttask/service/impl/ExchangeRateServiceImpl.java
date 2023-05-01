package com.ikozm.javatesttask.service.impl;

import com.ikozm.javatesttask.model.ExchangeAverageRateDTO;
import com.ikozm.javatesttask.model.ExchangeRate;
import com.ikozm.javatesttask.model.ExchangeRateDTO;
import com.ikozm.javatesttask.repository.ExchangeRateRepository;
import com.ikozm.javatesttask.service.ExchangeRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger                 log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
    private final        ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRateDTO> getAllExchangeRatesWithAverageValues() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllLatest();
        List<ExchangeRateDTO> exchangeRateDTOS = new ArrayList<>();
        exchangeRates.forEach(exchangeRate -> {
            ExchangeRateDTO exchangeRateDTO = ExchangeRateDTO.fromExchangeRate(exchangeRate);
            exchangeRateDTO.setAverageRateBuy(exchangeRateRepository.findLatestAverageRateBuyByCurrencyAndBaseCurrency(exchangeRate.getCurrency().getCurrencyCode(), exchangeRate.getBaseCurrency().getCurrencyCode()));
            exchangeRateDTO.setAverageRateSell(exchangeRateRepository.findLatestAverageRateSellByCurrencyAndBaseCurrency(exchangeRate.getCurrency().getCurrencyCode(), exchangeRate.getBaseCurrency().getCurrencyCode()));
            exchangeRateDTOS.add(exchangeRateDTO);
        });
        log.debug("Retrieved all exchange rates with average values.");
        return exchangeRateDTOS;
    }

    @Override
    public List<ExchangeAverageRateDTO> getAllAverageExchangeRatesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllLatest();
        List<ExchangeAverageRateDTO> exchangeRateDTOS = new ArrayList<>();
        exchangeRates.forEach(exchangeRate -> {
            ExchangeAverageRateDTO exchangeRateDTO = ExchangeAverageRateDTO.fromExchangeRate(exchangeRate);
            exchangeRateDTO.setAverageRateBuy(exchangeRateRepository.findLatestAverageRateBuyByCurrencyAndBaseCurrencyByDateRange(exchangeRate.getCurrency().getCurrencyCode(),
                                                                                                                                  exchangeRate.getBaseCurrency().getCurrencyCode(),
                                                                                                                                  startDate.atStartOfDay(),
                                                                                                                                  endDate.atTime(LocalTime.MAX)));
            exchangeRateDTO.setAverageRateSell(exchangeRateRepository.findLatestAverageRateSellByCurrencyAndBaseCurrencyByDateRange(exchangeRate.getCurrency().getCurrencyCode(),
                                                                                                                                    exchangeRate.getBaseCurrency().getCurrencyCode(),
                                                                                                                                    startDate.atStartOfDay(),
                                                                                                                                    endDate.atTime(LocalTime.MAX)));
            exchangeRateDTOS.add(exchangeRateDTO);
        });
        log.debug("Retrieved all average exchange rates by date range.");
        return exchangeRateDTOS;
    }
}
