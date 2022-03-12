package com.example.demo.dao;

import com.example.demo.model.Collaboration;
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
                "INSERT INTO Collaboration VALUES(?, ?, ?, ?, ?, ?)",
                collaboration.getId_C(), collaboration.getId_R(), collaboration.getId_O(), collaboration.getStartDate(), collaboration.getEndDate(), collaboration.getScore()
        );
    }

    public void deleteCollaboration(Collaboration collaboration) {
        jdbcTemplate.update(
                "DELETE FROM Collaboration WHERE id_C = ?",
                collaboration.getId_C()
        );
    }
    public void deleteCollaboration(String id_C) {
        jdbcTemplate.update(
                "DELETE FROM Collaboration WHERE id_C = ?",
                id_C
        );
    }

    public void updateCollaboration(Collaboration collaboration) {
        jdbcTemplate.update(
                "UPDATE Collaboration SET id_C = ?, id_R = ?, id_O = ?, StartDate = ?, EndDate = ? WHERE score = ?",
                collaboration.getId_C(), collaboration.getId_R(), collaboration.getId_O(), collaboration.getStartDate(), collaboration.getEndDate(), collaboration.getScore()
        );
    }

    public Collaboration getCollaboration(String id_C) {
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
                    new CollaborationRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
}
