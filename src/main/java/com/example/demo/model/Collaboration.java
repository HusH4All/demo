package com.example.demo.model;

import java.time.LocalDate;

public class Collaboration {
    private String id_C;
    private String id_R;
    private String id_O;
    private java.time.LocalDate StartDate;
    private java.time.LocalDate EndDate;
    private int score;

    public Collaboration() {}

    public String getId_C() {return id_C;}

    public void setId_C(String id_C) {this.id_C = id_C;}

    public String getId_R() {return id_R;}

    public void setId_R(String id_R) {this.id_R = id_R;}

    public String getId_O() {return id_O;}

    public void setId_O(String id_O) {this.id_O = id_O;}

    public LocalDate getStartDate() {return StartDate;}

    public void setStartDate(LocalDate startDate) {StartDate = startDate;}

    public LocalDate getEndDate() {return EndDate;}

    public void setEndDate(LocalDate endDate) {EndDate = endDate;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    @Override
    public String toString() {
        return "Collaboration{" +
                "id_C='" + id_C + '\'' +
                ", id_R='" + id_R + '\'' +
                ", id_O='" + id_O + '\'' +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", score=" + score +
                '}';

    }
}
