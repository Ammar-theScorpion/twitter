package com.equiptal.service.tweet;

import java.util.List;

import com.equiptal.DTO.CommentDTO;
import com.equiptal.model.CommentDAO;

import jakarta.inject.Inject;

public class CommentService {
    private CommentDAO commentDAO;

    @Inject
    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public Integer comment(Integer tweetId, Integer userId, String tweet) {
        return commentDAO.comment(tweetId, userId, tweet);
    }

    public List<CommentDTO> getComment(Integer tweetId) {
        return commentDAO.getComment(tweetId);
    }

    public List<CommentDTO> getComments(Integer tweetId) {
        System.err.println(commentDAO.getComments(tweetId));
        return commentDAO.getComments(tweetId);
    }

}
