package com.example.demo.dao;

import com.example.demo.model.Offer;
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

    public void addOffer(Offer offer) {
        jdbcTemplate.update(
                "INSERT INTO Offer VALUES(?, ?, ?, ?, ?, ?)",
                offer.getId_O(), offer.getId_al(), offer.getId_S(), offer.getDescription(), offer.getStartDate(), offer.getEndDate()
        );
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

    public List<Offer> getOffers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Offer",
                    new OfferRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Offer>();
        }
    }
}
