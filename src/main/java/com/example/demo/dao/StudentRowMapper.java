package com.example.demo.dao;

import com.example.demo.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class StudentRowMapper implements RowMapper<Student> {
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException{
        Student student = new Student();
        student.setId_al(rs.getString("id_al"));
        student.setName(rs.getString("Name"));
        student.setPassword(rs.getString("Password"));
        student.setEmail(rs.getString("Email"));
        student.setDegree(rs.getString("Degree"));
        student.setCourse(rs.getInt("Course"));
        student.setHours(rs.getInt("Hours"));
        student.setSKP(rs.getBoolean("SKP"));
        student.setActive(rs.getBoolean("active"));
        student.setBanned(rs.getBoolean("banned"));
        student.setBanMsg(rs.getString("ban_message"));
        return student;
    }
}
