package com.barin.stockexchangeapplication.domain.process.stock_exchange.service;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.model.StockExchange;
import com.barin.stockexchangeapplication.domain.process.exception.StockExchangeNotFoundException;
import com.barin.stockexchangeapplication.domain.process.exception.StockNotFoundException;
import com.barin.stockexchangeapplication.domain.process.stock.repository.StockRepository;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.repository.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StockExchangeServiceImpl implements StockExchangeService {

    private final StockExchangeRepository stockExchangeRepository;

    @Autowired
    public StockExchangeServiceImpl(StockExchangeRepository stockExchangeRepository, StockRepository stockRepository) {
        this.stockExchangeRepository = stockExchangeRepository;
    }

    @Override
    @Transactional
    public void addStockToExchange(String exchangeName, Stock stock) {
        Optional<StockExchange> stockExchangeOptional = stockExchangeRepository.findByName(exchangeName);
        if (stockExchangeOptional.isPresent()) {
            StockExchange stockExchange = stockExchangeOptional.get();
            stockExchange.getStocks().add(stock);
            updateStockExchangeLiveInMarket(stockExchange);
        } else {
            throw new StockExchangeNotFoundException("Stock exchange with name " + exchangeName + " not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StockExchange getStockExchangeByName(String exchangeName) {
        return stockExchangeRepository.findByName(exchangeName)
                .orElseThrow(() -> new StockExchangeNotFoundException("Stock exchange with name " + exchangeName + " not found"));
    }

    @Override
    @Transactional
    public void createStockExchange(StockExchange stockExchange) {
        stockExchangeRepository.save(stockExchange);
    }

    public void updateStockExchangeLiveInMarket(StockExchange stockExchange) {
        stockExchange.updateLiveInMarket();
        stockExchangeRepository.save(stockExchange);
    }

    @Override
    @Transactional
    public void deleteStockFromExchange(String exchangeName, Long stockId) {
        Optional<StockExchange> stockExchangeOptional = stockExchangeRepository.findByName(exchangeName);
        if (stockExchangeOptional.isPresent()) {

            StockExchange stockExchange = stockExchangeOptional.get();
            Optional<Stock> stockOptional = stockExchange.getStocks().stream()
                    .filter(stock -> stock.getId().equals(stockId))
                    .findFirst();
            if (stockOptional.isPresent()) {
                stockExchange.getStocks().remove(stockOptional.get());
                stockExchangeRepository.save(stockExchange);
            } else {
                throw new StockNotFoundException("Stock with ID " + stockId + " not found in the exchange " + exchangeName);
            }
        } else {
            throw new StockExchangeNotFoundException("Stock exchange with name " + exchangeName + " not found");
        }
    }
}
