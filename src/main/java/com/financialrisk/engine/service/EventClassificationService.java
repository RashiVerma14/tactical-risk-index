package com.financialrisk.engine.service;

import org.springframework.stereotype.Service;

import com.financialrisk.engine.model.EventResult;

@Service
public class EventClassificationService {

    public EventResult classify(String text) {

        if (text == null || text.isBlank()) {
            return new EventResult("OTHER");
        }

        String news = text.toLowerCase();

        if (containsAny(news,
                "merger",
                "acquisition",
                "acquire",
                "acquired",
                "takeover",
                "buyout")) {

            return new EventResult("M&A");
        }

        if (containsAny(news,
                "inflation",
                "interest rate",
                "interest rates",
                "gdp",
                "recession",
                "unemployment",
                "central bank",
                "federal reserve")) {

            return new EventResult("MACROECONOMIC");
        }

        if (containsAny(news,
                "war",
                "sanction",
                "sanctions",
                "election",
                "geopolitical",
                "conflict",
                "tariff")) {

            return new EventResult("GEOPOLITICAL");
        }

        if (containsAny(news,
                "bankruptcy",
                "default",
                "credit downgrade",
                "debt crisis",
                "debt default")) {

            return new EventResult("CREDIT_EVENT");
        }

        if (containsAny(news,
                "launches",
                "launched",
                "new product",
                "product launch",
                "unveils",
                "unveiled")) {

            return new EventResult("PRODUCT_LAUNCH");
        }

        if (containsAny(news,
                "hack",
                "hacked",
                "cyberattack",
                "cyber attack",
                "data breach",
                "breach")) {

            return new EventResult("CYBER_EVENT");
        }

        if (containsAny(news,
                "strike",
                "layoff",
                "layoffs",
                "workers protest",
                "union")) {

            return new EventResult("LABOR_EVENT");
        }

        return new EventResult("OTHER");
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