package com.example.demo.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Offer {
    public int id_O;
    public String id_al;
    public int id_S;
    public String description = "";
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    public LocalDate StartDate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    public LocalDate EndDate;
    public boolean Active = true;

    public Offer(){}

    public Offer(String id_al, int id_S, LocalDate StartDate, LocalDate EndDate, boolean active){
        this.id_al = id_al;
        this.id_S = id_S;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Active = active;
    }

    public int getId_O() {return id_O;}

    public String getId_al() {return id_al;}

    public int getId_S() {return id_S;}

    public String getDescription() {return description;}

    public LocalDate getStartDate() {return StartDate;}

    public LocalDate getEndDate() {return EndDate;}

    public boolean getActive() {return Active;}

    public void setId_O(int id_O) {this.id_O = id_O;}

    public void setId_al(String id_al) {this.id_al = id_al;}

    public void setId_S(int id_S) {this.id_S = id_S;}

    public void setDescription(String description) {this.description = description;}

    public void setStartDate(LocalDate startDate) {this.StartDate = startDate;}

    public void setEndDate(LocalDate endDate) {this.EndDate = endDate;}

    public void setActive(boolean active) {this.Active = active;}

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
