package com.barin.stockexchangeapplication.config;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.model.StockExchange;
import com.barin.stockexchangeapplication.domain.process.stock.repository.StockRepository;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.repository.StockExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DummyDataInitializer implements CommandLineRunner {

    private final StockExchangeRepository stockExchangeRepository;
    private final StockRepository stockRepository;

    public DummyDataInitializer(StockExchangeRepository stockExchangeRepository, StockRepository stockRepository) {
        this.stockExchangeRepository = stockExchangeRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) {
        StockExchange stockExchange1 = new StockExchange();
        stockExchange1.setName("BRKLYN");
        stockExchange1.setDescription("Brooklyn Stock Exchange");

        List<Stock> stocks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Stock stock = new Stock();
            stock.setId((long) i);
            stock.setName("Stock" + i);
            stock.setDescription("Description of Stock" + i);
            stock.setCurrentPrice(BigDecimal.valueOf(100 + i * 10));
            stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            stocks.add(stock);
            stockRepository.save(stock);
            log.info("Stock: {} is saved", stock);
        }

        stockExchange1.setStocks(stocks);
        stockExchangeRepository.save(stockExchange1);
        log.info("Stock exchange: {} is saved", stockExchange1);

    }
}