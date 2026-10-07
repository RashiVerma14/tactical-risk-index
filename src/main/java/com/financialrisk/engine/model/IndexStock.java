package com.financialrisk.engine.model;

public class IndexStock {

    private String symbol;
    private String companyName;
    private double weight;

    public IndexStock() {
    }

    public IndexStock(
            String symbol,
            String companyName,
            double weight) {

        this.symbol = symbol;
        this.companyName = companyName;
        this.weight = weight;
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
}