package com.application.ptsdwebapplication.repositories.questionnaireRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;
import com.application.ptsdwebapplication.models.questionnaireResults.IESRResults;

@Repository
public interface IESRRepository extends JpaRepository<IESRResults, Integer> {

    List<IESRResults> findByPatient(Patient patient);

    List<IESRResults> findByPatientOrderByDateDesc(Patient patient);

    List<IESRResults> findByPatientAndDateBetween(Patient patient, Date start, Date end);

    void save(Questionnaire saveQuestionnaire);
    
}
