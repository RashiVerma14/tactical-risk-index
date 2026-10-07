package com.financialrisk.engine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.financialrisk.engine.model.IndexStock;
import com.financialrisk.engine.model.IndexStockData;
import com.financialrisk.engine.model.MarketData;

@Service
public class TacticalIndexService {

    private final MarketDataService marketDataService;

    public TacticalIndexService(
            MarketDataService marketDataService) {

        this.marketDataService = marketDataService;
    }

    public List<IndexStock> getInitialIndex() {

        List<IndexStock> stocks = new ArrayList<>();

        stocks.add(new IndexStock("AAPL", "Apple", 10.0));
        stocks.add(new IndexStock("MSFT", "Microsoft", 10.0));
        stocks.add(new IndexStock("AMZN", "Amazon", 10.0));
        stocks.add(new IndexStock("GOOGL", "Alphabet", 10.0));
        stocks.add(new IndexStock("NVDA", "NVIDIA", 10.0));
        stocks.add(new IndexStock("META", "Meta", 10.0));
        stocks.add(new IndexStock("JPM", "JPMorgan Chase", 10.0));
        stocks.add(new IndexStock("XOM", "Exxon Mobil", 10.0));
        stocks.add(new IndexStock("TSLA", "Tesla", 10.0));
        stocks.add(new IndexStock("JNJ", "Johnson & Johnson", 10.0));

        return stocks;
    }

    public List<IndexStockData> getIndexWithMarketData() {

        List<IndexStock> stocks = getInitialIndex();

        List<IndexStockData> result = new ArrayList<>();

        for (IndexStock stock : stocks) {

            try {

                MarketData marketData =
                        marketDataService.getMarketData(
                                stock.getSymbol()
                        );

                result.add(
                        new IndexStockData(
                                stock.getSymbol(),
                                stock.getCompanyName(),
                                stock.getWeight(),
                                marketData.getPrice(),
                                marketData.getChangePercent()
                        )
                );

            } catch (Exception e) {

                result.add(
                        new IndexStockData(
                                stock.getSymbol(),
                                stock.getCompanyName(),
                                stock.getWeight(),
                                0.0,
                                0.0
                        )
                );
            }
        }

        return result;
    }
}