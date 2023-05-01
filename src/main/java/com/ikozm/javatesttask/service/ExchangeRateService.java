package com.ikozm.javatesttask.service;

import com.ikozm.javatesttask.model.ExchangeAverageRateDTO;
import com.ikozm.javatesttask.model.ExchangeRateDTO;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateService {

    /**
     * Retrieves all ExchangeRate entities along with their corresponding average buy and sell rates.
     *
     * @return a list of ExchangeRateDTO objects containing ExchangeRate entities along with their corresponding average buy and sell rates
     */
    List<ExchangeRateDTO> getAllExchangeRatesWithAverageValues();

    /**
     * Retrieves average buy and sell rates of ExchangeRate entities within the specified date range.
     *
     * @param startDate the start date of the date range
     * @param endDate   the end date of the date range
     * @return a list of ExchangeAverageRateDTO objects containing average buy and sell rates of ExchangeRate entities within the specified date range
     */
    List<ExchangeAverageRateDTO> getAllAverageExchangeRatesByDateRange(LocalDate startDate, LocalDate endDate);
}
