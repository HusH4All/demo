package com.example.demo.dao;

import com.example.demo.model.Stadistics;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class StadisticsRowMapper implements RowMapper<Stadistics> {
    public Stadistics mapRow(ResultSet rs, int rowNum) throws SQLException{
        Stadistics stadistics = new Stadistics();
        stadistics.setName(rs.getString("name"));
        stadistics.setStat(rs.getInt("stat"));
        return stadistics;
    }
}