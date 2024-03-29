package com.barin.stockexchangeapplication.domain.process.stock.repository;

import com.barin.stockexchangeapplication.domain.model.Stock;

import java.util.Optional;

public interface StockRepository {

    Optional<Stock> findById(Long id);

    void save(Stock stock);

    void deleteById(Long id);

}