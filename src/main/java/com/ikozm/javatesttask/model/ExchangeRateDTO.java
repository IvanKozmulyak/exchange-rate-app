package com.ikozm.javatesttask.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@ToString
public class ExchangeRateDTO {

    private Currency currency;

    private Currency baseCurrency;

    private BigDecimal rateSell;

    private BigDecimal rateBuy;

    private BigDecimal averageRateSell;

    private BigDecimal averageRateBuy;

    private ExchangeRateSource exchangeRateSource;

    private LocalDateTime date;

    public static ExchangeRateDTO fromExchangeRate(ExchangeRate exchangeRate){
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setExchangeRateSource(exchangeRate.getExchangeRateSource());
        exchangeRateDTO.setCurrency(exchangeRate.getCurrency());
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setDate(exchangeRate.getDate());
        exchangeRateDTO.setRateBuy(exchangeRate.getRateBuy());
        exchangeRateDTO.setRateSell(exchangeRate.getRateSell());
        return exchangeRateDTO;
    }
}
