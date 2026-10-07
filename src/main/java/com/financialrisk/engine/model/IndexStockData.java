package com.financialrisk.engine.model;

public class IndexStockData {

    private String symbol;
    private String companyName;

    private double weight;
    private double price;
    private double changePercent;

    public IndexStockData() {
    }

    public IndexStockData(
            String symbol,
            String companyName,
            double weight,
            double price,
            double changePercent) {

        this.symbol = symbol;
        this.companyName = companyName;
        this.weight = weight;
        this.price = price;
        this.changePercent = changePercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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