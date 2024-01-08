package com.equiptal.otherServices.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.equiptal.DTO.UserDTO;

public class UserDTOMapper implements RowMapper<UserDTO> {

    @Override
    public UserDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        UserDTO dto = new UserDTO(
                rs.getString("user_name"),
                rs.getInt("count"));
        return dto;
    }

}
