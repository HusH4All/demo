package com.example.demo.dao;

import com.example.demo.dao.RequestRowMapper;
import com.example.demo.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequest(Request request) {
        jdbcTemplate.update(
                "INSERT INTO Request VALUES(?, ?, ?, ?, ?, ?)",
                request.getId_R(), request.getId_al(), request.getId_S(), request.getDescription(), request.getStartDate(), request.getEndDate()
        );
    }

    public void deleteRequest(Request request) {
        jdbcTemplate.update(
                "DELETE FROM Request WHERE id_R = ?",
                request.getId_R()
        );
    }
    public void deleteRequest(String id_R) {
        jdbcTemplate.update(
                "DELETE FROM Request WHERE id_R = ?",
                id_R
        );
    }

    public void updateRequest(Request request) {
        jdbcTemplate.update(
                "UPDATE Request SET id_al = ?, id_S = ?, description = ?, StartDate = ?, EndDate = ? WHERE id_R = ?",
                request.getId_al(), request.getId_S(), request.getDescription(), request.getStartDate(), request.getEndDate(), request.getId_R()
        );
    }

    public Request getRequest(String id_R) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Request WHERE id_R = ?",
                    new RequestRowMapper(),
                    id_R
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getRequests() {
        try {
            return jdbcTemplate.query("SELECT * FROM Request",
                    new RequestRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
}