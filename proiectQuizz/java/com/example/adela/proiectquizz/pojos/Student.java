package com.example.adela.proiectquizz.pojos;

import java.util.List;

public class Student {
    private String name;
    private boolean type;
    private int points;
    private int gruop;
    private List<String> listTests;

    public Student(String name, boolean type, int points, int gruop, List<String> listTests) {
        this.name = name;
        this.type = type;
        this.points = points;
        this.gruop = gruop;
        this.listTests = listTests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGruop() {
        return gruop;
    }

    public void setGruop(int gruop) {
        this.gruop = gruop;
    }

    public List<String> getListTests() {
        return listTests;
    }

    public void setListTests(List<String> listTests) {
        this.listTests = listTests;
    }
}
