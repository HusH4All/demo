package com.example.demo.dao;

import com.example.demo.model.Offer;
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

    public void addOffer(Offer offer, String id, Student student) {
        jdbcTemplate.update(
                "INSERT INTO Offer VALUES(?, ?, ?, ?, ?, ?)",
                id, student.getId_al(), offer.getId_S(), offer.getDescription(), offer.getStartDate(), offer.getEndDate()
        );
    }

    public Offer lastOffer(){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Offer order by 1 desc fetch first 1 row only",
                    new OfferRowMapper()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteOffer(Offer offer) {
        jdbcTemplate.update(
                "DELETE FROM Offer WHERE id_O = ?",
                offer.getId_O()
        );
    }
    public void deleteOffer(String id_O) {
        jdbcTemplate.update(
                "DELETE FROM Offer WHERE id_O = ?",
                id_O
        );
    }

    public void updateOffer(Offer offer) {
        jdbcTemplate.update(
                "UPDATE Offer SET id_al = ?, id_S = ?, description = ?, StartDate = ?, EndDate = ? WHERE id_O = ?",
                offer.getId_al(), offer.getId_S(), offer.getDescription(), offer.getStartDate(), offer.getEndDate(), offer.getId_O()
        );
    }

    public Offer getOffer(String id_O) {
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

    public List<Offer> getOffers(Student student) {
        if (student == null) {
            try {
                return jdbcTemplate.query("SELECT o.id_o, st.name as id_al, sk.name as id_s, o.description, o.startdate, o.enddate, sk.level as skilllevel FROM Offer as o JOIN student as st USING(id_al) JOIN skilltype as sk USING(id_s);",
                        new OfferRowMapper());
            }
            catch(EmptyResultDataAccessException e) {
                return new ArrayList<Offer>();
            }
        }
        else {
            try {
                return jdbcTemplate.query("SELECT o.id_o, st.name as id_al, sk.name as id_s, o.description, o.startdate, o.enddate, sk.level as skilllevel FROM Offer as o JOIN student as st USING(id_al) JOIN skilltype as sk USING(id_s) WHERE id_al != ?;",
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
            return jdbcTemplate.query("SELECT o.id_o, st.name as id_al, sk.name as id_s, o.description, o.startdate, o.enddate, sk.level as skilllevel FROM Offer as o JOIN student as st USING(id_al) JOIN skilltype as sk USING(id_s) WHERE id_al = ?;",
                    new OfferRowMapper(),
                    student.getId_al()
            );
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
        }
    }


    public SkillType getSkill(String id_S){
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
}
