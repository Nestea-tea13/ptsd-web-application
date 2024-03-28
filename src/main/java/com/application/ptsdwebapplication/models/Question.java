package com.application.ptsdwebapplication.models;


public class Question {
    
    private int id;

    private int questionnaireId;

    // Список вариантов ответов

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

}
