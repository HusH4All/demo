package com.example.demo.dao;

import com.example.demo.model.SkillType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class SkillTypeRowMapper implements RowMapper<SkillType> {
    public SkillType mapRow(ResultSet rs, int rowNum) throws SQLException{
        SkillType skill = new SkillType();
        skill.setId_S(rs.getString("id_S"));
        skill.setName(rs.getString("name"));
        skill.setLevel(rs.getString("level"));
        skill.setDescription(rs.getString("Description"));
        return skill;
    }
}