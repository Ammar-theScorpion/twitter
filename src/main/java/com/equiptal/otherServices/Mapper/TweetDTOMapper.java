package com.equiptal.otherServices.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.equiptal.DTO.TweetDTO;

public class TweetDTOMapper implements RowMapper<TweetDTO> {

    @Override
    public TweetDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        TweetDTO dto = new TweetDTO(rs.getInt("id"),
                rs.getString("user_name"),
                rs.getString("tweet"),
                rs.getDate("create_date"),
                rs.getArray("users"),
                rs.getInt("count"));
        return dto;
    }

}
