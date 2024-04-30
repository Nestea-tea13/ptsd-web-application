package com.application.ptsdwebapplication.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Drug")
public class Drug {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "drug", fetch = FetchType.EAGER)
    private List<PersonDrug> persons;

    public Drug() {}

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PersonDrug> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDrug> persons) {
        this.persons = persons;
    }

}
