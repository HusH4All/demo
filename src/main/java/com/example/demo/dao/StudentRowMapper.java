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
        student.setDegree(rs.getString("Degree"));
        student.setCourse(rs.getInt("Course"));
        student.setHours(rs.getInt("Hours"));
        student.setSKP(rs.getInt("SKP"));
        return student;
    }
}
