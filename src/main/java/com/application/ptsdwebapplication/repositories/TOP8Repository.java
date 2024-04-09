package com.application.ptsdwebapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.Questionnaire;
import com.application.ptsdwebapplication.models.TOP8Results;

@Repository
public interface TOP8Repository extends JpaRepository<TOP8Results, Integer> {

    List<TOP8Results> findByUser(Person currentPerson);

    List<TOP8Results> findByUserOrderByDateDesc(Person currentPerson);

    void save(Questionnaire saveQuestionnaire);
    
}
