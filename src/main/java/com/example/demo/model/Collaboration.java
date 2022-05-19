package com.example.demo.model;

import java.time.LocalDate;

public class Collaboration {
    public int id_C;
    public int id_R;
    public int id_O;
    public java.time.LocalDate StartDate;
    public java.time.LocalDate EndDate;
    public Boolean state = true;
    public int score;
    public Boolean pending = true;

    public Collaboration() {}

    public Collaboration(int id_R, int id_O, LocalDate StartDate, LocalDate EndDate, Boolean pending){
        this.id_R = id_R;
        this.id_O = id_O;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.pending = pending;
    }

    public int getId_C() {return id_C;}

    public void setId_C(int id_C) {this.id_C = id_C;}

    public int getId_R() {return id_R;}

    public void setId_R(int id_R) {this.id_R = id_R;}

    public int getId_O() {return id_O;}

    public void setId_O(int id_O) {this.id_O = id_O;}

    public LocalDate getStartDate() {return StartDate;}

    public void setStartDate(LocalDate startDate) {StartDate = startDate;}

    public LocalDate getEndDate() {return EndDate;}

    public void setEndDate(LocalDate endDate) {EndDate = endDate;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public Boolean getState() {return state;}

    public void setState(Boolean state) {this.state = state;}

    public Boolean getPending() {return pending;}

    public void setPending(Boolean pending) {this.pending = pending;}

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
