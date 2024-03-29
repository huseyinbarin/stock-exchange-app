package com.barin.stockexchangeapplication.adapter.persist;

import com.barin.stockexchangeapplication.domain.model.StockExchange;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.repository.StockExchangeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StockExchangeRepositoryImpl implements StockExchangeRepository {

    private final Map<String, StockExchange> stockExchangeMap = new ConcurrentHashMap<>();

    @Override
    public Optional<StockExchange> findByName(String name) {
        return Optional.ofNullable(stockExchangeMap.get(name));
    }

    @Override
    public Optional<List<StockExchange>> findAll() {
        return Optional.of(new ArrayList<>(stockExchangeMap.values()));
    }

    @Override
    public void deleteByName(String name) {
        stockExchangeMap.remove(name);
    }

    @Override
    public void save(StockExchange stockExchange) {
        stockExchangeMap.put(stockExchange.getName(), stockExchange);
    }
}
