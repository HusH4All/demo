package com.example.demo.dao;

import com.example.demo.model.Collaboration;
import com.example.demo.model.Offer;
import com.example.demo.model.Request;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollaborationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCollaboration(Collaboration collaboration) {
        jdbcTemplate.update(
                "INSERT INTO Collaboration(id_R, id_O, StartDate, EndDate, state, pending, requesting) VALUES(?, ?, ?, ?, true, ?, ?)",
                collaboration.getId_R(), collaboration.getId_O(), collaboration.getStartDate(), collaboration.getEndDate(), collaboration.getPending(), collaboration.getRequesting()
        );
    }

    public void acceptCollaboration(int id_C) {
        jdbcTemplate.update(
                "UPDATE Collaboration SET pending = false WHERE id_C = ?",
                id_C
        );
    }
    public void deleteCollaboration(int id_C) {
        jdbcTemplate.update(
                "DELETE FROM Collaboration WHERE id_C = ?",
                id_C
        );
    }

    public void updateCollaboration(Collaboration collaboration) {
        jdbcTemplate.update(
                "UPDATE Collaboration SET id_R = ?, id_O = ?, StartDate = ?, EndDate = ?, score = ?, state = ?, pending = ?, requesting = ? WHERE id_C = ?",
                collaboration.getId_R(), collaboration.getId_O(), collaboration.getStartDate(), collaboration.getEndDate(), collaboration.getScore(), collaboration.getState(),collaboration.getPending(), collaboration.getRequesting(), collaboration.getId_C()
                );
    }

    public Collaboration getCollaboration(int id_C) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Collaboration WHERE id_C = ?",
                    new CollaborationRowMapper(),
                    id_C
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Collaboration> getCollaborations() {
        try {
            return jdbcTemplate.query("SELECT * FROM Collaboration",
                    new CollaborationRowMapper()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public List<Collaboration> getPendingCollaborationFromOffer(Student student) {
        try {
            return jdbcTemplate.query("SELECT c.* FROM Collaboration as c join offer as o using(id_o) join request as r on c.id_r = r.id_r join student as s on r.id_al = s.id_al WHERE o.id_al = ? and s.banned = false and c.pending = true and requesting = o.id_o",
                    new CollaborationRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public List<Collaboration> getPendingCollaborationFromRequest(Student student) {
        try {
            return jdbcTemplate.query("SELECT c.* FROM Collaboration as c join request as r using(id_r) join offer as o on c.id_o = o.id_o join student as s on o.id_al = s.id_al WHERE r.id_al = ? and s.banned = false and c.pending = true and requesting = r.id_r",
                    new CollaborationRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public List<Collaboration> getManagmentCollaborationFromOffer(Student student) {
        try {
            return jdbcTemplate.query("SELECT c.* FROM Collaboration as c join offer as o using(id_o) join request as r on c.id_r = r.id_r join student as s on r.id_al = s.id_al WHERE o.id_al = ? and s.banned = false and c.pending = true and requesting != o.id_o",
                    new CollaborationRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public List<Collaboration> getManagmentCollaborationFromRequest(Student student) {
        try {
            return jdbcTemplate.query("SELECT c.* FROM Collaboration as c join request as r using(id_r) join offer as o on c.id_o = o.id_o join student as s on o.id_al = s.id_al WHERE r.id_al = ? and s.banned = false and c.pending = true and requesting != r.id_r",
                    new CollaborationRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public List<Collaboration> getCollaborationFromOffer(Student student) {
        try {
            return jdbcTemplate.query("SELECT c.* FROM Collaboration as c join offer as o using(id_o) WHERE o.id_al = ? and c.pending = false and requesting != o.id_o",
                    new CollaborationRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }

    public List<Collaboration> getCollaborationFromRequest(Student student) {
        try {
            return jdbcTemplate.query("SELECT c.* FROM Collaboration as c join request as r using(id_r) WHERE r.id_al = ? and c.pending = false and requesting != r.id_r",
                    new CollaborationRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
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
}
