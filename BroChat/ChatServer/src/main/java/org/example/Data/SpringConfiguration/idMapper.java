package org.example.Data.SpringConfiguration;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class idMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        if(rs.getRow() > 0){
            return Integer.valueOf(rs.getInt(1));
        }
        return null;
    }
}