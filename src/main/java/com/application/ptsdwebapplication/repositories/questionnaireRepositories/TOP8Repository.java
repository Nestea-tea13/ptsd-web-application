package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;
import com.application.ptsdwebapplication.models.questionnaireResults.TOP8Results;

@Repository
public interface TOP8Repository extends JpaRepository<TOP8Results, Integer> {

    List<TOP8Results> findByPatientOrderByDate(Patient patient);

    List<TOP8Results> findByPatientOrderByDateDesc(Patient patient);

    List<TOP8Results> findByPatientAndDateBetween(Patient patient, Date start, Date end);

    boolean existsByIdAndPatient(int id, Patient patient);

    void save(Questionnaire saveQuestionnaire);
    
}
