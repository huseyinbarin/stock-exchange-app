package com.barin.stockexchangeapplication.adapter.persist;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.process.stock.repository.StockRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StockRepositoryImpl implements StockRepository {
    private final Map<Long, Stock> stockMap;

    public StockRepositoryImpl() {
        this.stockMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return Optional.ofNullable(stockMap.get(id));
    }

    @Override
    public void save(Stock stock) {
        stockMap.put(stock.getId(), stock);
    }

    @Override
    public void deleteById(Long id) {
        stockMap.remove(id);
    }

}
