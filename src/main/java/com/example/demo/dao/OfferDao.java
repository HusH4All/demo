package com.example.demo.dao;

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
public class OfferDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOffer(Offer offer, Student student) {
        jdbcTemplate.update(
                "INSERT INTO Offer(id_al, id_S, description, startdate, enddate, active) VALUES(?, ?, ?, ?, ?, ?)",
                student.getId_al(), offer.getId_S(), offer.getDescription(), offer.getStartDate(), offer.getEndDate(), offer.getActive()
        );
    }


    public void disableOffer(Offer offer) {
        jdbcTemplate.update(
                "UPDATE Offer SET active = false WHERE id_O = ?",
                offer.getId_O()
        );
    }
    public void deleteOffer(int id_O) {
        jdbcTemplate.update(
                "DELETE FROM Offer WHERE id_O = ?",
                id_O
        );
    }

    public void updateOffer(Offer offer) {
        jdbcTemplate.update(
                "UPDATE Offer SET id_S = ?, description = ?, StartDate = ?, EndDate = ? WHERE id_O = ?",
                offer.getId_S(), offer.getDescription(), offer.getStartDate(), offer.getEndDate(), offer.getId_O()
        );
    }

    public Offer getOffer(int id_O) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Offer WHERE id_O = ?",
                    new OfferRowMapper(),
                    id_O
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Offer> getSimilarOffers(int id_S) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Offer WHERE id_S = ?",
                    new OfferRowMapper(),
                    id_S
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Offer> getDisabledOffers(String id_al) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Offer WHERE id_al = ? and active = false",
                    new OfferRowMapper(),
                    id_al
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Offer> getDisabledOffers() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Offer WHERE active = false",
                    new OfferRowMapper()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Offer> getOffers(Student student) {
        if (student == null) {
            try {
                return jdbcTemplate.query("SELECT * FROM Offer;",
                        new OfferRowMapper());
            }
            catch(EmptyResultDataAccessException e) {
                return new ArrayList<Offer>();
            }
        }
        else {
            try {
                return jdbcTemplate.query("SELECT * FROM Offer WHERE id_al != ?;",
                        new OfferRowMapper(),
                        student.getId_al()
                );
            } catch (EmptyResultDataAccessException e) {
                return new ArrayList<Offer>();
            }
        }
    }

    public List<Offer> getMyOffers(Student student) {
        try {
            return jdbcTemplate.query("SELECT * FROM Offer WHERE id_al = ?;",
                    new OfferRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
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

    public Offer getLastOffer(){
        return jdbcTemplate.queryForObject(
                "select * from offer order by id_o desc fetch first 1 rows only",
                new OfferRowMapper()
        );
    }
}
