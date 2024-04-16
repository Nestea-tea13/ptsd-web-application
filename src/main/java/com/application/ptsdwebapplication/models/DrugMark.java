package com.application.ptsdwebapplication.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Mark")
public class DrugMark {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_drug_id", referencedColumnName = "id")
    private PersonDrug personDrug;

    @Column(name = "taking_num")
    private int takingNum;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;

    @Column(name = "mark_num_per_day")
    private int markNumPerDay;

    @Column(name = "mark")
    private String mark;

    public DrugMark() {}

    public DrugMark(PersonDrug personDrug, int takingNum, Date date, int markNumPerDay) {
        this.personDrug = personDrug;
        this.takingNum = takingNum;
        this.date = date;
        this.markNumPerDay = markNumPerDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonDrug getPersonDrug() {
        return personDrug;
    }

    public void setPersonDrug(PersonDrug personDrug) {
        this.personDrug = personDrug;
    }

    public int getTakingNum() {
        return takingNum;
    }

    public void setTakingNum(int takingNum) {
        this.takingNum = takingNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMarkNumPerDay() {
        return markNumPerDay;
    }

    public void setMarkNumPerDay(int markNumPerDay) {
        this.markNumPerDay = markNumPerDay;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
    
}
