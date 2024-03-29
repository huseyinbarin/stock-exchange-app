package com.barin.stockexchangeapplication.domain.process.stock_exchange.repository;

import com.barin.stockexchangeapplication.domain.model.StockExchange;

import java.util.List;
import java.util.Optional;

public interface StockExchangeRepository {
    Optional<StockExchange> findByName(String name);

    Optional<List<StockExchange>> findAll();

    void deleteByName(String name);

    void save(StockExchange stockExchange);
}
