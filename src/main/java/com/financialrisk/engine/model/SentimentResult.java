package com.financialrisk.engine.model;

public class SentimentResult {

    private double sentimentScore;
    private String sentiment;

    public SentimentResult() {
    }

    public SentimentResult(double sentimentScore, String sentiment) {
        this.sentimentScore = sentimentScore;
        this.sentiment = sentiment;
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
}