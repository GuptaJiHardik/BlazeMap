package com.blaze.map.service;

import com.blaze.map.entity.SourceFeed;
import com.blaze.map.repository.SourceFeedRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TwitterPollingService {

    @Value("${twitter.bearer.token}")
    private String bearerToken;

    private final SourceFeedRepository sourceFeedRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String SEARCH_URL =
            "https://api.twitter.com/2/tweets/search/recent"
                    + "?query={keyword}&max_results=10&tweet.fields=created_at,text,author_id";


    public TwitterPollingService(SourceFeedRepository sourceFeedRepository) {
        this.sourceFeedRepository = sourceFeedRepository;
    }

    public void fetchAndSaveTweets(List<String> keywords) {
        for (String keyword : keywords) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(bearerToken);
                HttpEntity<Void> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        SEARCH_URL,
                        HttpMethod.GET,
                        entity,
                        String.class,
                        keyword
                );

                if (response.getStatusCode().is2xxSuccessful()) {
                    SourceFeed feed = SourceFeed.builder()
                            .sourceType("twitter")
                            .keyword(keyword)
                            .rawContent(response.getBody())
                            .fetchedAt(LocalDateTime.now())
                            .build();
                    sourceFeedRepository.save(feed);
                }
            } catch (Exception e) {
                // Log and continue
                System.err.println("Error fetching tweets for \"" + keyword + "\": " + e.getMessage());
            }
        }
    }
}
