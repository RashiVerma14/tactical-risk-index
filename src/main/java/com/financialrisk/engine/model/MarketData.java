package com.financialrisk.engine.model;

public class MarketData {

    private String symbol;
    private double price;
    private double changePercent;

    public MarketData() {
    }

    public MarketData(
            String symbol,
            double price,
            double changePercent) {

        this.symbol = symbol;
        this.price = price;
        this.changePercent = changePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }
}