//package com.barin.stockexchangeapplication;
//
//import com.barin.stockexchangeapplication.domain.model.Stock;
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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@Disabled
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class StockApplicationTests {
//
//    @LocalServerPort
//    private int port;
//
//    private String baseUrl = "http://localhost";
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
//        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/stock");
//    }
//
//
//    @Test
//    @Sql(statements = "DELETE FROM STOCK")
//    public void shouldAddStockGiven() {
//        Stock stock = new Stock();
//        stock.setId(1L);
//        stock.setName("Test Stock");
//        restTemplate.postForObject(baseUrl, stock, Stock.class);
//        assertEquals(1, h2Repository.findAll().size());
//    }
//
//    @Test
//    @Sql(statements = "DELETE FROM STOCK")
//    public void shouldReturnStockGivenId() {
//        Stock stock = new Stock();
//        stock.setId(2L);
//        stock.setName("APPLE");
//        restTemplate.postForObject(baseUrl, stock, Stock.class);
//        Stock stockFound = restTemplate.getForObject(baseUrl + "/{id}", Stock.class, 2L);
//        assertEquals(1, h2Repository.findAll().size());
//        assertEquals("APPLE", stockFound.getName());
//    }
//
//
//
//}
