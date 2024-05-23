package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;
import com.application.ptsdwebapplication.models.questionnaireResults.CAPSResults;

@Repository
public interface CAPSRepository extends JpaRepository<CAPSResults, Integer> {

    List<CAPSResults> findByPatient(Patient patient);

    List<CAPSResults> findByPatientOrderByDateDesc(Patient patient);

    List<CAPSResults> findByPatientAndDateBetween(Patient patient, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
