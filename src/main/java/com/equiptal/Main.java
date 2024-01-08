package com.equiptal;

import static io.javalin.apibuilder.ApiBuilder.*;

import com.equiptal.controller.TwitterController;
import com.equiptal.controller.TwitterControllerAPI;
import com.equiptal.otherServices.AppModule;
import com.equiptal.otherServices.AuthImplementaion;
import com.equiptal.service.TweetService;
import com.equiptal.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinPebble;

public class Main {

    public Main() {
        Injector injector = Guice.createInjector(new AppModule());
        TweetService tweetService = injector.getInstance(TweetService.class);
        UserService userService = injector.getInstance(UserService.class);
        authImplementaion = injector.getInstance(AuthImplementaion.class);
        twitterControllerAPI = injector.getInstance(TwitterControllerAPI.class);
        twitterController = injector.getInstance(TwitterController.class);
    }

    public void configureRoutes(Javalin app) {
        app.routes(() -> {
            path("api", () -> {
                path("tweet", () -> {
                    post(twitterControllerAPI.tweet);
                    post("/like", twitterControllerAPI.giveLike);
                    post("/comment", twitterControllerAPI.comment);
                    post("/getComments", twitterControllerAPI.getTweetComments);
                    post("/retweet", twitterControllerAPI.retweet);
                });

                post("login", authImplementaion::handeLogin);
                post("signup", authImplementaion::handeSignup);
            });

            // VIEW //
            before("/", authImplementaion.ensureLogin);
            get("/", twitterController.homePage);
            get("/logout", authImplementaion.logout);
            get("/login", authImplementaion.renderLogin);
            get("/signup", authImplementaion.renderSignup);
            get("/profile", twitterController.profile);

        });
    }

    public static void main(String[] args) {

        JavalinRenderer.register(new JavalinPebble(), ".peb", ".pebble");
        Javalin app = Javalin.create(config -> {

            config.staticFiles.add("/public"); // client files/ static, img, css...
            config.jetty.sessionHandler(() -> AuthImplementaion.fileSessionHandler());
        }).start(8084);

        new Main().configureRoutes(app);

    }

    private TwitterControllerAPI twitterControllerAPI;
    private AuthImplementaion authImplementaion;
    private TwitterController twitterController;

}