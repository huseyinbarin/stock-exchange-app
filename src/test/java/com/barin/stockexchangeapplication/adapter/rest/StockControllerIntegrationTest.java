package com.barin.stockexchangeapplication.adapter.rest;

import com.barin.stockexchangeapplication.domain.model.Stock;
import com.barin.stockexchangeapplication.domain.process.stock.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockService stockService;

    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnStockByGivenId() throws Exception {
        Stock stock = new Stock(1L, "Test Stock", "Test Description", BigDecimal.valueOf(100.00), null);
        stockService.createStock(stock);

        Long stockId = stock.getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock/" + stockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Stock retrievedStock = new ObjectMapper().readValue(responseBody, Stock.class);
        assertEquals(stockId, retrievedStock.getId());
        assertEquals("Test Stock", retrievedStock.getName());
        assertEquals("Test Description", retrievedStock.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), retrievedStock.getCurrentPrice());

        stockService.deleteStockById(stockId);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldThrowExceptionWhenStockIdNotFound() throws Exception {
        Long nonExistingStockId = 999L;

        String errorMessage = Objects.requireNonNull(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock/{id}", nonExistingStockId))
                .andExpect(status().isInternalServerError())
                .andReturn()
                .getResolvedException()).getMessage();
        assertEquals(errorMessage, "Stock with ID 999 not found");


    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldCreateStock() throws Exception {
        Stock stock = new Stock(1L, "Test Stock", "Test Description", BigDecimal.valueOf(100.00), null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(stock)))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(201, mvcResult.getResponse().getStatus());

        stockService.deleteStockById(stock.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldUpdateStockPriceSuccessfully() throws Exception {
        // Given
        Stock stock = new Stock(1L, "Test Stock", "Test Description", BigDecimal.valueOf(100.00), null);
        stockService.createStock(stock);

        Long stockId = stock.getId();
        BigDecimal newPrice = new BigDecimal("150.50");


        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stock/{id}", stockId)
                        .param("newPrice", newPrice.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldNotUpdateStockPriceWhenIdIsNotFound() throws Exception {
        // Given
        Long invalidStockId = 999L;
        BigDecimal newPrice = new BigDecimal("150.50");

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stock/{id}", invalidStockId)
                        .param("newPrice", newPrice.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
