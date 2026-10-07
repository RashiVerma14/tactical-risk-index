package com.financialrisk.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.MarketData;
import com.financialrisk.engine.service.MarketDataService;

@RestController
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(
            MarketDataService marketDataService) {

        this.marketDataService = marketDataService;
    }

    @GetMapping("/api/market/{symbol}")
    public MarketData getMarketData(
            @PathVariable String symbol) {

        return marketDataService.getMarketData(
                symbol.toUpperCase()
        );
    }
}