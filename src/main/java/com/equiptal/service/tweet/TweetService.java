package com.equiptal.service.tweet;

import java.util.List;

import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.model.TweetDAO;

import jakarta.inject.Inject;

public class TweetService {

    private TweetDAO tweetDAO;

    @Inject
    public TweetService(TweetDAO tweetService) {
        this.tweetDAO = tweetService;
    }

    public TweetDTO insert(String tweet, Integer userId) {
        Integer id = tweetDAO.insert(tweet, userId);
        return tweetDAO.findById(id);
    }

    public RetweetDTO insertRetweet(Integer tweetId, Integer userId) {
        Integer id = tweetDAO.insertRetweet(tweetId, userId);
        System.err.println(id);
        return tweetDAO.findByIdRT(id);
    }

    public TweetDTO getTweet(Integer id) {
        return tweetDAO.getTweet(id);
    }

    public List<TweetDTO> getTweets(Integer id, Integer limit) {
        System.err.println("--------------------------------");
        System.err.println(5);
        System.err.println(5 * (limit - 1));
        System.err.println("--------------------------------");
        List<TweetDTO> tw = tweetDAO.getTweets(id, 5, 5 * (limit - 1));
        System.err.println(tw.size());
        return tw;
    }

    public List<TweetDTO> getTweets(Integer id) {
        int defaultLimit = 5;
        int defaultOffset = 0;

        return tweetDAO.getTweets(id, defaultLimit, defaultOffset);
    }

    public List<RetweetDTO> getRetweets(Integer id, Integer limit) {
        return tweetDAO.getRetweets(id, 5, 5 * (limit - 1));
    }

    public String getTweetOwner(Integer tweetId) {
        return tweetDAO.getTweetOwner(tweetId);
    }

}
