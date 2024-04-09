package com.application.ptsdwebapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.Questionnaire;

@Repository
public interface BHSRepository extends JpaRepository<BHSResults, Integer> {

    List<BHSResults> findByUser(Person currentPerson);

    List<BHSResults> findByUserOrderByDateDesc(Person currentPerson);

    void save(Questionnaire saveQuestionnaire);
    
}
