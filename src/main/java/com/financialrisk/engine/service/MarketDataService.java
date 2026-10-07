package com.financialrisk.engine.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.financialrisk.engine.model.MarketData;

@Service
public class MarketDataService {

    private final RestClient restClient;
    private final String apiKey;

    private final Map<String, CachedMarketData> cache =
            new ConcurrentHashMap<>();

    /*
     * Alpha Vantage free API has limited requests.
     * Cache market data for 10 minutes.
     */
    private static final long CACHE_DURATION =
            10 * 60 * 1000;

    /*
     * Fallback prices are used only when
     * Alpha Vantage cannot provide data.
     *
     * This prevents the dashboard from showing
     * $0.00 during the demo.
     */
    private static final Map<String, MarketData> FALLBACK_DATA =
            Map.of(
                    "AAPL",
                    new MarketData("AAPL", 250.00, 0.80),

                    "MSFT",
                    new MarketData("MSFT", 510.00, 0.55),

                    "AMZN",
                    new MarketData("AMZN", 230.00, -0.35),

                    "GOOGL",
                    new MarketData("GOOGL", 245.00, 0.42),

                    "NVDA",
                    new MarketData("NVDA", 180.00, 1.25),

                    "META",
                    new MarketData("META", 750.00, -0.20),

                    "JPM",
                    new MarketData("JPM", 310.00, 0.30),

                    "XOM",
                    new MarketData("XOM", 115.00, -0.45),

                    "TSLA",
                    new MarketData("TSLA", 450.00, 1.10),

                    "JNJ",
                    new MarketData("JNJ", 205.00, 0.15)
            );


    public MarketDataService(
            @Value("${alphavantage.api.key}") String apiKey) {

        this.apiKey = apiKey;

        this.restClient =
                RestClient.builder()
                        .baseUrl("https://www.alphavantage.co")
                        .build();
    }


    public MarketData getMarketData(String stockSymbol) {

        String symbol =
                stockSymbol.toUpperCase();


        /*
         * Check cache first
         */

        CachedMarketData cached =
                cache.get(symbol);


        if (cached != null &&
                System.currentTimeMillis()
                        - cached.timestamp
                        < CACHE_DURATION) {

            return cached.data;
        }


        /*
         * Try Alpha Vantage
         */

        try {

            Map<?, ?> response =
                    restClient.get()

                            .uri(uriBuilder ->
                                    uriBuilder
                                            .path("/query")
                                            .queryParam(
                                                    "function",
                                                    "GLOBAL_QUOTE"
                                            )
                                            .queryParam(
                                                    "symbol",
                                                    symbol
                                            )
                                            .queryParam(
                                                    "apikey",
                                                    apiKey
                                            )
                                            .build()
                            )

                            .retrieve()

                            .body(Map.class);


            if (response == null) {

                throw new RuntimeException(
                        "Empty response"
                );
            }


            Object quoteObject =
                    response.get("Global Quote");


            if (!(quoteObject instanceof Map)) {

                throw new RuntimeException(
                        "Global Quote unavailable"
                );
            }


            Map<?, ?> quote =
                    (Map<?, ?>) quoteObject;


            if (quote.isEmpty()) {

                throw new RuntimeException(
                        "Empty quote"
                );
            }


            String priceText =
                    String.valueOf(
                            quote.get("05. price")
                    );


            String changeText =
                    String.valueOf(
                            quote.get("10. change percent")
                    );


            /*
             * Validate response
             */

            if (priceText.equals("null") ||
                    changeText.equals("null")) {

                throw new RuntimeException(
                        "Invalid market response"
                );
            }


            double price =
                    Double.parseDouble(
                            priceText
                    );


            double changePercent =
                    Double.parseDouble(
                            changeText.replace(
                                    "%",
                                    ""
                            )
                    );


            MarketData marketData =
                    new MarketData(
                            symbol,
                            price,
                            changePercent
                    );


            /*
             * Store successful API result
             */

            cache.put(
                    symbol,
                    new CachedMarketData(
                            marketData,
                            System.currentTimeMillis()
                    )
            );


            System.out.println(
                    "Alpha Vantage data loaded: "
                            + symbol
                            + " = $"
                            + price
            );


            return marketData;


        } catch (Exception e) {

            System.out.println(
                    "Alpha Vantage failed for "
                            + symbol
                            + ": "
                            + e.getMessage()
            );


            /*
             * If old cached data exists,
             * use it.
             */

            if (cached != null) {

                System.out.println(
                        "Using cached data for "
                                + symbol
                );

                return cached.data;
            }


            /*
             * Otherwise use fallback data.
             */

            MarketData fallback =
                    FALLBACK_DATA.get(symbol);


            if (fallback != null) {

                System.out.println(
                        "Using fallback market data for "
                                + symbol
                );

                return fallback;
            }


            /*
             * Final safety fallback
             */

            return new MarketData(
                    symbol,
                    0.0,
                    0.0
            );
        }
    }


    /*
     * Optional method:
     * clear all cached market data.
     */

    public void clearCache() {

        cache.clear();

        System.out.println(
                "Market data cache cleared."
        );
    }


    /*
     * Internal cache object
     */

    private static class CachedMarketData {

        private final MarketData data;

        private final long timestamp;


        private CachedMarketData(
                MarketData data,
                long timestamp) {

            this.data = data;

            this.timestamp =
                    timestamp;
        }
    }
}