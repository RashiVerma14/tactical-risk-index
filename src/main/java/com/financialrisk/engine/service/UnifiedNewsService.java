package com.financialrisk.engine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.financialrisk.engine.model.NewsArticle;

@Service
public class UnifiedNewsService {

    private final NewsIngestionService newsIngestionService;
    private final NewsApiService newsApiService;

    public UnifiedNewsService(
            NewsIngestionService newsIngestionService,
            NewsApiService newsApiService) {

        this.newsIngestionService = newsIngestionService;
        this.newsApiService = newsApiService;
    }


    public List<NewsArticle> getAllNews() {

        List<NewsArticle> allNews =
                new ArrayList<>();


        /*
         * IMPORTANT:
         *
         * NewsAPI comes FIRST because it is
         * specifically searching for companies
         * in our tactical index.
         */
        try {

            allNews.addAll(
                    newsApiService.getLatestNews()
            );

        } catch (Exception e) {

            System.out.println(
                    "NewsAPI unavailable: "
                            + e.getMessage()
            );
        }


        /*
         * BBC Business news comes second.
         *
         * This provides additional market context.
         */
        try {

            allNews.addAll(
                    newsIngestionService.getLatestNews()
            );

        } catch (Exception e) {

            System.out.println(
                    "BBC news unavailable: "
                            + e.getMessage()
            );
        }


        return allNews;
    }
}