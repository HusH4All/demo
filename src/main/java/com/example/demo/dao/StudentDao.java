package com.example.demo.dao;

import com.example.demo.dao.StudentRowMapper;
import com.example.demo.model.Student;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addStudent(Student student) {
        jdbcTemplate.update(
                "INSERT INTO Student VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                student.getId_al(), student.getName(), student.getPassword(), student.getDegree(), student.getCourse(), student.getHours(), student.getSKP(), student.getActive()
        );
    }

    public void deleteStudent(Student student) {
        jdbcTemplate.update(
                "DELETE FROM Student WHERE id_al = ?",
                student.getId_al()
        );
    }
    public void deleteStudent(String id_al) {
        jdbcTemplate.update(
                "DELETE FROM Student WHERE id_al = ?",
                id_al
        );
    }

    public void updateStudent(Student student) {
        jdbcTemplate.update(
                "UPDATE Student SET name = ?, password = ?, degree = ?, course = ?, hours = ?, skp = ?, active =, WHERE id_al = ?",
                student.getName(), student.getPassword(), student.getDegree(), student.getCourse(), student.getHours(), student.getSKP(), student.getActive(), student.getId_al()
        );
    }

    public Student getStudent(String id_al) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Student WHERE id_al = ?",
                    new StudentRowMapper(),
                    id_al
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Student> getStudents() {
        try {
            return jdbcTemplate.query("SELECT * FROM Student",
                    new StudentRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Student>();
        }
    }

    public Student loadUserByUsername(String username, String password) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        Student student = getStudent(username.trim());
        if (student == null)
            return null;
        if (passwordEncryptor.checkPassword(password, student.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return student;
        }
        if (password.equals(student.getPassword()))
            return student;
        else return null;
    }
}
