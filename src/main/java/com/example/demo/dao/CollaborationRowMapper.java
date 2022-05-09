package com.example.demo.dao;

import com.example.demo.model.Collaboration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CollaborationRowMapper implements RowMapper<Collaboration> {
    public Collaboration mapRow(ResultSet rs, int rowNum) throws SQLException{
        Collaboration collaboration = new Collaboration();
        collaboration.setId_C(rs.getInt("id_C"));
        collaboration.setId_R(rs.getInt("id_R"));
        collaboration.setId_O(rs.getInt("id_O"));
        collaboration.setStartDate(rs.getObject("StartDate", LocalDate.class));
        collaboration.setEndDate(rs.getObject("EndDate", LocalDate.class));
        collaboration.setScore(rs.getInt("score"));
        return collaboration;
    }
}
