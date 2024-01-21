package com.equiptal.service.tweet;

import com.equiptal.model.LikeDAO;

import jakarta.inject.Inject;

public class LikeService {
    private LikeDAO likeDAO;

    @Inject
    public LikeService(LikeDAO likeDAO) {
        this.likeDAO = likeDAO;
    }

    public Integer giveLike(Integer tweetId, Integer userId) {
        // check if you liked first;
        if (!likeDAO.noLikeForThisTweet(tweetId, userId))
            likeDAO.giveLike(tweetId, userId);
        else {
            likeDAO.deleteLike(tweetId, userId);
        }
        return likeDAO.likesCount(tweetId);
    }

}
