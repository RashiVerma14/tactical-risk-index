package com.financialrisk.engine.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.financialrisk.engine.model.SentimentResult;

@Service
public class SentimentService {

    private final Set<String> positiveWords = Set.of(
            "growth",
            "profit",
            "profits",
            "gain",
            "gains",
            "success",
            "successful",
            "increase",
            "increased",
            "surge",
            "surges",
            "strong",
            "positive",
            "win",
            "wins",
            "boost",
            "rise",
            "rises",
            "record"
    );

    private final Set<String> negativeWords = Set.of(
            "loss",
            "losses",
            "decline",
            "declined",
            "fall",
            "falls",
            "drop",
            "drops",
            "crisis",
            "risk",
            "risks",
            "hack",
            "hacked",
            "fraud",
            "scam",
            "strike",
            "negative",
            "weak",
            "layoff",
            "layoffs",
            "collapse",
            "catastrophic"
    );

    public SentimentResult analyze(String text) {

        if (text == null || text.isBlank()) {
            return new SentimentResult(0.0, "NEUTRAL");
        }

        String[] words = text
                .toLowerCase()
                .replaceAll("[^a-zA-Z ]", " ")
                .split("\\s+");

        int positiveCount = 0;
        int negativeCount = 0;

        for (String word : words) {

            if (positiveWords.contains(word)) {
                positiveCount++;
            }

            if (negativeWords.contains(word)) {
                negativeCount++;
            }
        }

        int total = positiveCount + negativeCount;

        if (total == 0) {
            return new SentimentResult(0.0, "NEUTRAL");
        }

        double score =
                (double) (positiveCount - negativeCount) / total;

        score = Math.max(-1.0, Math.min(1.0, score));

        String sentiment;

        if (score > 0.2) {
            sentiment = "POSITIVE";
        } else if (score < -0.2) {
            sentiment = "NEGATIVE";
        } else {
            sentiment = "NEUTRAL";
        }

        return new SentimentResult(score, sentiment);
    }
}