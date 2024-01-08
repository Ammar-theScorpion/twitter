package com.equiptal.model;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.equiptal.DTO.UserDTO;
import com.equiptal.otherServices.Mapper.UserDTOMapper;

public interface UserDAO {
    @SqlUpdate("INSERT INTO \"User\" (user_name, user_pass) VALUES (?, ?)")
    void insert(String userName, String password);

    @SqlQuery("SELECT u.user_name, COUNT(l.like_id) as count FROM \"like\" l JOIN \"User\" u ON u.id = l.user_id  WHERE l.user_id=:userId GROUP BY u.user_name")
    @RegisterRowMapper(UserDTOMapper.class)
    UserDTO getUserProfile(@Bind("userId") Integer userId);

    @SqlQuery("SELECT user_name FROM \"User\" u WHERE u.user_name=:userName")
    String findByUserName(@Bind("userName") String userName);

    @SqlQuery("SELECT id FROM \"User\" u WHERE u.user_name=:userName")
    Integer findIdByName(@Bind("userName") String userName);
}