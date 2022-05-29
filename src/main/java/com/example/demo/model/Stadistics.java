package com.example.demo.model;

public class Stadistics {
    public String name;
    public float stat = 0;
    public String label;
    public String category;

    public Stadistics(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStat() {
        return stat;
    }

    public void setStat(float stat) {
        this.stat = stat;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
