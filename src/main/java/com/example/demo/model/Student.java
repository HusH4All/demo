package com.example.demo.model;

public class Student {
    private String id_al;
    private String name;
    private String password;
    private String email;
    private String degree;
    private Integer course;
    private Integer hours = 0;
    private Boolean SKP = false;
    private Boolean Active = true;

    public String getId_al() { return id_al; }

    public void setId_al(String id_al) {
        this.id_al = id_al;
        String e = id_al+"@uji.es";
        this.email = e.replace(" ","");
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getDegree() { return degree; }

    public void setDegree(String degree) { this.degree = degree; }

    public Integer getCourse() { return course; }

    public void setCourse(Integer course) { this.course = course; }

    public Integer getHours() { return hours; }

    public void setHours(Integer hours) { this.hours = hours; }

    public Boolean getSKP() { return SKP; }

    public void setSKP(Boolean SKP) { this.SKP = SKP; }

    public Boolean getActive() { return Active; }

    public void setActive(Boolean active) { Active = active; }

    public String getEmail() {return email;}
}
