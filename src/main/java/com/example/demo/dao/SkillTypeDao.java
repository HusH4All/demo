package com.example.demo.dao;

import com.example.demo.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkillTypeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSkillType(SkillType skill) {
        jdbcTemplate.update(
                "INSERT INTO SkillType(name, level, description, active) VALUES(?, ?, ?, ?)",
                skill.getName(), skill.getLevel(), skill.getDescription(), skill.getActive()
        );
    }

    public void deleteSkillType(SkillType skill) {
        jdbcTemplate.update(
                "DELETE FROM SkillType WHERE id_S = ?",
                skill.getId_S()
        );
    }
    public void deleteSkilType(String id_S) {
        jdbcTemplate.update(
                "DELETE FROM SkillType WHERE id_S = ?",
                id_S
        );
    }

    public void updateSkillType(SkillType skill) {
        jdbcTemplate.update(
                "UPDATE SkillTypeSET name = ?, description = ?, level = ?",
                skill.getName(), skill.getDescription(), skill.getLevel()
        );
    }

    public SkillType getSkillType(int id_S) {
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
            return jdbcTemplate.query("SELECT distinct * FROM SkillType",
                    new SkillTypeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<SkillType>();
        }
    }
}
