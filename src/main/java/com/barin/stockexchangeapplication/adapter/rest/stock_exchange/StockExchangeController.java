package com.barin.stockexchangeapplication.adapter.rest.stock_exchange;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.model.StockExchange;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.service.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

    private final StockExchangeService stockExchangeService;

    @Autowired
    public StockExchangeController(StockExchangeService stockExchangeService) {
        this.stockExchangeService = stockExchangeService;
    }

    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<StockExchange> getStockExchangeByName(@PathVariable String name) {
        StockExchange stockExchange = stockExchangeService.getStockExchangeByName(name);
        return ResponseEntity.ok().body(stockExchange);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> createStockExchange(@RequestBody StockExchange stockExchange) {
        stockExchangeService.createStockExchange(stockExchange);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{exchangeName}/add-stock", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> addStockToExchange(@PathVariable String exchangeName, @RequestBody Stock stock) {
        stockExchangeService.addStockToExchange(exchangeName, stock);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{exchangeName}/delete-stock/{stockId}", produces = "application/json")
    public ResponseEntity<Void> deleteStockFromExchange(@PathVariable String exchangeName, @PathVariable Long stockId) {
        stockExchangeService.deleteStockFromExchange(exchangeName, stockId);
        return ResponseEntity.ok().build();
    }
}
