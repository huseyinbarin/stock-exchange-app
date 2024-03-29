package com.barin.stockexchangeapplication.domain.process.stock.service;


import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.process.exception.StockNotFoundException;
import com.barin.stockexchangeapplication.domain.process.stock.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public void createStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void updateStockPrice(Long id, BigDecimal newPrice) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setCurrentPrice(newPrice);
            stockRepository.save(stock);
        } else {
            throw new StockNotFoundException("Stock with ID " + id + " not found");
        }
    }

    @Override
    @Transactional
    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }
}
