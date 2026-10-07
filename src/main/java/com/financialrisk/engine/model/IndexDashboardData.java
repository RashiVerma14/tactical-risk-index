package com.financialrisk.engine.model;

import java.util.List;

public class IndexDashboardData {

    private List<RebalancedStock> stocks;
    private double totalWeight;

    public IndexDashboardData() {
    }

    public IndexDashboardData(
            List<RebalancedStock> stocks,
            double totalWeight) {

        this.stocks = stocks;
        this.totalWeight = totalWeight;
    }

    public List<RebalancedStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<RebalancedStock> stocks) {
        this.stocks = stocks;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }
}