package com.financialrisk.engine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financialrisk.engine.model.AIAnalysisResult;

@Service
public class GroqService {

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    public GroqService(
            @Value("${groq.api.key}") String apiKey,
            @Value("${groq.model}") String model) {

        this.apiKey = apiKey;
        this.model = model;

        this.restClient = RestClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .build();
    }


    public AIAnalysisResult analyzeText(String text) {

        String prompt = """
                You are a financial risk analysis engine.

                Analyze the following financial/news headline.

                Return ONLY valid JSON.
                Do not use markdown.
                Do not add explanations outside JSON.

                Required JSON format:

                {
                  "sentimentScore": 0.75,
                  "sentiment": "POSITIVE",
                  "eventType": "OTHER",
                  "impactScore": 7,
                  "reason": "Short explanation",
                  "affectedStocks": ["NVDA"]
                }

                IMPORTANT RULES:

                1. sentimentScore:
                   - Must be between -1.0 and 1.0
                   - Positive news = positive score
                   - Negative news = negative score
                   - Neutral news = score close to 0

                2. sentiment:
                   - POSITIVE
                   - NEGATIVE
                   - NEUTRAL

                3. eventType MUST be one of:

                   GEOPOLITICAL
                   MACROECONOMIC
                   CREDIT_EVENT
                   M&A
                   PRODUCT_LAUNCH
                   CYBER_EVENT
                   LABOR_EVENT
                   OTHER

                4. impactScore:
                   - Integer from 1 to 10
                   - 1 = very low impact
                   - 10 = extremely high impact

                5. reason:
                   - Keep it short
                   - Explain why the news is positive, negative,
                     or neutral for investors

                6. affectedStocks:

                   ONLY use these 10 stock symbols:

                   AAPL
                   MSFT
                   AMZN
                   GOOGL
                   NVDA
                   META
                   JPM
                   XOM
                   TSLA
                   JNJ

                IMPORTANT STOCK MAPPING:

                Apple / iPhone / iPad / Mac / Apple AI
                -> AAPL

                Microsoft / Windows / Azure / Microsoft AI
                -> MSFT

                Amazon / AWS / Amazon Prime
                -> AMZN

                Google / Alphabet / YouTube
                -> GOOGL

                NVIDIA / Nvidia / GPU / GeForce / AI chips
                -> NVDA

                Meta / Facebook / Instagram / WhatsApp
                -> META

                JPMorgan / JPMorgan Chase / Chase
                -> JPM

                Exxon / ExxonMobil / Exxon Mobil
                -> XOM

                Tesla / Cybertruck / Tesla vehicles
                -> TSLA

                Johnson & Johnson / J&J / Janssen
                -> JNJ

                If the headline clearly relates to one of these
                companies, you MUST include its ticker.

                If it clearly relates to multiple companies,
                include all relevant tickers.

                If it does not relate to any of these companies,
                return an empty array.

                Do NOT invent a stock relationship.

                NEWS HEADLINE:
                """ + text;


        Map<String, Object> request = Map.of(

                "model",
                model,

                "messages",
                List.of(

                        Map.of(
                                "role",
                                "system",
                                "content",
                                "You are a precise financial risk analysis engine."
                        ),

                        Map.of(
                                "role",
                                "user",
                                "content",
                                prompt
                        )
                ),

                "temperature",
                0.1
        );


        Map<?, ?> response =
                restClient.post()

                        .uri("/chat/completions")

                        .header(
                                "Authorization",
                                "Bearer " + apiKey
                        )

                        .header(
                                "Content-Type",
                                "application/json"
                        )

                        .body(request)

                        .retrieve()

                        .body(Map.class);


        if (response == null) {

            throw new RuntimeException(
                    "Empty response from Groq"
            );
        }


        List<?> choices =
                (List<?>) response.get("choices");


        if (choices == null ||
                choices.isEmpty()) {

            throw new RuntimeException(
                    "No choices returned by Groq"
            );
        }


        Map<?, ?> firstChoice =
                (Map<?, ?>) choices.get(0);


        Map<?, ?> message =
                (Map<?, ?>) firstChoice.get("message");


        String content =
                String.valueOf(
                        message.get("content")
                );


        try {

            ObjectMapper mapper =
                    new ObjectMapper();


            AIAnalysisResult result =
                    mapper.readValue(
                            content,
                            AIAnalysisResult.class
                    );


            /*
             * Safety layer:
             *
             * If Groq fails to identify a stock,
             * inspect the headline ourselves.
             *
             * This does NOT create new financial
             * information. It only catches obvious
             * company names that Groq may have missed.
             */

            List<String> detectedStocks =
                    detectStocksFromHeadline(text);


            if (!detectedStocks.isEmpty()) {

                if (result.getAffectedStocks() == null ||
                        result.getAffectedStocks().isEmpty()) {

                    result.setAffectedStocks(
                            detectedStocks
                    );

                } else {

                    List<String> merged =
                            new ArrayList<>(
                                    result.getAffectedStocks()
                            );

                    for (String stock :
                            detectedStocks) {

                        if (!merged.contains(stock)) {

                            merged.add(stock);
                        }
                    }

                    result.setAffectedStocks(
                            merged
                    );
                }
            }


            return result;


        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse Groq response: "
                            + content,
                    e
            );
        }
    }


    /*
     * Detect obvious company references
     * from the news headline.
     */
    private List<String> detectStocksFromHeadline(
            String text) {

        List<String> stocks =
                new ArrayList<>();


        String title =
                text.toLowerCase();


        /*
         * Apple
         */

        if (containsAny(
                title,
                "apple",
                "iphone",
                "ipad",
                "macbook",
                "mac"
        )) {

            stocks.add("AAPL");
        }


        /*
         * Microsoft
         */

        if (containsAny(
                title,
                "microsoft",
                "azure",
                "windows"
        )) {

            stocks.add("MSFT");
        }


        /*
         * Amazon
         */

        if (containsAny(
                title,
                "amazon",
                "aws"
        )) {

            stocks.add("AMZN");
        }


        /*
         * Google
         */

        if (containsAny(
                title,
                "google",
                "alphabet",
                "youtube"
        )) {

            stocks.add("GOOGL");
        }


        /*
         * NVIDIA
         */

        if (containsAny(
                title,
                "nvidia",
                "gpu",
                "geforce"
        )) {

            stocks.add("NVDA");
        }


        /*
         * Meta
         */

        if (containsAny(
                title,
                "meta",
                "facebook",
                "instagram",
                "whatsapp"
        )) {

            stocks.add("META");
        }


        /*
         * JPMorgan
         */

        if (containsAny(
                title,
                "jpmorgan",
                "jpmorgan chase",
                "chase bank"
        )) {

            stocks.add("JPM");
        }


        /*
         * Exxon
         */

        if (containsAny(
                title,
                "exxon",
                "exxonmobil",
                "exxon mobil"
        )) {

            stocks.add("XOM");
        }


        /*
         * Tesla
         */

        if (containsAny(
                title,
                "tesla",
                "cybertruck"
        )) {

            stocks.add("TSLA");
        }


        /*
         * Johnson & Johnson
         */

        if (containsAny(
                title,
                "johnson & johnson",
                "johnson and johnson",
                "janssen"
        )) {

            stocks.add("JNJ");
        }


        return stocks;
    }


    /*
     * Utility method for keyword detection.
     */

    private boolean containsAny(
            String text,
            String... keywords) {

        for (String keyword : keywords) {

            if (text.contains(keyword)) {

                return true;
            }
        }

        return false;
    }
}