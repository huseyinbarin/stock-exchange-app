package com.barin.stockexchangeapplication.domain.process.stock_exchange;


import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.model.StockExchange;
import com.barin.stockexchangeapplication.domain.process.exception.StockExchangeNotFoundException;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.repository.StockExchangeRepository;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.service.StockExchangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockExchangeServiceTest {

    @Mock
    private StockExchangeRepository stockExchangeRepository;

    @InjectMocks
    private StockExchangeServiceImpl stockExchangeService;

    @Test
    void shouldGetStockExchangeByName() {
        // given
        String exchangeName = "NASDAQ";
        StockExchange expectedStockExchange = new StockExchange();
        expectedStockExchange.setName(exchangeName);
        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(expectedStockExchange));

        // when
        StockExchange result = stockExchangeService.getStockExchangeByName(exchangeName);

        // then
        assertEquals(expectedStockExchange, result);
    }

    @Test
    void shouldCreateStockExchange() {
        // given
        StockExchange stockExchange = new StockExchange();

        // when
        stockExchangeService.createStockExchange(stockExchange);

        // then
        verify(stockExchangeRepository, times(1)).save(stockExchange);
    }

    @Test
    void shouldAddStockToExchange() {
        // given
        String exchangeName = "APPLE";
        Stock stock = new Stock();
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName(exchangeName);
        List<Stock> stocks = new ArrayList<>();
        stockExchange.setStocks(stocks);
        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(stockExchange));

        // when
        stockExchangeService.addStockToExchange(exchangeName, stock);

        // then
        verify(stockExchangeRepository, times(1)).save(stockExchange);
        assertEquals(1, stockExchange.getStocks().size());
    }

    @Test
    void shouldDeleteStockFromExchange() {
        // given
        String exchangeName = "SUN";
        Long stockId = 1L;
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName(exchangeName);
        List<Stock> stocks = new ArrayList<>();
        Stock stock = new Stock();
        stock.setId(stockId);
        stocks.add(stock);
        stockExchange.setStocks(stocks);
        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(stockExchange));

        // when
        stockExchangeService.deleteStockFromExchange(exchangeName, stockId);

        // then
        verify(stockExchangeRepository, times(1)).save(stockExchange);
        assertEquals(0, stockExchange.getStocks().size());
    }

    @Test
    void shouldThrowExceptionWhenStockExchangeNotFound() {
        // given
        String exchangeName = "UNKNOWN";
        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.empty());

        // when & then
        assertThrows(StockExchangeNotFoundException.class,
                () -> stockExchangeService.addStockToExchange(exchangeName, new Stock()));

        assertThrows(StockExchangeNotFoundException.class,
                () -> stockExchangeService.deleteStockFromExchange(exchangeName, 1L));
    }

    @Test
    void shouldNotSetStockExchangeLiveInMarketWhenLessThanFiveStocks() {
        // Given
        StockExchange stockExchange = new StockExchange();
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock());
        stockExchange.setStocks(stocks);

        // When
        stockExchangeService.updateStockExchangeLiveInMarket(stockExchange);

        // Then
        Mockito.verify(stockExchangeRepository, times(1)).save(stockExchange);

        assert !stockExchange.isLiveInMarket();
    }

    @Test
    void shouldSetStockExchangeLiveInMarketWhenMoreThanFourStocks() {
        // Given
        StockExchange stockExchange = new StockExchange();
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock());
        stocks.add(new Stock());
        stocks.add(new Stock());
        stocks.add(new Stock());
        stocks.add(new Stock());
        stockExchange.setStocks(stocks);

        // When
        stockExchangeService.updateStockExchangeLiveInMarket(stockExchange);

        // Then
        Mockito.verify(stockExchangeRepository, times(1)).save(stockExchange);
        assert stockExchange.isLiveInMarket();
    }
}