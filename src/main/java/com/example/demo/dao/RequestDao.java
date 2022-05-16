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

    public void deleteRequest(Request request) {
        jdbcTemplate.update(
                "DELETE FROM Request WHERE id_R = ?",
                request.getId_R()
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
                "UPDATE Request SET id_al = ?, id_S = ?, description = ?, StartDate = ?, EndDate = ? WHERE id_R = ?",
                request.getId_al(), request.getId_S(), request.getDescription(), request.getStartDate(), request.getEndDate(), request.getId_R()
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

    public List<Request> getRequests(Student student) {
        if (student == null) {
            try {
                return jdbcTemplate.query("SELECT r.id_r, st.name as id_al, sk.name as id_s, r.description, r.startdate, r.enddate, sk.level as skilllevel FROM Request as r JOIN student as st USING(id_al) JOIN skilltype as sk USING(id_s);",
                        new RequestRowMapper());
            }
            catch(EmptyResultDataAccessException e) {
                return new ArrayList<Request>();
            }
        }
        else {
            try {
                return jdbcTemplate.query("SELECT r.id_r, st.name as id_al, sk.name as id_s, r.description, r.startdate, r.enddate, sk.level as skilllevel FROM Request as r JOIN student as st USING(id_al) JOIN skilltype as sk USING(id_s) WHERE id_al != ?;",
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
            return jdbcTemplate.query("SELECT r.id_r, st.name as id_al, sk.name as id_s, r.description, r.startdate, r.enddate, sk.level as skilllevel FROM Request as r JOIN student as st USING(id_al) JOIN skilltype as sk USING(id_s) WHERE id_al = ?;",
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
}