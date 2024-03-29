package com.barin.stockexchangeapplication.domain.process.exception;

public class StockNotFoundException extends StockException {
    public StockNotFoundException(String message) {
        super(message);
    }
}