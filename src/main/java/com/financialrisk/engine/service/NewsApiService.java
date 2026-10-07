package com.financialrisk.engine.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.financialrisk.engine.model.NewsArticle;

@Service
public class NewsApiService {

    private final RestClient restClient;
    private final String apiKey;

    public NewsApiService(
            @Value("${newsapi.key}") String apiKey) {

        this.apiKey = apiKey;

        this.restClient = RestClient.builder()
                .baseUrl("https://newsapi.org")
                .build();
    }


    public List<NewsArticle> getLatestNews() {

        List<ScoredArticle> scoredArticles =
                new ArrayList<>();

        /*
         * Search specifically around companies
         * in our tactical index.
         */
        String query =
                "Apple OR Microsoft OR Amazon OR "
                + "Google OR NVIDIA OR Meta OR "
                + "JPMorgan OR Exxon OR Tesla OR "
                + "\"Johnson & Johnson\"";


        try {

            Map<?, ?> response =
                    restClient.get()

                            .uri(uriBuilder ->
                                    uriBuilder
                                            .path("/v2/everything")

                                            .queryParam(
                                                    "q",
                                                    query
                                            )

                                            .queryParam(
                                                    "language",
                                                    "en"
                                            )

                                            .queryParam(
                                                    "sortBy",
                                                    "publishedAt"
                                            )

                                            .queryParam(
                                                    "pageSize",
                                                    50
                                            )

                                            .queryParam(
                                                    "searchIn",
                                                    "title,description"
                                            )

                                            .build()
                            )

                            .header(
                                    "X-Api-Key",
                                    apiKey
                            )

                            .retrieve()

                            .body(Map.class);


            if (response == null) {

                throw new RuntimeException(
                        "Empty response from NewsAPI"
                );
            }


            Object articlesObject =
                    response.get("articles");


            if (!(articlesObject instanceof List)) {

                throw new RuntimeException(
                        "Articles not found in NewsAPI response"
                );
            }


            List<?> articles =
                    (List<?>) articlesObject;


            for (Object articleObject :
                    articles) {

                if (!(articleObject instanceof Map)) {
                    continue;
                }


                Map<?, ?> article =
                        (Map<?, ?>) articleObject;


                String title =
                        String.valueOf(
                                article.get("title")
                        );


                String description =
                        String.valueOf(
                                article.get("description")
                        );


                if (title.equals("null")
                        || title.isBlank()) {

                    continue;
                }


                /*
                 * Remove obvious commercial /
                 * shopping content.
                 */
                if (isIrrelevantArticle(
                        title,
                        description)) {

                    continue;
                }


                /*
                 * Give financially relevant
                 * articles a higher score.
                 */
                int relevanceScore =
                        calculateRelevanceScore(
                                title,
                                description
                        );


                /*
                 * Ignore very low relevance
                 * articles.
                 */
                if (relevanceScore < 2) {
                    continue;
                }


                String url =
                        String.valueOf(
                                article.get("url")
                        );


                String publishedAt =
                        String.valueOf(
                                article.get("publishedAt")
                        );


                String source =
                        getSourceName(article);


                NewsArticle newsArticle =
                        new NewsArticle(
                                title,
                                url,
                                source,
                                publishedAt
                        );


                scoredArticles.add(
                        new ScoredArticle(
                                newsArticle,
                                relevanceScore
                        )
                );
            }


        } catch (Exception e) {

            System.out.println(
                    "NewsAPI failed: "
                            + e.getMessage()
            );
        }


        /*
         * Highest financial relevance first.
         */
        scoredArticles.sort(
                Comparator.comparingInt(
                        ScoredArticle::getScore
                ).reversed()
        );


        /*
         * Return maximum 10 articles.
         */
        List<NewsArticle> result =
                new ArrayList<>();


        for (int i = 0;
             i < Math.min(10, scoredArticles.size());
             i++) {

            result.add(
                    scoredArticles
                            .get(i)
                            .getArticle()
            );
        }


        return result;
    }


    /*
     * Calculate financial relevance.
     */
    private int calculateRelevanceScore(
            String title,
            String description) {

        String text =
                (title + " " + description)
                        .toLowerCase();


        int score = 0;


        /*
         * Strong financial events.
         */
        String[] highImpactKeywords = {

                "earnings",
                "revenue",
                "profit",
                "loss",
                "guidance",
                "forecast",
                "acquisition",
                "acquires",
                "merger",
                "lawsuit",
                "investigation",
                "regulator",
                "regulatory",
                "fine",
                "penalty",
                "layoffs",
                "jobs",
                "restructuring",
                "partnership",
                "contract",
                "investment",
                "shares",
                "stock",
                "market",
                "quarter",
                "financial",
                "sales",
                "growth",
                "decline",
                "cyberattack",
                "cybersecurity",
                "hack",
                "breach",
                "tariff",
                "sanctions",
                "interest rate"
        };


        for (String keyword :
                highImpactKeywords) {

            if (text.contains(keyword)) {

                score += 2;
            }
        }


        /*
         * Product/technology events.
         */
        String[] eventKeywords = {

                "launch",
                "launched",
                "announces",
                "announced",
                "chip",
                "ai",
                "artificial intelligence",
                "cloud",
                "technology",
                "software"
        };


        for (String keyword :
                eventKeywords) {

            if (text.contains(keyword)) {

                score++;
            }
        }


        /*
         * Company relevance.
         */
        String[] companyKeywords = {

                "apple",
                "microsoft",
                "amazon",
                "google",
                "alphabet",
                "nvidia",
                "meta",
                "jpmorgan",
                "exxon",
                "tesla",
                "johnson & johnson",
                "janssen"
        };


        for (String keyword :
                companyKeywords) {

            if (text.contains(keyword)) {

                score += 2;
            }
        }


        return score;
    }


    /*
     * Filter shopping, deals and other
     * irrelevant commercial content.
     */
    private boolean isIrrelevantArticle(
            String title,
            String description) {

        String text =
                (title + " " + description)
                        .toLowerCase();


        String[] unwantedKeywords = {

                "deal",
                "deals",
                "discount",
                "coupon",
                "promo",
                "promotion",
                "buy now",
                "best price",
                "lowest price",
                "shopping",
                "sale",
                "gift guide",
                "review roundup",
                "black friday",
                "prime day",
                "cyber monday",
                "where to buy",
                "price drop"
        };


        for (String keyword :
                unwantedKeywords) {

            if (text.contains(keyword)) {

                return true;
            }
        }


        return false;
    }


    /*
     * Extract source name safely.
     */
    private String getSourceName(
            Map<?, ?> article) {

        Object sourceObject =
                article.get("source");


        if (sourceObject instanceof Map) {

            Map<?, ?> sourceMap =
                    (Map<?, ?>) sourceObject;


            Object sourceName =
                    sourceMap.get("name");


            if (sourceName != null) {

                return String.valueOf(
                        sourceName
                );
            }
        }


        return "NewsAPI";
    }


    /*
     * Small internal wrapper used for
     * relevance ranking.
     */
    private static class ScoredArticle {

        private final NewsArticle article;
        private final int score;


        private ScoredArticle(
                NewsArticle article,
                int score) {

            this.article = article;
            this.score = score;
        }


        public NewsArticle getArticle() {
            return article;
        }


        public int getScore() {
            return score;
        }
    }
}