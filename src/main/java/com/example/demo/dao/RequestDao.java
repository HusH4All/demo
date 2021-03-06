package com.example.demo.dao;

import com.example.demo.dao.RequestRowMapper;
import com.example.demo.model.Offer;
import com.example.demo.model.Request;
import com.example.demo.model.SkillType;
import com.example.demo.model.Student;
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

    public void addRequest(Request request, Student student) {
        jdbcTemplate.update(
                "INSERT INTO Request(id_al, id_S, description, startdate, enddate, active) VALUES(?, ?, ?, ?, ?, ?)",
                student.getId_al(), request.getId_S(), request.getDescription(), request.getStartDate(), request.getEndDate(), request.getActive()
        );
    }

    public void disableRequest(int id_R) {
        jdbcTemplate.update(
                "UPDATE Request SET active = false WHERE id_R = ?",
                id_R
        );
    }

    public void disableMyRequests(String id_al) {
        jdbcTemplate.update(
                "UPDATE Request SET active = false WHERE id_al = ?",
                id_al
        );
    }

    public void disableWithSkill(int id_s) {
        jdbcTemplate.update(
                "UPDATE Request SET active = false WHERE id_s = ?",
                id_s
        );
    }

    public void deleteRequest(int id_R) {
        jdbcTemplate.update(
                "DELETE FROM Request WHERE id_R = ?",
                id_R
        );
    }

    public void updateRequest(Request request) {
        jdbcTemplate.update(
                "UPDATE Request SET id_S = ?, description = ?, StartDate = ?, EndDate = ? WHERE id_R = ?",
                request.getId_S(), request.getDescription(), request.getStartDate(), request.getEndDate(), request.getId_R()
        );
    }

    public Request getRequest(int id_R) {
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

    public List<Request> getSimilarRequests(int id_S, Student student) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Request WHERE id_S = ? and active = true and id_al != ?",
                    new RequestRowMapper(),
                    id_S, student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getPendingRequests(Student student){
        try {
            return jdbcTemplate.query(
                    "Select r.* from request as r join collaboration as c using(id_r) join offer as o using(id_o) where o.id_al = ? and c.pending = true and c.requesting != r.id_r",
                    new RequestRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    public List<Request> getDisabledRequests(String id_al) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Request WHERE id_al = ? and active = false",
                    new RequestRowMapper(),
                    id_al
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getDisabledRequests() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Request WHERE active = false",
                    new RequestRowMapper()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getRequests(Student student) {
        if (student == null) {
            try {
                return jdbcTemplate.query("SELECT * FROM Request;",
                        new RequestRowMapper());
            }
            catch(EmptyResultDataAccessException e) {
                return new ArrayList<Request>();
            }
        }
        else {
            try {
                return jdbcTemplate.query("SELECT * FROM Request WHERE id_al != ?;",
                        new RequestRowMapper(),
                        student.getId_al()
                );
            } catch (EmptyResultDataAccessException e) {
                return new ArrayList<Request>();
            }
        }
    }

    public List<Request> getMyRequests(Student student) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE id_al = ? and active = true;",
                    new RequestRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    public SkillType getSkill(int id_S){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM SkillType WHERE id_S = ?",
                    new SkillTypeRowMapper(),
                    id_S
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<SkillType> getSkillTypes() {
        try {
            return jdbcTemplate.query("SELECT * FROM SkillType",
                    new SkillTypeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<SkillType>();
        }
    }

    public Request getLastRequest(){
        return jdbcTemplate.queryForObject(
                "select * from request order by id_r desc fetch first 1 rows only",
                new RequestRowMapper()
        );
    }

}