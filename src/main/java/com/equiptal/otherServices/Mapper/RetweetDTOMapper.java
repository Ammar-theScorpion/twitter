package com.equiptal.otherServices.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.equiptal.DTO.RetweetDTO;

public class RetweetDTOMapper implements RowMapper<RetweetDTO> {

    @Override
    public RetweetDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        RetweetDTO dto = new RetweetDTO(rs.getInt("id"),
                rs.getString("user_name"),
                rs.getString("tweet"),
                rs.getDate("create_date"),
                rs.getArray("users"),
                rs.getInt("count"),

                rs.getInt("oid"),
                rs.getString("ouser_name"),
                rs.getDate("ocreateDate"),
                rs.getArray("ousers"),
                rs.getInt("ocount"));
        return dto;
    }

}
