package com.financialrisk.engine.service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.financialrisk.engine.model.NewsArticle;

@Service
public class NewsIngestionService {

    private final RestClient restClient;

    public NewsIngestionService() {
        this.restClient = RestClient.create();
    }

    public List<NewsArticle> getLatestNews() {

        String rssUrl = "https://feeds.bbci.co.uk/news/business/rss.xml";

        String xml = restClient.get()
                .uri(rssUrl)
                .retrieve()
                .body(String.class);

        return parseRss(xml);
    }

    private List<NewsArticle> parseRss(String xml) {

        List<NewsArticle> articles = new ArrayList<>();

        try {

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document document = builder.parse(
                    new ByteArrayInputStream(
                            xml.getBytes(StandardCharsets.UTF_8)
                    )
            );

            NodeList items = document.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {

                Element item = (Element) items.item(i);

                String title = getElementValue(item, "title");
                String url = getElementValue(item, "link");
                String publishedAt = getElementValue(item, "pubDate");

                articles.add(
                        new NewsArticle(
                                title,
                                url,
                                "BBC Business",
                                publishedAt
                        )
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse BBC RSS feed",
                    e
            );
        }

        return articles;
    }

    private String getElementValue(Element element, String tagName) {

        NodeList nodes = element.getElementsByTagName(tagName);

        if (nodes.getLength() == 0) {
            return "";
        }

        return nodes.item(0).getTextContent();
    }
}