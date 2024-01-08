package com.equiptal.controller;

import java.util.HashMap;
import java.util.Map;

import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.service.TweetService;
import com.equiptal.service.UserService;

import io.javalin.http.Handler;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TwitterControllerAPI {
    private TweetService tweetService;
    private UserService userService;

    @Inject
    public TwitterControllerAPI(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
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
        System.err.print(t);
        model.put("tweet", t);
        ctx.render("templates/tweet.peb", model);
        ctx.status(201);
    };

    public Handler giveLike = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);
        Integer likesCount = tweetService.giveLike(tweetId, userId);
        ctx.result(likesCount + "");
    };

    public Handler comment = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        String comment = ctx.formParam("comment");
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);
        System.err.println(comment);

        tweetService.comment(tweetId, userId, comment);
    };

    public Handler getTweetComments = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        System.err.println(tweetId);
        ctx.json(tweetService.getComments(tweetId));
    };

    public Handler retweet = ctx -> {
        Integer tweetId = Integer.parseInt(ctx.formParam("tweet-id"));
        String userName = ctx.sessionAttribute("currentUser");
        Integer userId = userService.findIdByName(userName);

        RetweetDTO t = tweetService.insertRetweet(tweetId, userId); // old tweet and current user
        Map<String, Object> model = new HashMap<>();

        model.put("tweets", t);
        ctx.status(201);
    };

}