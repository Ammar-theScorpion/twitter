package com.equiptal.service;

import java.util.List;

import com.equiptal.DTO.CommentDTO;
import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.model.DBConnection;
import com.equiptal.model.TweetDAO;

public class TweetService {

    public TweetDTO insert(String tweet, Integer userId) {
        Integer id = tweetDAO.insert(tweet, userId);
        return tweetDAO.findById(id);
    }

    public RetweetDTO insertRetweet(Integer tweetId, Integer userId) {
        Integer id = tweetDAO.insertRetweet(tweetId, userId);
        System.err.println(id);
        return tweetDAO.findByIdRT(id);
    }

    public Integer giveLike(Integer tweetId, Integer userId) {
        // check if you liked first;
        if (!tweetDAO.noLikeForThisTweet(tweetId, userId))
            tweetDAO.giveLike(tweetId, userId);
        else {
            tweetDAO.deleteLike(tweetId, userId);
        }
        return tweetDAO.likesCount(tweetId);
    }

    public Integer comment(Integer tweetId, Integer userId, String tweet) {
        return tweetDAO.comment(tweetId, userId, tweet);
    }

    public List<CommentDTO> getComment(Integer tweetId) {
        return tweetDAO.getComment(tweetId);
    }

    public List<CommentDTO> getComments(Integer tweetId) {
        System.err.println(tweetDAO.getComments(tweetId));
        return tweetDAO.getComments(tweetId);
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

    private TweetDAO tweetDAO = DBConnection.jdbi.onDemand(TweetDAO.class);
}
