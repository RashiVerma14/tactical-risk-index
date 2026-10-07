package com.financialrisk.engine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.financialrisk.engine.model.IndexStock;
import com.financialrisk.engine.model.MarketData;
import com.financialrisk.engine.model.RebalancedStock;
import com.financialrisk.engine.model.RiskSignal;

@Service
public class RebalancingService {

    private final TacticalIndexService tacticalIndexService;
    private final RiskEngineService riskEngineService;
    private final MarketDataService marketDataService;

    /*
     * Portfolio limits
     */
    private static final double MIN_WEIGHT = 5.0;
    private static final double MAX_WEIGHT = 15.0;

    /*
     * Controls how strongly AI signals affect
     * portfolio weights.
     */
    private static final double REBALANCING_FACTOR = 0.8;


    public RebalancingService(
            TacticalIndexService tacticalIndexService,
            RiskEngineService riskEngineService,
            MarketDataService marketDataService) {

        this.tacticalIndexService = tacticalIndexService;
        this.riskEngineService = riskEngineService;
        this.marketDataService = marketDataService;
    }


    public List<RebalancedStock> rebalance() {

        /*
         * Get the original 10-stock index.
         */
        List<IndexStock> stocks =
                tacticalIndexService.getInitialIndex();


        /*
         * Get AI-generated risk signals.
         */
        List<RiskSignal> signals =
                riskEngineService.analyzeNews();


        List<RebalancedStock> result =
                new ArrayList<>();


        /*
         * Process every stock.
         */
        for (IndexStock stock : stocks) {

            double oldWeight =
                    stock.getWeight();


            /*
             * Aggregate AI signals affecting
             * this particular stock.
             */
            double weightedSentiment = 0.0;

            double totalImpact = 0.0;

            int strongestImpact = 0;

            String reason =
                    "No direct AI risk signal.";

            List<String> affectedStocks =
                    new ArrayList<>();


            for (RiskSignal signal : signals) {

                if (!affectsStock(stock, signal)) {
                    continue;
                }


                double sentiment =
                        signal.getSentimentScore();


                double impact =
                        signal.getImpactScore();


                /*
                 * Impact-weighted sentiment.
                 *
                 * A high-impact event has
                 * greater influence.
                 */
                weightedSentiment +=
                        sentiment * impact;


                totalImpact += impact;


                /*
                 * Keep the strongest event
                 * for display.
                 */
                if (signal.getImpactScore()
                        >= strongestImpact) {

                    strongestImpact =
                            signal.getImpactScore();

                    reason =
                            signal.getReason();
                }


                if (signal.getAffectedStocks()
                        != null) {

                    for (String ticker :
                            signal.getAffectedStocks()) {

                        if (!affectedStocks
                                .contains(ticker)) {

                            affectedStocks.add(
                                    ticker
                            );
                        }
                    }
                }
            }


            /*
             * Calculate average
             * impact-weighted sentiment.
             */
            double averageSentiment = 0.0;

            if (totalImpact > 0) {

                averageSentiment =
                        weightedSentiment
                                / totalImpact;
            }


            /*
             * Calculate the strength of
             * the AI signal.
             *
             * Maximum impact = 10
             */
            double impactMultiplier =
                    strongestImpact / 10.0;


            /*
             * Final adjustment.
             *
             * Example:
             *
             * sentiment = +0.8
             * impact = 8
             *
             * adjustment =
             * 0.8 × 0.8 × 0.8 × 5
             *
             * = +2.56%
             */
            double adjustment =
                    averageSentiment
                            * impactMultiplier
                            * REBALANCING_FACTOR
                            * 5.0;


            /*
             * Apply adjustment.
             */
            double newWeight =
                    oldWeight + adjustment;


            /*
             * Enforce portfolio limits.
             */
            newWeight =
                    Math.max(
                            MIN_WEIGHT,
                            Math.min(
                                    MAX_WEIGHT,
                                    newWeight
                            )
                    );


            /*
             * Get market data.
             */
            double price = 0.0;

            double dailyChange = 0.0;


            try {

                MarketData marketData =
                        marketDataService
                                .getMarketData(
                                        stock.getSymbol()
                                );


                price =
                        marketData.getPrice();


                dailyChange =
                        marketData
                                .getChangePercent();


            } catch (Exception e) {

                System.out.println(
                        "Market data unavailable for "
                                + stock.getSymbol()
                );
            }


            /*
             * Create result.
             */
            result.add(
                    new RebalancedStock(

                            stock.getSymbol(),

                            stock.getCompanyName(),

                            oldWeight,

                            newWeight,

                            price,

                            dailyChange,

                            averageSentiment,

                            strongestImpact,

                            reason,

                            affectedStocks
                    )
            );
        }


        /*
         * Normalize everything so that
         * total portfolio weight = 100%.
         */
        normalizeWeights(result);


        return result;
    }


