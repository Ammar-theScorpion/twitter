package com.equiptal.otherServices;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.FileSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;

import com.equiptal.service.UserService;

import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthImplementaion {
    private UserService userService;

    @Inject
    public AuthImplementaion(UserService userService) {
        this.userService = userService;
    }

    public static SessionHandler fileSessionHandler() {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(fileSessionDataStore());
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        return sessionHandler;
    }

    private static FileSessionDataStore fileSessionDataStore() {
        FileSessionDataStore fileSessionDataStore = new FileSessionDataStore();
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        File storeDir = new File(baseDir, "javalin-session-store");
        storeDir.mkdir();
        fileSessionDataStore.setStoreDir(storeDir);
        return fileSessionDataStore;
    }

    public Handler handeLogin = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("auth_type", "Log In");
        model.put("auth_url", "login");

        if (ctx.method() == HandlerType.POST) {
            System.err.println(ctx.method());
            String userName = ctx.formParam("userName");
            String password = ctx.formParam("pass");
            String result = userService.login(userName, password);
            if (result.equals("404")) {

                model.put("error", "user name or password is worng");
                model.put("name", userName);
                model.put("pass", password);

            } else {
                ctx.sessionAttribute("currentUser", userName);
                ctx.header("HX-Redirect", "/");
                ctx.redirect("/", HttpStatus.SEE_OTHER);
            }
        }
        ctx.render("templates/auth/login.peb", model);
    };

    public Handler handeSignup = ctx -> {
        Map<String, Object> model = new HashMap<>();
        model.put("auth_type", "Sign Up");
        model.put("auth_url", "signup");
        if (ctx.method() == HandlerType.POST) {
            String userName = ctx.formParam("userName");
            String password = ctx.formParam("pass");
            String result = userService.createUser(userName, password);

            if (result.equals("201")) {
                // want to fix this
                ctx.sessionAttribute("currentUser", userName);
                ctx.header("HX-Redirect", "/");
                ctx.redirect("/", HttpStatus.SEE_OTHER);
            } else {
                ctx.status(HttpStatus.SEE_OTHER);
                model.put("error", result);
                model.put("name", userName);
                model.put("pass", password);

            }
        }

        ctx.render("templates/auth/signup.peb", model);
    };

    public Handler logout = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/login");
    };

    public Handler ensureLogin = ctx -> {
        if (ctx.sessionAttribute("currentUser") == null) {
            ctx.sessionAttribute("loginRedirect", ctx.path());
            ctx.redirect("/login");
        }
    };

}
