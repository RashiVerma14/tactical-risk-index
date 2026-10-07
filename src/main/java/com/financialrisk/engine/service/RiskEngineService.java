package com.financialrisk.engine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.financialrisk.engine.model.AIAnalysisResult;
import com.financialrisk.engine.model.NewsArticle;
import com.financialrisk.engine.model.RiskSignal;

@Service
public class RiskEngineService {

    private final UnifiedNewsService unifiedNewsService;
    private final GroqService groqService;

    private List<RiskSignal> cachedSignals =
            new ArrayList<>();

    public RiskEngineService(
            UnifiedNewsService unifiedNewsService,
            GroqService groqService) {

        this.unifiedNewsService = unifiedNewsService;
        this.groqService = groqService;
    }

    public synchronized List<RiskSignal> analyzeNews() {

        /*
         * Return cached analysis if available.
         * This prevents repeated Groq API calls.
         */
        if (!cachedSignals.isEmpty()) {
            return cachedSignals;
        }

        List<NewsArticle> news =
                unifiedNewsService.getAllNews();

        List<RiskSignal> signals =
                new ArrayList<>();

        /*
         * Analyze maximum 5 articles
         * to stay within Groq limits.
         */
        int limit =
                Math.min(news.size(), 5);

        for (int i = 0; i < limit; i++) {

            NewsArticle article =
                    news.get(i);

            AIAnalysisResult aiResult =
                    groqService.analyzeText(
                            article.getTitle()
                    );

            RiskSignal signal =
                    new RiskSignal(
                            article.getTitle(),
                            article.getUrl(),
                            article.getSource(),
                            article.getPublishedAt(),
                            aiResult.getSentimentScore(),
                            aiResult.getSentiment(),
                            aiResult.getEventType(),
                            aiResult.getImpactScore(),
                            aiResult.getReason(),
                            aiResult.getAffectedStocks()
                    );

            signals.add(signal);
        }

        cachedSignals = signals;

        return cachedSignals;
    }

    public synchronized void clearCache() {

        cachedSignals =
                new ArrayList<>();
    }
}