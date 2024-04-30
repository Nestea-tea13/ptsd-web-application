package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.questionnaireResults.IESRResults;

@Repository
public interface IESRRepository extends JpaRepository<IESRResults, Integer> {

    List<IESRResults> findByUser(Person currentPerson);

    List<IESRResults> findByUserOrderByDateDesc(Person currentPerson);

    List<IESRResults> findByUserAndDateBetween(Person currentPerson, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
