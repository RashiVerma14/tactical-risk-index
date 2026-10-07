package com.financialrisk.engine.model;

import java.util.List;

public class RiskSignal {

    private String title;
    private String url;
    private String source;
    private String publishedAt;

    private double sentimentScore;
    private String sentiment;
    private String eventType;
    private int impactScore;
    private String reason;

    private List<String> affectedStocks;

    public RiskSignal() {
    }

    public RiskSignal(
            String title,
            String url,
            String source,
            String publishedAt,
            double sentimentScore,
            String sentiment,
            String eventType,
            int impactScore,
            String reason,
            List<String> affectedStocks) {

        this.title = title;
        this.url = url;
        this.source = source;
        this.publishedAt = publishedAt;
        this.sentimentScore = sentimentScore;
        this.sentiment = sentiment;
        this.eventType = eventType;
        this.impactScore = impactScore;
        this.reason = reason;
        this.affectedStocks = affectedStocks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
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