package com.application.ptsdwebapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.Questionnaire;

@Repository
public interface CAPSRepository extends JpaRepository<CAPSResults, Integer> {

    List<CAPSResults> findByUser(Person currentPerson);

    void save(Questionnaire saveQuestionnaire);
    
}
