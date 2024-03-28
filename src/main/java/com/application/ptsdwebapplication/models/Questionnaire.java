package com.application.ptsdwebapplication.models;


public class Questionnaire {
    
    private int id;

    private String name;

    private String period; // в чем измерять?

    // Список вопросов

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}
