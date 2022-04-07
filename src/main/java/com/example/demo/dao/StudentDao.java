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
                "INSERT INTO Student VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                student.getId_al(), student.getName(), student.getPassword(), student.getEmail(), student.getDegree(), student.getCourse(), student.getHours(), student.getSKP(), student.getActive(), student.getBanned()
        );
    }

    public void disableStudent(String id_al) {
        jdbcTemplate.update(
                "UPDATE Student SET active = ? WHERE id_al = ?",
                false, id_al
        );
    }

    public void enableStudent(String id_al) {
        jdbcTemplate.update(
                "UPDATE Student SET active = ? WHERE id_al = ?",
                true, id_al
        );
    }

    public void banStudent(String id_al) {
        jdbcTemplate.update(
                "UPDATE Student SET banned = ? WHERE id_al = ?",
                true, id_al
        );
    }

    public void unBanStudent(String id_al) {
        jdbcTemplate.update(
                "UPDATE Student SET banned = ? WHERE id_al = ?",
                false, id_al
        );
    }

    public void updateStudent(Student student) {
        jdbcTemplate.update(
                "UPDATE Student SET name = ?, degree = ?, course = ? WHERE id_al = ?",
                student.getName(), student.getDegree(), student.getCourse(), student.getId_al()
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

    public List<Student> getBanneableStudents() {
        try {
            return jdbcTemplate.query("SELECT * FROM Student WHERE SKP = 'false'",
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
