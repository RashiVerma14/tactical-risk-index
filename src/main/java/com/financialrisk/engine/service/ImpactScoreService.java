package com.financialrisk.engine.service;

import org.springframework.stereotype.Service;

@Service
public class ImpactScoreService {

    public int calculateImpact(String text, String eventType) {

        if (text == null || text.isBlank()) {
            return 1;
        }

        String news = text.toLowerCase();

        int score = 3;

        // High-impact events
        if (eventType.equals("CREDIT_EVENT")) {
            score += 4;
        } else if (eventType.equals("M&A")) {
            score += 3;
        } else if (eventType.equals("GEOPOLITICAL")) {
            score += 3;
        } else if (eventType.equals("MACROECONOMIC")) {
            score += 3;
        } else if (eventType.equals("CYBER_EVENT")) {
            score += 3;
        } else if (eventType.equals("LABOR_EVENT")) {
            score += 2;
        } else if (eventType.equals("PRODUCT_LAUNCH")) {
            score += 1;
        }

        // Extremely strong risk keywords
        if (containsAny(news,
                "crisis",
                "collapse",
                "bankruptcy",
                "default",
                "war",
                "major breach",
                "catastrophic")) {

            score += 2;
        }

        // Keep score within required 1-10 range
        return Math.min(10, Math.max(1, score));
    }

    private boolean containsAny(String text, String... keywords) {

        for (String keyword : keywords) {

            if (text.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}