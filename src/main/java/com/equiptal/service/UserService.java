package com.equiptal.service;

import com.equiptal.DTO.UserDTO;
import com.equiptal.model.DBConnection;
import com.equiptal.model.UserDAO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    public String createUser(String userName, String password) {
        if (findByUser(userName) != null) { // or try/catch; check if the user is already ...
            return userName + " is already used try:" + generateRecommendation(userName);
        }
        userDAO.insert(userName, password);
        return "201";
    }

    public String login(String userName, String password) {
        if (findByUser(userName) != null) {
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

    private String findByUser(String userName) {
        return userDAO.findByUserName(userName);
    }

    private String generateRecommendation(String foundUserName) {
        return foundUserName + "1";
    }

    private UserDAO userDAO = DBConnection.jdbi.onDemand(UserDAO.class);
}