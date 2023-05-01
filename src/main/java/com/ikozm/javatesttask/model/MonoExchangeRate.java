package com.ikozm.javatesttask.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@ToString
public class MonoExchangeRate {

    private String currencyCodeA;

    private String currencyCodeB;

    private LocalDateTime date;

    private BigDecimal rateSell;

    private BigDecimal rateBuy;

    private BigDecimal rateCross;

    public void setDate(Long date) {
        this.date = Instant.ofEpochMilli(date * 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
