package com.example.demo.model;

public class StudentsColaborating {
    private Student teacher;
    private Student student;
    private SkillType skillType;
    private int score;
    private int hours;

    public StudentsColaborating() {
    }

    public StudentsColaborating(Student teacher, Student student, SkillType skillType) {
        this.teacher = teacher;
        this.student = student;
        this.skillType = skillType;
    }

    public Student getTeacher() {
        return teacher;
    }

    public void setTeacher(Student teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
