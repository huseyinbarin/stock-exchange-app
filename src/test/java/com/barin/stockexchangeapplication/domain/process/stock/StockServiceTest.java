package com.barin.stockexchangeapplication.domain.process.stock;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.process.exception.StockNotFoundException;
import com.barin.stockexchangeapplication.domain.process.stock.repository.StockRepository;
import com.barin.stockexchangeapplication.domain.process.stock.service.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;


    @Test
    void shouldGetTheStockWhenItsIdProvided() {

        // given
        Stock mockStock = new Stock();
        mockStock.setId(1L);
        mockStock.setName("Test Stock");
        when(stockRepository.findById(1L)).thenReturn(Optional.of(mockStock));

        // when
        Stock result = stockService.getStockById(1L);

        // then
        assertEquals(1L, result.getId());
        assertEquals("Test Stock", result.getName());
    }

    @Test
    public void shouldThrowExceptionWhenStockNotFound() {
        // given
        Long stockId = 1L;
        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        // when
        StockNotFoundException exception = assertThrows(
                StockNotFoundException.class,
                () -> stockService.getStockById(stockId)
        );

        // then
        String expectedMessage = "Stock with ID " + stockId + " not found";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void shouldCreateStock() {
        // given
        Stock stock = new Stock();

        // when
        stockService.createStock(stock);

        // then
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void shouldUpdateStockPriceWhenFound() {
        // given
        Long stockId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(100);
        Stock stock = new Stock();
        stock.setId(stockId);
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        // when
        stockService.updateStockPrice(stockId, newPrice);

        // then
        assertEquals(newPrice, stock.getCurrentPrice());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingStockPriceIfNotFound() {
        // given
        Long stockId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(100);
        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        // when & then
        StockNotFoundException exception = assertThrows(
                StockNotFoundException.class,
                () -> stockService.updateStockPrice(stockId, newPrice)
        );
        assertEquals("Stock with ID " + stockId + " not found", exception.getMessage());
    }

    @Test
    public void shouldDeleteStockById() {
        // given
        Long stockId = 1L;

        // when
        stockService.deleteStockById(stockId);

        // then
        verify(stockRepository, times(1)).deleteById(stockId);
    }

}
