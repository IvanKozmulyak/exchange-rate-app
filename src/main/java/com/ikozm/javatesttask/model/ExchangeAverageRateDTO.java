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
public class ExchangeAverageRateDTO {

    private Currency currency;

    private Currency baseCurrency;

    private BigDecimal averageRateSell;

    private BigDecimal averageRateBuy;

    private LocalDateTime date;

    public static ExchangeAverageRateDTO fromExchangeRate(ExchangeRate exchangeRate){
        ExchangeAverageRateDTO exchangeRateDTO = new ExchangeAverageRateDTO();
        exchangeRateDTO.setCurrency(exchangeRate.getCurrency());
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setDate(exchangeRate.getDate());
        return exchangeRateDTO;
    }
}
