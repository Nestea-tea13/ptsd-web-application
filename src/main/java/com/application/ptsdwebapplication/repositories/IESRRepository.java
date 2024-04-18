package com.application.ptsdwebapplication.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.Questionnaire;

@Repository
public interface IESRRepository extends JpaRepository<IESRResults, Integer> {

    List<IESRResults> findByUser(Person currentPerson);

    List<IESRResults> findByUserOrderByDateDesc(Person currentPerson);

    List<IESRResults> findByUserAndDateBetween(Person currentPerson, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
