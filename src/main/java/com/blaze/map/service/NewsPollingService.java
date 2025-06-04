package com.blaze.map.service;

import com.blaze.map.entity.SourceFeed;
import com.blaze.map.repository.SourceFeedRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsPollingService {

    @Value("${news.api.key}")
    private String newsApiKey;

    private final SourceFeedRepository sourceFeedRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // Example GNews endpoint
    private static final String NEWS_URL =
            "https://gnews.io/api/v4/search?q={keyword}&token={apiKey}";

    public NewsPollingService(SourceFeedRepository sourceFeedRepository) {
        this.sourceFeedRepository = sourceFeedRepository;
    }

    public void fetchAndSaveArticles(List<String> keywords) {
        for (String keyword : keywords) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(
                        NEWS_URL, String.class, keyword, newsApiKey
                );

                if (response.getStatusCode().is2xxSuccessful()) {
                    SourceFeed feed = SourceFeed.builder()
                            .sourceType("news")
                            .keyword(keyword)
                            .rawContent(response.getBody())
                            .fetchedAt(LocalDateTime.now())      
                            .build();

                    sourceFeedRepository.save(feed);
                }
            } catch (Exception e) {
                System.err.println("Error fetching news for \"" + keyword + "\": " + e.getMessage());
            }
        }
    }
}
