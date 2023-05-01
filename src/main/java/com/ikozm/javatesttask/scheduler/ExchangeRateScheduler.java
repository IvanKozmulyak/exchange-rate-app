package com.ikozm.javatesttask.scheduler;

import com.ikozm.javatesttask.model.MinfinExchangeRate;
import com.ikozm.javatesttask.repository.ExchangeRateRepository;
import com.ikozm.javatesttask.model.ExchangeRate;
import com.ikozm.javatesttask.model.MonoExchangeRate;
import com.ikozm.javatesttask.model.PrivatExchangeRate;
import com.ikozm.javatesttask.service.MinfinService;
import com.ikozm.javatesttask.service.MonobankService;
import com.ikozm.javatesttask.service.PrivatBankService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeRateScheduler {

    private static final Logger                 log = LoggerFactory.getLogger(ExchangeRateScheduler.class);
    private final        PrivatBankService      privatBankService;
    private final        MonobankService        monobankService;
    private final        MinfinService          minfinService;
    private final        ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateScheduler(PrivatBankService privatBankService,
                                 MonobankService monobankService,
                                 MinfinService minfinService,
                                 ExchangeRateRepository exchangeRateRepository) {
        this.privatBankService = privatBankService;
        this.monobankService = monobankService;
        this.minfinService = minfinService;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Method to retrieve exchange rates from banks and store them in the database.
     * This method is scheduled to run at a specific interval defined by the cron expression.
     * The retrieved exchange rates are stored in the ExchangeRate entity and then persisted in the database.
     */
    @Scheduled(cron = "${app.scheduler.exchange-rate.process.cron}")
    public void processExchangeRates() {
        log.info("Retrieving exchange rates from banks");

        // Retrieve exchange rates from different sources
        List<MonoExchangeRate> exchangeRatesFromMono = monobankService.getExchangeRates();
        List<PrivatExchangeRate> exchangeRatesFromPrivat = privatBankService.getExchangeRates();
        List<MinfinExchangeRate> exchangeRatesFromMinfin = minfinService.getExchangeRates();

        // Combine exchange rates from different sources
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.addAll(exchangeRatesFromMono.stream().map(ExchangeRate::fromMonoExchangeRate).toList());
        exchangeRates.addAll(exchangeRatesFromPrivat.stream().map(ExchangeRate::fromPrivatExchangeRate).toList());
        exchangeRates.addAll(exchangeRatesFromMinfin.stream().map(ExchangeRate::fromMinfinExchangeRate).toList());
        try {
            // Save exchange rates to the database
            for (ExchangeRate exchangeRate : exchangeRates) {
                exchangeRateRepository.upsert(exchangeRate, exchangeRate.getExchangeRateSource().toString());
            }
        } catch (Exception e) {
            log.error("Error saving exchange rate to the database: " + e.getMessage(), e);
        }
    }
}
