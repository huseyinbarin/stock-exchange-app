package com.barin.stockexchangeapplication.domain.process.stock.service;

import com.barin.stockexchangeapplication.domain.model.Stock;

import java.math.BigDecimal;

public interface StockService {
    Stock getStockById(Long id);

    void createStock(Stock stock);

    void updateStockPrice(Long id, BigDecimal newPrice);

    void deleteStockById(Long id);
}
