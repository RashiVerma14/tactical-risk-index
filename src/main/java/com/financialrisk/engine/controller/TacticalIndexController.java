package com.financialrisk.engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.IndexStock;
import com.financialrisk.engine.model.IndexStockData;
import com.financialrisk.engine.service.TacticalIndexService;

@RestController
public class TacticalIndexController {

    private final TacticalIndexService tacticalIndexService;

    public TacticalIndexController(
            TacticalIndexService tacticalIndexService) {

        this.tacticalIndexService = tacticalIndexService;
    }

    @GetMapping("/api/index")
    public List<IndexStock> getIndex() {

        return tacticalIndexService.getInitialIndex();
    }

    @GetMapping("/api/index/market")
    public List<IndexStockData> getIndexWithMarketData() {

        return tacticalIndexService.getIndexWithMarketData();
    }
}