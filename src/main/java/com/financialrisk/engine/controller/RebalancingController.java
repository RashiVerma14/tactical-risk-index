package com.financialrisk.engine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialrisk.engine.model.IndexDashboardData;
import com.financialrisk.engine.model.RebalancedStock;
import com.financialrisk.engine.service.RebalancingService;

@RestController
public class RebalancingController {

    private final RebalancingService rebalancingService;

    public RebalancingController(
            RebalancingService rebalancingService) {

        this.rebalancingService = rebalancingService;
    }

    @GetMapping("/api/index/rebalanced")
    public List<RebalancedStock> getRebalancedIndex() {

        return rebalancingService.rebalance();
    }

    @GetMapping("/api/index/dashboard")
    public IndexDashboardData getDashboardData() {

        List<RebalancedStock> stocks =
                rebalancingService.rebalance();

        double totalWeight =
                rebalancingService.calculateTotalWeight(
                        stocks
                );

        return new IndexDashboardData(
                stocks,
                totalWeight
        );
    }
}