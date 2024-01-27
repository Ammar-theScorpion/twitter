package com.equiptal.service;

import com.equiptal.DTO.UserDTO;
import com.equiptal.model.UserDAO;
import com.google.inject.Inject;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserService {

    private UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String createUser(String userName, String password) {
        if (findIdByName(userName) != null) { // or try/catch; check if the user is already ...
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

    private String findByUser(String userName, String pass) {
        return userDAO.findByUserName(userName, pass);
    }

    private String generateRecommendation(String foundUserName) {
        // generate a unique name
        int counter = 1;
        String recommendedName = foundUserName;

        while (findIdByName(recommendedName) != null) {
            counter++;
            recommendedName = foundUserName + counter;
        }
        return recommendedName;
    }

}