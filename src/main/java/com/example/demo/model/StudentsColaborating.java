package com.example.demo.model;

public class StudentsColaborating {
    private Student teacher;
    private Student student;
    private SkillType skillType;

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
}
