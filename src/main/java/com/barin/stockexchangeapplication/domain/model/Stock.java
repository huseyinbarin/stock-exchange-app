package com.barin.stockexchangeapplication.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private Long id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private Timestamp lastUpdate;

}