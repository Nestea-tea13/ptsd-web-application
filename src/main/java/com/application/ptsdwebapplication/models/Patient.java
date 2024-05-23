package com.application.ptsdwebapplication.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.application.ptsdwebapplication.models.questionnaireResults.*;

@Entity
@Table(name = "Patient")
public class Patient {

    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<PersonDrug> drugs;

    @OneToMany(mappedBy = "patient")
    private List<CAPSResults> resultsCAPS;

    @OneToMany(mappedBy = "patient")
    private List<IESRResults> resultsIESR;

    @OneToMany(mappedBy = "patient")
    private List<BHSResults> resultsBHS;

    @OneToMany(mappedBy = "patient")
    private List<TOP8Results> resultsTOP8;

    public Patient() {}

    public Patient(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PersonDrug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<PersonDrug> drugs) {
        this.drugs = drugs;
    }

    public List<CAPSResults> getResultsCAPS() {
        return resultsCAPS;
    }

    public void setResultsCAPS(List<CAPSResults> resultsCAPS) {
        this.resultsCAPS = resultsCAPS;
    }

    public List<IESRResults> getResultsIESR() {
        return resultsIESR;
    }

    public void setResultsIESR(List<IESRResults> resultsIESR) {
        this.resultsIESR = resultsIESR;
    }

    public List<BHSResults> getResultsBHS() {
        return resultsBHS;
    }

    public void setResultsBHS(List<BHSResults> resultsBHS) {
        this.resultsBHS = resultsBHS;
    }
    
    public List<TOP8Results> getResultsTOP8() {
        return resultsTOP8;
    }

    public void setResultsTOP8(List<TOP8Results> resultsTOP8) {
        this.resultsTOP8 = resultsTOP8;
    }

}
