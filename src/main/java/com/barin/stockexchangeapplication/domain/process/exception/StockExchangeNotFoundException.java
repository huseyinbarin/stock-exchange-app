package com.barin.stockexchangeapplication.domain.process.exception;

public class StockExchangeNotFoundException extends StockException {
    public StockExchangeNotFoundException(String message) {
        super(message);
    }
}