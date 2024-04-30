package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.questionnaireResults.BHSResults;

@Repository
public interface BHSRepository extends JpaRepository<BHSResults, Integer> {

    List<BHSResults> findByUser(Person currentPerson);

    List<BHSResults> findByUserOrderByDateDesc(Person currentPerson);

    List<BHSResults> findByUserAndDateBetween(Person currentPerson, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
