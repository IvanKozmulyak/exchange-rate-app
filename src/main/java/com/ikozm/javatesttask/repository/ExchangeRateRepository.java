package com.ikozm.javatesttask.repository;

import com.ikozm.javatesttask.model.ExchangeRate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing {@link ExchangeRate} entities.
 */
@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

    /**
     * Upserts an {@link ExchangeRate} entity into the database.
     *
     * @param exchangeRate The {@link ExchangeRate} entity to upsert.
     * @param exchangeRateSource The source of the exchange rate.
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO exchange_rate (exchange_rate_source, currency, base_currency, rate_buy, rate_sell, date) "
            + "VALUES (:exchangeRateSource, :#{#exchangeRate.currency}, :#{#exchangeRate.baseCurrency}, "
            + ":#{#exchangeRate.rateBuy}, :#{#exchangeRate.rateSell}, :#{#exchangeRate.date}) "
            + "ON CONFLICT (exchange_rate_source, currency, base_currency, date) "
            + "DO UPDATE SET rate_buy = EXCLUDED.rate_buy, rate_sell = EXCLUDED.rate_sell", nativeQuery = true)
    void upsert(@Param("exchangeRate") ExchangeRate exchangeRate, @Param("exchangeRateSource") String exchangeRateSource);

    /**
     * Finds the latest exchange rates for each currency and base currency combination.
     *
     * @return A list of the latest {@link ExchangeRate} entities for each currency and base currency combination.
     */
    @Query(value = "SELECT er.id, er.currency, er.base_currency, er.rate_sell, er.rate_buy, er.exchange_rate_source, er.date "
            + "FROM exchange_rate er "
            + "INNER JOIN ( "
            + "SELECT currency, base_currency, MAX(date) AS max_timestamp "
            + "FROM exchange_rate "
            + "GROUP BY currency, base_currency "
            + ") sub ON er.currency = sub.currency AND er.base_currency = sub.base_currency AND er.date = sub.max_timestamp;" , nativeQuery = true)
    List<ExchangeRate> findAllLatest();

    /**
     * Finds the latest average rate sell for the given currency and base currency combination.
     *
     * @param currency The currency to find the average rate sell for.
     * @param baseCurrency The base currency to find the average rate sell for.
     * @return The latest average rate sell for the given currency and base currency combination.
     */
    @Query(value = "SELECT AVG(rate_sell) " +
            "FROM exchange_rate " +
            "WHERE currency = :currency AND base_currency = :baseCurrency " +
            "GROUP BY currency, base_currency", nativeQuery = true)
    BigDecimal findLatestAverageRateSellByCurrencyAndBaseCurrency(@Param("currency") String currency, @Param("baseCurrency") String baseCurrency);

    /**
     * Finds the latest average rate buy for the given currency and base currency combination.
     *
     * @param currency The currency to find the average rate buy for.
     * @param baseCurrency The base currency to find the average rate buy for.
     * @return The latest average rate buy for the given currency and base currency combination.
     */
    @Query(value = "SELECT AVG(rate_buy) " +
            "FROM exchange_rate e " +
            "WHERE currency = :currency AND base_currency = :baseCurrency " +
            "GROUP BY currency, base_currency", nativeQuery = true)
    BigDecimal findLatestAverageRateBuyByCurrencyAndBaseCurrency(@Param("currency") String currency, @Param("baseCurrency") String baseCurrency);

    /**
     * Returns the latest average rate sell for a specific currency and base currency within a given date range.
     *
     * @param currency the currency code
     * @param baseCurrency the base currency code
     * @param startDate the start date (inclusive) of the date range
     * @param endDate the end date (inclusive) of the date range
     * @return the latest average rate sell within the given date range
     */
    @Query(value = "SELECT AVG(rate_sell) " +
            "FROM exchange_rate " +
            "WHERE currency = :currency AND base_currency = :baseCurrency " +
            "AND date BETWEEN :startDate AND :endDate " +
            "GROUP BY currency, base_currency", nativeQuery = true)
    BigDecimal findLatestAverageRateSellByCurrencyAndBaseCurrencyByDateRange(@Param("currency") String currency, @Param("baseCurrency") String baseCurrency, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Returns the latest average rate buy for a specific currency and base currency within a given date range.
     *
     * @param currency the currency code
     * @param baseCurrency the base currency code
     * @param startDate the start date (inclusive) of the date range
     * @param endDate the end date (inclusive) of the date range
     * @return the latest average rate buy within the given date range
     */
    @Query(value = "SELECT AVG(rate_buy) " +
            "FROM exchange_rate e " +
            "WHERE currency = :currency AND base_currency = :baseCurrency " +
            "AND date BETWEEN :startDate AND :endDate " +
            "GROUP BY currency, base_currency", nativeQuery = true)
    BigDecimal findLatestAverageRateBuyByCurrencyAndBaseCurrencyByDateRange(@Param("currency") String currency, @Param("baseCurrency") String baseCurrency, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