    /*
     * Determines whether an AI signal
     * affects a particular stock.
     */
    private boolean affectsStock(
            IndexStock stock,
            RiskSignal signal) {


        /*
         * First check AI-generated
         * affectedStocks.
         */
        if (signal.getAffectedStocks()
                != null) {

            for (String affectedStock :
                    signal.getAffectedStocks()) {

                if (affectedStock != null
                        && affectedStock
                        .equalsIgnoreCase(
                                stock.getSymbol()
                        )) {

                    return true;
                }
            }
        }


        /*
         * Also check the headline.
         */
        String title =
                signal.getTitle()
                        .toLowerCase();


        String symbol =
                stock.getSymbol()
                        .toLowerCase();


        String company =
                stock.getCompanyName()
                        .toLowerCase();


        if (title.contains(symbol)
                || title.contains(company)) {

            return true;
        }


        /*
         * Company-specific keyword
         * fallback.
         */
        return switch (stock.getSymbol()) {

            case "AAPL" ->
                    title.contains("apple")
                    || title.contains("iphone")
                    || title.contains("ipad")
                    || title.contains("mac");

            case "MSFT" ->
                    title.contains("microsoft")
                    || title.contains("azure")
                    || title.contains("windows");

            case "AMZN" ->
                    title.contains("amazon")
                    || title.contains("aws");

            case "GOOGL" ->
                    title.contains("google")
                    || title.contains("alphabet")
                    || title.contains("youtube");

            case "NVDA" ->
                    title.contains("nvidia")
                    || title.contains("gpu")
                    || title.contains("geforce");

            case "META" ->
                    title.contains("meta")
                    || title.contains("facebook")
                    || title.contains("instagram")
                    || title.contains("whatsapp");

            case "JPM" ->
                    title.contains("jpmorgan")
                    || title.contains("jpmorgan chase")
                    || title.contains("chase bank");

            case "XOM" ->
                    title.contains("exxon")
                    || title.contains("exxonmobil")
                    || title.contains("exxon mobil");

            case "TSLA" ->
                    title.contains("tesla")
                    || title.contains("cybertruck");

            case "JNJ" ->
                    title.contains("johnson")
                    || title.contains("johnson & johnson")
                    || title.contains("janssen");

            default ->
                    false;
        };
    }


    /*
     * Normalize weights to exactly 100%.
     */
    private void normalizeWeights(
            List<RebalancedStock> stocks) {


        double totalWeight =
                stocks.stream()
                        .mapToDouble(
                                RebalancedStock::getNewWeight
                        )
                        .sum();


        if (totalWeight <= 0) {
            return;
        }


        /*
         * First normalization pass.
         */
        for (RebalancedStock stock :
                stocks) {

            double normalizedWeight =
                    (stock.getNewWeight()
                            / totalWeight)
                            * 100.0;


            stock.setNewWeight(
                    normalizedWeight
            );
        }


        /*
         * Recalculate total after
         * normalization.
         */
        double normalizedTotal =
                stocks.stream()
                        .mapToDouble(
                                RebalancedStock::getNewWeight
                        )
                        .sum();


        /*
         * Correct floating-point
         * rounding error.
         */
        double difference =
                100.0 - normalizedTotal;


        if (!stocks.isEmpty()) {

            RebalancedStock first =
                    stocks.get(0);


            first.setNewWeight(
                    first.getNewWeight()
                            + difference
            );
        }


        /*
         * Calculate final weight changes.
         */
        for (RebalancedStock stock :
                stocks) {

            stock.setChange(
                    stock.getNewWeight()
                            - stock.getOldWeight()
            );
        }
    }


    /*
     * Calculate total portfolio weight.
     */
    public double calculateTotalWeight(
            List<RebalancedStock> stocks) {

        return stocks.stream()
                .mapToDouble(
                        RebalancedStock::getNewWeight
                )
                .sum();
    }
}