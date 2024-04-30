package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.questionnaireResults.TOP8Results;

@Repository
public interface TOP8Repository extends JpaRepository<TOP8Results, Integer> {

    List<TOP8Results> findByUser(Person currentPerson);

    List<TOP8Results> findByUserOrderByDateDesc(Person currentPerson);

    List<TOP8Results> findByUserAndDateBetween(Person currentPerson, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
