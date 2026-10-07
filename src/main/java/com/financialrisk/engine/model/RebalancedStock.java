package com.financialrisk.engine.model;

import java.util.List;

public class RebalancedStock {

    private String symbol;
    private String companyName;

    private double oldWeight;
    private double newWeight;
    private double change;

    private double price;
    private double dailyChange;

    private double sentimentScore;
    private int impactScore;
    private String reason;
    private List<String> affectedStocks;

    public RebalancedStock() {
    }

    public RebalancedStock(
            String symbol,
            String companyName,
            double oldWeight,
            double newWeight,
            double price,
            double dailyChange,
            double sentimentScore,
            int impactScore,
            String reason,
            List<String> affectedStocks) {

        this.symbol = symbol;
        this.companyName = companyName;
        this.oldWeight = oldWeight;
        this.newWeight = newWeight;
        this.change = newWeight - oldWeight;

        this.price = price;
        this.dailyChange = dailyChange;

        this.sentimentScore = sentimentScore;
        this.impactScore = impactScore;
        this.reason = reason;
        this.affectedStocks = affectedStocks;
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

    public double getOldWeight() {
        return oldWeight;
    }

    public void setOldWeight(double oldWeight) {
        this.oldWeight = oldWeight;
    }

    public double getNewWeight() {
        return newWeight;
    }

    public void setNewWeight(double newWeight) {
        this.newWeight = newWeight;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDailyChange() {
        return dailyChange;
    }

    public void setDailyChange(double dailyChange) {
        this.dailyChange = dailyChange;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public int getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(int impactScore) {
        this.impactScore = impactScore;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getAffectedStocks() {
        return affectedStocks;
    }

    public void setAffectedStocks(List<String> affectedStocks) {
        this.affectedStocks = affectedStocks;
    }
}