package com.equiptal.otherServices.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.equiptal.DTO.CommentDTO;

public class CommentDTOMapper implements RowMapper<CommentDTO> {

    @Override
    public CommentDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        CommentDTO dto = new CommentDTO(
                rs.getString("tweet"),
                rs.getDate("create_date"),
                rs.getString("user_name"));
        return dto;
    }

}