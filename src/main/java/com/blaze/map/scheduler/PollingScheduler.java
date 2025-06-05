package com.blaze.map.scheduler;

import com.blaze.map.entity.Keyword;
import com.blaze.map.repository.KeywordRepository;
import com.blaze.map.service.TwitterPollingService;
import com.blaze.map.service.NewsPollingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PollingScheduler {

    private final TwitterPollingService twitterPollingService;
    private final NewsPollingService newsPollingService;
    private final KeywordRepository keywordRepository;

    public PollingScheduler(
            TwitterPollingService twitterPollingService,
            NewsPollingService newsPollingService,
            KeywordRepository keywordRepository) {
        this.twitterPollingService = twitterPollingService;
        this.newsPollingService = newsPollingService;
        this.keywordRepository = keywordRepository;
    }

    /**
     * Runs at midnight every 3rd day (1st,4th,7th,10th,13th,16th,19th,22nd,25th,28th).
     * 10 tweets per keyword
     */
    @Scheduled(cron = "0 0 0 */3 * ?")
    public void pollTwitterOnly() {
        List<String> keywordList = keywordRepository.findAll()
                .stream()
                .map(Keyword::getValue)
                .collect(Collectors.toList());

        twitterPollingService.fetchAndSaveTweets(keywordList);
    }

    /**
     * Runs twice per hour: at minute 0 and minute 30 of every hour.
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void pollNewsOnly() {
        List<String> keywordList = keywordRepository.findAll()
                .stream()
                .map(Keyword::getValue)
                .collect(Collectors.toList());

        newsPollingService.fetchAndSaveArticles(keywordList);
    }
}
