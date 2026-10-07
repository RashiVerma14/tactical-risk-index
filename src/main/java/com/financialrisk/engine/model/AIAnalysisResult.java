package com.financialrisk.engine.model;

import java.util.List;

public class AIAnalysisResult {

    private double sentimentScore;
    private String sentiment;
    private String eventType;
    private int impactScore;
    private String reason;

    private List<String> affectedStocks;

    public AIAnalysisResult() {
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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