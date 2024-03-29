package com.barin.stockexchangeapplication.adapter.rest;

import com.barin.stockexchangeapplication.adapter.rest.stock_exchange.StockExchangeController;
import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.model.StockExchange;
import com.barin.stockexchangeapplication.domain.process.stock_exchange.service.StockExchangeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(StockExchangeController.class)
@AutoConfigureMockMvc
public class StockExchangeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockExchangeService stockExchangeService;

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetStockExchangeByName() throws Exception {
        String exchangeName = "NASDAQ";

        StockExchange stockExchange = new StockExchange();
        stockExchange.setName(exchangeName);
        stockExchange.setDescription("Stock exchange for technology companies");

        doNothing().when(stockExchangeService).createStockExchange(any(StockExchange.class));
        doNothing().when(stockExchangeService).addStockToExchange(any(String.class), any(Stock.class));
        doNothing().when(stockExchangeService).deleteStockFromExchange(any(String.class), any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock-exchange/{name}", exchangeName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Disabled
    public void shouldCreateStockExchange() throws Exception {
        StockExchange stockExchange = new StockExchange();
        stockExchange.setName("ING");
        stockExchange.setDescription("ING Hub");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock-exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockExchange))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Disabled
    public void shouldAddStockToExchange() throws Exception {
        String exchangeName = "NASDAQ";

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("NBA");
        stock.setCurrentPrice(BigDecimal.valueOf(150.00));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock-exchange/{exchangeName}/add-stock", exchangeName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stock))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Disabled
    public void shouldDeleteStockFromExchange() throws Exception {
        String exchangeName = "NASDAQ";
        Long stockId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stock-exchange/{exchangeName}/delete-stock/{stockId}", exchangeName, stockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}