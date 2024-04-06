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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Person")
public class Person {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Column(name = "sername")
    private String sername;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private String birthday;

    @NotEmpty(message = "Почта не должна быть пустой")
    @Email(message = "Корректный формат: name@example.com")
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<PersonDrug> drugs;

    @OneToMany(mappedBy = "user")
    private List<CAPSResults> resultsCAPS;

    @OneToMany(mappedBy = "user")
    private List<IESRResults> resultsIESR;

    @OneToMany(mappedBy = "user")
    private List<BHSResults> resultsBHS;

    @OneToMany(mappedBy = "user")
    private List<TOP8Results> resultsTOP8;

    public Person() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void generateRandomPassword() {
        String randomPassword = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();

        for (int i = 0; i < 10; i++)
            randomPassword += characters.charAt((int) Math.floor(Math.random() * charactersLength));

        this.password = randomPassword;
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
