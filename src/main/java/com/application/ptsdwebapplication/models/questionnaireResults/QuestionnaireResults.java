package com.application.ptsdwebapplication.models.questionnaireResults;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.application.ptsdwebapplication.models.Person;

@MappedSuperclass
public class QuestionnaireResults {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    protected Person user;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    protected Date date;

    public QuestionnaireResults() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
    
    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
