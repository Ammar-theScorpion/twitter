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

    public void comment(Integer tweetId, Integer userId, String tweet) {
        tweetDAO.comment(tweetId, userId, tweet);
    }

    public List<CommentDTO> getComments(Integer tweetId) {
        System.err.println(tweetDAO.getComments(tweetId));
        return tweetDAO.getComments(tweetId);
    }

    public List<TweetDTO> getTweets(Integer id) {
        return tweetDAO.getTweets(id);
    }

    public List<RetweetDTO> getRetweets(Integer id) {
        return tweetDAO.getRetweets(id);
    }

    public String getTweetOwner(Integer tweetId) {
        return tweetDAO.getTweetOwner(tweetId);
    }

    private TweetDAO tweetDAO = DBConnection.jdbi.onDemand(TweetDAO.class);
}
