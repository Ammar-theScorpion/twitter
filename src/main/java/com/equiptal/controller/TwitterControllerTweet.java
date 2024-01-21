package com.equiptal.controller;

import java.util.HashMap;
import java.util.Map;

import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.service.UserService;
import com.equiptal.service.tweet.CommentService;
import com.equiptal.service.tweet.LikeService;
import com.equiptal.service.tweet.TweetService;

import io.javalin.http.Handler;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TwitterControllerTweet {
    private TweetService tweetService;
    private UserService userService;
    private LikeService likeService;
    private CommentService commentService;

    @Inject
    public TwitterControllerTweet(TweetService tweetService, UserService userService, CommentService commentService,
            LikeService likeService) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    public Handler tweet = ctx -> {
        String userTweet = ctx.formParam("tweet");
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);

        Map<String, Object> model = new HashMap<>();
        TweetDTO t = tweetService.insert(userTweet, userId);
        // retweet
        // Integer tweetId = Integer.parseInt(retweet);
        // String tweetUserName = tweetService.getTweetOwner(tweetId);
        model.put("tweet", t);
        ctx.render("templates/tweet/tweet.peb", model);
        ctx.status(201);
    };

    public Handler giveLike = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);
        Integer likesCount = likeService.giveLike(tweetId, userId);
        ctx.result(likesCount + "");
    };

    public Handler comment = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        String comment = ctx.formParam("comment");
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);

        Integer commentId = commentService.comment(tweetId, userId, comment);
        Map<String, Object> model = new HashMap<>();
        model.put("tweets", commentService.getComment(commentId));
        model.put("onlyComment", true);

        ctx.render("templates/tweet/comments.peb", model);
    };

    public Handler getTweetComments = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        Map<String, Object> model = new HashMap<>();
        model.put("tweet", tweetService.getTweet(tweetId));
        model.put("onComment", true);

        model.put("tweets", commentService.getComments(tweetId));
        ctx.render("templates/tweet/comments.peb", model);
    };

    public Handler retweet = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);

        RetweetDTO t = tweetService.insertRetweet(tweetId, userId); // old tweet and current user
        Map<String, Object> model = new HashMap<>();
        model.put("tweet", t);
        System.err.println(tweetId);
        System.err.println(userId);
        System.err.println(t);
        ctx.render("templates/tweet/retweet.peb", model);
        ctx.status(201);
    };

}