package com.ikozm.javatesttask.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PrivatExchangeRate {

    private String ccy;

    private String base_ccy;

    private BigDecimal sale;

    private BigDecimal buy;

    private LocalDateTime localDateTime = LocalDateTime.now();
}
