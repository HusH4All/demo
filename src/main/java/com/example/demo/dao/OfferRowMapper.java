package com.example.demo.dao;

import com.example.demo.enumerations.SkillLevel;
import com.example.demo.model.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class OfferRowMapper implements RowMapper<Offer> {
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException{
        Offer offer = new Offer();
        offer.setId_O(rs.getString("id_o"));
        offer.setId_al(rs.getString("id_al"));
        offer.setId_S(rs.getString("id_s"));
        offer.setDescription(rs.getString("Description"));
        offer.setStartDate(rs.getObject("StartDate", LocalDate.class));
        offer.setEndDate(rs.getObject("EndDate", LocalDate.class));
        return offer;
    }
}
