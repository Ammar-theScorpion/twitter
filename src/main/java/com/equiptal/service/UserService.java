package com.equiptal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.equiptal.DTO.UserDTO;
import com.equiptal.model.DBConnection;
import com.equiptal.model.UserDAO;

import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    private UserDAO userDAO = DBConnection.jdbi.onDemand(UserDAO.class);

    public String createUser(String userName, String password) {
        if (findByUser(userName, password) != null) { // or try/catch; check if the user is already ...
            return userName + " is already used try:" + generateRecommendation(userName);
        }
        if (password.length() <= 3)
            return "password is short";
        userDAO.insert(userName, password);
        return "201";
    }

    public String login(String userName, String password) {
        if (findByUser(userName, password) != null) {
            return "/";
        }
        return "404";
    }

    public UserDTO getUserProfile(Integer userId) {
        return userDAO.getUserProfile(userId);
    }

    public Integer findIdByName(String name) {
        return userDAO.findIdByName(name);
    }

    public Handler search = ctx -> {
        String someoneName = ctx.formParam("name");
        Map<String, Object> model = new HashMap<>();
        List<String> names = userDAO.findMatchNames(someoneName);
        model.put("simNames", names);
        ctx.render("templates/search.peb", model);
    };

    private String findByUser(String userName, String pass) {
        return userDAO.findByUserName(userName, pass);
    }

    private String generateRecommendation(String foundUserName) {
        return foundUserName + "1";
    }

}