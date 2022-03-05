package com.example.demo.dao;

import com.example.demo.model.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class RequestRowMapper implements RowMapper<Request> {
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException{
        Request request = new Request();
        request.setId_R(rs.getString("id_R"));
        request.setId_al(rs.getString("id_al"));
        request.setId_S(rs.getString("id_S"));
        request.setDescription(rs.getString("Description"));
        request.setStartDate(rs.getObject("StartDate", LocalDate.class));
        request.setEndDate(rs.getObject("EndDate", LocalDate.class));
        return request;
    }
}
