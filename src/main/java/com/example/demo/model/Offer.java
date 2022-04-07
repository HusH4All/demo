package com.example.demo.model;

import java.time.LocalDate;

public class Offer {
    public String id_O;
    public String id_al;
    public String id_S;
    public String description;
    public java.time.LocalDate StartDate;
    public java.time.LocalDate EndDate;

    public Offer(){}

    public String getId_O() {return id_O;}

    public String getId_al() {return id_al;}

    public String getId_S() {return id_S;}

    public String getDescription() {return description;}

    public LocalDate getStartDate() {return StartDate;}

    public LocalDate getEndDate() {return EndDate;}

    public void setId_O(String id_O) {this.id_O = id_O;}

    public void setId_al(String id_al) {this.id_al = id_al;}

    public void setId_S(String id_S) {this.id_S = id_S;}

    public void setDescription(String description) {this.description = description;}

    public void setStartDate(LocalDate startDate) {StartDate = startDate;}

    public void setEndDate(LocalDate endDate) {EndDate = endDate;}

    @Override
    public String toString() {
        return "Offer{" +
                "id_O='" + id_O + '\'' +
                ", id_al='" + id_al + '\'' +
                ", id_S='" + id_S + '\'' +
                ", description='" + description + '\'' +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                '}';
    }
}
