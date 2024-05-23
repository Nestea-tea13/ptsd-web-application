package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;
import com.application.ptsdwebapplication.models.questionnaireResults.BHSResults;

@Repository
public interface BHSRepository extends JpaRepository<BHSResults, Integer> {

    List<BHSResults> findByPatient(Patient patient);

    List<BHSResults> findByPatientOrderByDateDesc(Patient patient);

    List<BHSResults> findByPatientAndDateBetween(Patient patient, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
