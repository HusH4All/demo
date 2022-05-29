package com.example.demo.model;

public class SkillType {
    public int id_S;
    public String name;
    public String level;
    public String description;
    public boolean Active = true;

    public SkillType(){}

    public int getId_S() {
        return id_S;
    }

    public void setId_S(int id_S) {
        this.id_S = id_S;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {return Active;}

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

    public void setActive(boolean active) {this.Active = active;}

    public void setAll(SkillType skillType){
        this.name = skillType.getName();
        this.description = skillType.getDescription();
        this.level = skillType.getLevel();
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
