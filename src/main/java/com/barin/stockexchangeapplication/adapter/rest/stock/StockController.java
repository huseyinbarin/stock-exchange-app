package com.barin.stockexchangeapplication.adapter.rest.stock;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.process.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {

        Stock stock = stockService.getStockById(id);

        return ResponseEntity.ok().body(stock);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> createStock(@RequestBody Stock stock) {
        stockService.createStock(stock);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> updateStockPrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
        stockService.updateStockPrice(id, newPrice);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteStockById(@PathVariable Long id) {
        stockService.deleteStockById(id);
        return ResponseEntity.ok().build();
    }
}
