package com.ikozm.javatesttask.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class MinfinExchangeRate {

    private String currency;

    private String date;

    private BigDecimal ask;

    private BigDecimal bid;
}
