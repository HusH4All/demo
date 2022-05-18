package com.example.demo.model;

public class Student {
    public String id_al;
    public String name;
    public String password;
    public String email;
    public String degree;
    public Integer course;
    public Integer hours = 0;
    public Boolean SKP = false;
    public Boolean active = true;
    public Boolean banned = false;
    public String banMsg;

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

    public String getEmail() {return email;}

    public void setEmail(String email) { this.email = email;}

    public String getDegree() { return degree; }

    public void setDegree(String degree) { this.degree = degree; }

    public Integer getCourse() { return course; }

    public void setCourse(Integer course) { this.course = course; }

    public Integer getHours() { return hours; }

    public void setHours(Integer hours) { this.hours = hours; }

    public Boolean getSKP() { return SKP; }

    public void setSKP(Boolean SKP) { this.SKP = SKP; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public Boolean getBanned() { return banned; }

    public void setBanned(Boolean banned){ this.banned=banned; }

    public String getBanMsg() {return banMsg;}

    public void setBanMsg(String banMsg) {this.banMsg = banMsg;}

    public void setAll(String id_al, String password, String email, Integer hours, Boolean SKP, Boolean active, Boolean banned){
        this.id_al = id_al;
        this.password = password;
        this.email = email;
        this.hours = hours;
        this.SKP = SKP;
        this.active = active;
        this.banned = banned;
    }
}
