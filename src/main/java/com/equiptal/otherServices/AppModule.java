package com.equiptal.otherServices;

import com.equiptal.model.CommentDAO;
import com.equiptal.model.DBConnection;
import com.equiptal.model.LikeDAO;
import com.equiptal.model.TweetDAO;
import com.equiptal.model.UserDAO;
import com.equiptal.service.UserService;
import com.equiptal.service.tweet.TweetService;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

    public AppModule() {
        new DBConnection();
    }

    @Override
    protected void configure() {
        bind(UserDAO.class).toInstance(DBConnection.jdbi.onDemand(UserDAO.class));
        bind(TweetDAO.class).toInstance(DBConnection.jdbi.onDemand(TweetDAO.class));
        bind(LikeDAO.class).toInstance(DBConnection.jdbi.onDemand(LikeDAO.class));
        bind(CommentDAO.class).toInstance(DBConnection.jdbi.onDemand(CommentDAO.class));

        bind(TweetService.class);
        bind(UserService.class);
    }
}
