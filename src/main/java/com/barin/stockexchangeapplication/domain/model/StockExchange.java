package com.barin.stockexchangeapplication.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExchange {

    private Long id;
    private String name;
    private String description;
    @Getter
    private boolean liveInMarket;
    private List<Stock> stocks;

    public void updateLiveInMarket() {
        this.liveInMarket = stocks.size() >= 5;
    }
}
