package com.example.demo.model;

public class SkillType {
    private String id_S;
    private String name;
    private String level;
    private String description;

    public SkillType(){}

    public String getId_S() {
        return id_S;
    }

    public void setId_S(String id_S) {
        this.id_S = id_S;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SkillType{" +
                "id_S='" + id_S + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
