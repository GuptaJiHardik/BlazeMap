package com.blaze.map.controller;

import com.blaze.map.entity.Keyword;
import com.blaze.map.repository.KeywordRepository;
import com.blaze.map.service.NewsPollingService;
import com.blaze.map.service.TwitterPollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TwitterPollingService twitterPollingService;
    private final NewsPollingService newsPollingService;
    private final KeywordRepository keywordRepository;
    //private final RedditPollingService redditPollingService;

//    @GetMapping("/test-reddit")
//    public String testRedditPolling() {
//        redditPollingService.fetchAndSavePostsFromReddit();
//        return "Reddit polling triggered!";
//    }

    @GetMapping("/poll-now")
    public String pollNow() {
        var keywordList = keywordRepository.findAll()
                .stream()
                .map(Keyword::getValue)
                .toList();

        twitterPollingService.fetchAndSaveTweets(keywordList);
        newsPollingService.fetchAndSaveArticles(keywordList);
        return "Polled Twitter & News at " + LocalDateTime.now();
    }
}
