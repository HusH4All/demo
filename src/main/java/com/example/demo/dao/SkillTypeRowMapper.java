package com.example.demo.dao;

import com.example.demo.model.SkillType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SkillTypeRowMapper implements RowMapper<SkillType> {
    public SkillType mapRow(ResultSet rs, int rowNum) throws SQLException{
        SkillType skill = new SkillType();
        skill.setId_S(rs.getString("id_s"));
        skill.setName(rs.getString("name"));
        skill.setLevel(rs.getString("level"));
        skill.setDescription(rs.getString("Description"));
        skill.setActive(rs.getBoolean("active"));
        return skill;
    }
}