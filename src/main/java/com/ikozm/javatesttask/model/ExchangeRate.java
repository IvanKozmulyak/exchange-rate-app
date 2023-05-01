package com.ikozm.javatesttask.model;

import com.ikozm.javatesttask.utils.CurrencyUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;

@Entity
@Getter
@Setter
@ToString
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private Currency baseCurrency;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private BigDecimal rateSell;

    @Column(nullable = false)
    private BigDecimal rateBuy;

    @Enumerated(EnumType.STRING)
    private ExchangeRateSource exchangeRateSource;

    public static ExchangeRate fromMonoExchangeRate(MonoExchangeRate monoExchangeRate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrency(CurrencyUtil.getCurrencyFromNumericCode(monoExchangeRate.getCurrencyCodeA()));
        exchangeRate.setBaseCurrency(CurrencyUtil.getCurrencyFromNumericCode(monoExchangeRate.getCurrencyCodeB()));
        exchangeRate.setRateBuy(monoExchangeRate.getRateBuy());
        exchangeRate.setRateSell(monoExchangeRate.getRateSell());
        if (!monoExchangeRate.getRateCross().equals(BigDecimal.ZERO)) {
            exchangeRate.setRateBuy(monoExchangeRate.getRateCross());
            exchangeRate.setRateSell(monoExchangeRate.getRateCross());
        }
        exchangeRate.setDate(monoExchangeRate.getDate());
        exchangeRate.setExchangeRateSource(ExchangeRateSource.MONOBANK);
        return exchangeRate;
    }

    public static ExchangeRate fromPrivatExchangeRate(PrivatExchangeRate privatExchangeRate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrency(Currency.getInstance(privatExchangeRate.getCcy()));
        exchangeRate.setBaseCurrency(Currency.getInstance(privatExchangeRate.getBase_ccy()));
        exchangeRate.setRateBuy(privatExchangeRate.getBuy());
        exchangeRate.setRateSell(privatExchangeRate.getSale());
        exchangeRate.setDate(privatExchangeRate.getLocalDateTime());
        exchangeRate.setExchangeRateSource(ExchangeRateSource.PRIVATBANK);
        return exchangeRate;
    }

    public static ExchangeRate fromMinfinExchangeRate(MinfinExchangeRate minfinExchangeRate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrency(Currency.getInstance(minfinExchangeRate.getCurrency().toUpperCase(Locale.ENGLISH)));
        exchangeRate.setBaseCurrency(Currency.getInstance("UAH"));
        exchangeRate.setRateBuy(minfinExchangeRate.getAsk());
        exchangeRate.setRateSell(minfinExchangeRate.getBid());
        exchangeRate.setDate(LocalDateTime.parse(minfinExchangeRate.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        exchangeRate.setExchangeRateSource(ExchangeRateSource.MINFIN);
        return exchangeRate;
    }

}
