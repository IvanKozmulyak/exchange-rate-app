package com.ikozm.javatesttask.controllers;

import com.ikozm.javatesttask.model.ExchangeAverageRateDTO;
import com.ikozm.javatesttask.model.ExchangeRate;
import com.ikozm.javatesttask.model.ExchangeRateDTO;
import com.ikozm.javatesttask.service.ExchangeRateService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Exchange Rate", description = "Exchange Rate APIs")
@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    /**
     * Constructs a new instance of the {@link ExchangeRateController}.
     *
     * @param exchangeRateService The {@link ExchangeRateService} to use.
     */
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Gets all exchange rates with average market rates.
     *
     * @return A list of {@link ExchangeRateDTO}s representing the exchange rates.
     */
    @Operation(summary = "Get a list of exchange rates for all sources, with average market rates")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/")
    public List<ExchangeRateDTO> getAllExchangeRatesWithAverageValues() {
        return exchangeRateService.getAllExchangeRatesWithAverageValues();
    }

    /**
     * Gets average exchange rates for a specific period.
     *
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return A list of {@link ExchangeAverageRateDTO}s representing the average exchange rates.
     */
    @Operation(summary = "Get a list of average exchange rates for all sources for the period")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/average")
    public List<ExchangeAverageRateDTO> getAverageExchangeRatesByPeriod(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return exchangeRateService.getAllAverageExchangeRatesByDateRange(startDate, endDate);
    }
}