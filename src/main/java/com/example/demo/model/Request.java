package com.example.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Request {
    public int id_R;
    public String id_al;
    public int id_S;
    public String description = "";
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    public java.time.LocalDate StartDate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    public java.time.LocalDate EndDate;
    public boolean Active = true;

    public Request(){}

    public Request(String id_al, int id_S, LocalDate StartDate, LocalDate EndDate, boolean active){
        this.id_al = id_al;
        this.id_S = id_S;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Active = active;
    }

    public int getId_R() { return id_R; }

    public void setId_R(int id_R) { this.id_R = id_R; }

    public String getId_al() { return id_al; }

    public void setId_al(String id_al) { this.id_al = id_al; }

    public int getId_S() { return id_S; }

    public boolean getActive() {return Active;}

    public void setId_S(int id_S) { this.id_S = id_S; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return StartDate; }

    public void setStartDate(LocalDate startDate) { StartDate = startDate; }

    public LocalDate getEndDate() { return EndDate; }

    public void setEndDate(LocalDate endDate) { EndDate = endDate; }

    public void setActive(boolean active) {this.Active = active;}
}
