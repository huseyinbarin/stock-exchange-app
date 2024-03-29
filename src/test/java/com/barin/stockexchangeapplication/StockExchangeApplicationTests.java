//package com.barin.stockexchangeapplication;
//
//import com.barin.stockexchangeapplication.domain.model.Stock;
//import com.barin.stockexchangeapplication.domain.model.StockExchange;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@Disabled
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class StockExchangeApplicationTests {
//
//    @LocalServerPort
//    private int port;
//
//    private String baseUrl;
//
//    private static RestTemplate restTemplate;
//
//    @Autowired
//    private TestH2Repository h2Repository;
//
//    @BeforeAll
//    public static void init() {
//        restTemplate = new RestTemplate();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        baseUrl = "http://localhost:" + port + "/api/v1/stock-exchange";
//    }
//
//    @Test
//    @Sql(statements = "DELETE FROM STOCK_EXCHANGE")
//    public void shouldCreateStockExchange() {
//        StockExchange stockExchange = new StockExchange();
//        stockExchange.setName("NYSE");
//        stockExchange.setDescription("New York");
//
//        restTemplate.postForObject(baseUrl, stockExchange, Void.class);
//
//        assertEquals(1, h2Repository.findAll().size());
//    }
//
//    @Test
//    @Sql(statements = "DELETE FROM STOCK_EXCHANGE")
//    public void shouldGetStockExchangeByName() {
//
//        StockExchange stockExchange = new StockExchange();
//        stockExchange.setName("LSE");
//        stockExchange.setDescription("London");
//        restTemplate.postForObject(baseUrl, stockExchange, Void.class);
//
//        // Get the created stock exchange by name
//        StockExchange responseStockExchange = restTemplate.getForObject(baseUrl + "/{name}", StockExchange.class, "LSE");
//
//        assertEquals("London", responseStockExchange.getDescription());
//    }
//
//    @Test
//    @Sql(statements = {"DELETE FROM STOCK_EXCHANGE", "INSERT INTO STOCK_EXCHANGE (name, description) VALUES ('NASDAQ', 'New York')"})
//    public void shouldAddStockToExchange() {
//        // Create a stock to add to the exchange
//        Stock stock = new Stock();
//        stock.setName("APPLE");
//        stock.setCurrentPrice(BigDecimal.valueOf(150.50));
//        stock.setLastUpdate(Timestamp.valueOf("2024-04-01 12:00:00"));
//
//        restTemplate.postForObject(baseUrl + "/NASDAQ/add-stock", stock, Void.class);
//
//        assertEquals(1, h2Repository.findAll().size());
//    }
//
//    @Test
//    @Sql(statements = {"DELETE FROM STOCK_EXCHANGE", "INSERT INTO STOCK_EXCHANGE (name, description) VALUES ('NASDAQ', 'New York')"})
//    public void shouldDeleteStockFromExchange() {
//
//        Stock stock = new Stock();
//        stock.setName("GOOGLE");
//        stock.setCurrentPrice(BigDecimal.valueOf(2000.00));
//        stock.setLastUpdate(Timestamp.valueOf("2024-04-01 12:00:00"));
//
//        // Add the stock to the exchange
//        restTemplate.postForObject(baseUrl + "/NASDAQ/add-stock", stock, Void.class);
//
//        // Delete the added stock from the exchange
//        restTemplate.delete(baseUrl + "/NASDAQ/delete-stock/{stockId}", "GOOGLE");
//
//        assertEquals(0, h2Repository.findAll().size());
//    }
//}
