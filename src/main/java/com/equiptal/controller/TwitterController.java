package com.equiptal.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.DTO.UserDTO;
import com.equiptal.otherServices.Tweet.TweetBase;
import com.equiptal.service.TweetService;
import com.equiptal.service.UserService;

import io.javalin.http.Handler;
import jakarta.inject.Inject;

public class TwitterController {

    private TweetService tweetService;
    private UserService userService;

    @Inject
    public TwitterController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    public Handler homePage = ctx -> {
        Map<String, Object> model = new HashMap<>();
        List<TweetDTO> tweets = tweetService.getTweets(0);
        List<RetweetDTO> retweets = tweetService.getRetweets(0);
        List<TweetBase> combinedList = new ArrayList<>();
        combinedList.addAll(tweets);
        combinedList.addAll(retweets);

        Comparator<TweetBase> createDateComparator = Comparator.comparing(TweetBase::getCreateDate).reversed();

        Collections.sort(combinedList, createDateComparator);

        model.put("tweets", combinedList);
        ctx.render("templates/home.peb", model);
    };

    public Handler profile = ctx -> {

        String userName = ctx.queryParam("profile");
        Integer userId = userService.findIdByName(userName);
        UserDTO userInfo = userService.getUserProfile(userId);

        Map<String, Object> model = new HashMap<>();
        model.put("user", userInfo);
        model.put("tweets", tweetService.getTweets(userId));

        ctx.render("templates/profile.peb", model);
    };
}
