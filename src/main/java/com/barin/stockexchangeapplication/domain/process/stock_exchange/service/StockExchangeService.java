package com.barin.stockexchangeapplication.domain.process.stock_exchange.service;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.model.StockExchange;

public interface StockExchangeService {
    StockExchange getStockExchangeByName(String name);

    void createStockExchange(StockExchange stockExchange);

    void addStockToExchange(String exchangeName, Stock stock);

    void deleteStockFromExchange(String exchangeName, Long stockId);
}
