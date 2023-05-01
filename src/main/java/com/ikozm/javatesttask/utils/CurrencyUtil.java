package com.ikozm.javatesttask.utils;

import java.util.Currency;

public class CurrencyUtil {

    /**
     * Returns a Currency object from the numeric code of a currency.
     *
     * @param numericCode the numeric code of the currency
     * @return the Currency object for the given numeric code
     * @throws IllegalArgumentException if the provided numeric code is not valid
     */
    public static Currency getCurrencyFromNumericCode(String numericCode){
        return Currency.getAvailableCurrencies().stream().filter(c -> c.getNumericCode() == Integer.parseInt(numericCode)).findAny().orElseThrow(() -> new IllegalArgumentException("Invalid currency code: " + numericCode));
    }
}
