package com.example.demo.model;

import java.time.LocalDate;

public class Request {
    private String id_R;
    private String id_al;
    private String id_S;
    private String description;
    private java.time.LocalDate StartDate;
    private java.time.LocalDate EndDate;

    public String getId_R() { return id_R; }

    public void setId_R(String id_R) { this.id_R = id_R; }

    public String getId_al() { return id_al; }

    public void setId_al(String id_al) { this.id_al = id_al; }

    public String getId_S() { return id_S; }

    public void setId_S(String id_S) { this.id_S = id_S; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return StartDate; }

    public void setStartDate(LocalDate startDate) { StartDate = startDate; }

    public LocalDate getEndDate() { return EndDate; }

    public void setEndDate(LocalDate endDate) { EndDate = endDate; }
}
