package com.application.ptsdwebapplication.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;
import com.application.ptsdwebapplication.models.questionnaireResults.*;
import com.application.ptsdwebapplication.repositories.questionnaireRepositories.*;

@Service
@Transactional(readOnly = true)
public class QuestionnairesService {

    private final PeopleService peopleService; 
    private final CAPSRepository capsRepository;
    private final IESRRepository iesrRepository;
    private final BHSRepository bhsRepository;
    private final TOP8Repository top8Repository;

    @Autowired
    public QuestionnairesService(PeopleService peopleService, CAPSRepository capsRepository, IESRRepository iesrRepository,
                        BHSRepository bhsRepository, TOP8Repository top8Repository) {
        this.peopleService = peopleService;
        this.capsRepository = capsRepository;
        this.iesrRepository = iesrRepository;
        this.bhsRepository = bhsRepository;
        this.top8Repository = top8Repository;
    }

    public Boolean existsQuestionnaireResultsForCurrentPatient(int id, String questionnaireName) {
        Patient patient = peopleService.getCurrentPatient();
        if (questionnaireName.equals("CAPS")) return capsRepository.existsByIdAndPatient(id, patient);
        else if (questionnaireName.equals("IESR")) return iesrRepository.existsByIdAndPatient(id, patient);
        else if (questionnaireName.equals("BHS")) return bhsRepository.existsByIdAndPatient(id, patient);
        else if (questionnaireName.equals("TOP8")) return top8Repository.existsByIdAndPatient(id, patient);
        else return false;
    }

    public Questionnaire findQuestionnaireResults(int id, String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return findCAPSResultsById(id);
        else if (questionnaireName.equals("IESR")) return findIESRResultsById(id);
        else if (questionnaireName.equals("BHS")) return findBHSResultsById(id);
        else return findTOP8ResultsById(id);
    }

    public CAPSResults findCAPSResultsById(int id) {
        CAPSResults results = capsRepository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public IESRResults findIESRResultsById(int id) {
        IESRResults results = iesrRepository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public BHSResults findBHSResultsById(int id) {
        BHSResults results = bhsRepository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public TOP8Results findTOP8ResultsById(int id) {
        TOP8Results results = top8Repository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public Boolean existsPassedQuestionnaire(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) { 
            if (getAllCAPSForUser().size() == 0) return false;
        } else if (questionnaireName.equals("IESR")) {
            if (getAllIESRForUser().size() == 0) return false;
        } else if (questionnaireName.equals("BHS")) {
            if (getAllBHSForUser().size() == 0) return false;
        } else if (questionnaireName.equals("TOP8")) { 
            if (getAllTOP8ForUser().size() == 0) return false;
        }    
        return true;
    }

    public List<CAPSResults> getAllCAPSForUser() {
        return capsRepository.findByPatientOrderByDate(peopleService.getCurrentPatient());
    }

    public List<IESRResults> getAllIESRForUser() {
        return iesrRepository.findByPatientOrderByDate(peopleService.getCurrentPatient());
    }

    public List<BHSResults> getAllBHSForUser() {
        return bhsRepository.findByPatientOrderByDate(peopleService.getCurrentPatient());
    }

    public List<TOP8Results> getAllTOP8ForUser() {
        return top8Repository.findByPatientOrderByDate(peopleService.getCurrentPatient());
    }

    @Transactional
    public Questionnaire saveQuestionnaire(String[] answers, String questionnaireName) {
        Questionnaire saveQuestionnaire;
        if (questionnaireName.equals("CAPS")) {
            saveQuestionnaire = new CAPSResults(answers, peopleService.getCurrentPatient());
            capsRepository.save(saveQuestionnaire);
        } else if (questionnaireName.equals("IESR")) {
            saveQuestionnaire = new IESRResults(answers, peopleService.getCurrentPatient());
            iesrRepository.save(saveQuestionnaire);
        } else if (questionnaireName.equals("BHS")) {
            saveQuestionnaire = new BHSResults(answers, peopleService.getCurrentPatient());
            bhsRepository.save(saveQuestionnaire);
        } else {
            saveQuestionnaire = new TOP8Results(answers, peopleService.getCurrentPatient());
            top8Repository.save(saveQuestionnaire);
        }
        return saveQuestionnaire;
    }

    public Boolean checkPeriodQuestionnaire(String questionnaireName) {
        Questionnaire questionnaire = getLastQuestionnaire(questionnaireName);
        if (questionnaire != null) {
            int needDiff;
            if (questionnaireName.equals("CAPS")) needDiff = 30; else needDiff = 7;
            if (TimeUnit.DAYS.convert(new Date().getTime() - questionnaire.getDate().getTime(), TimeUnit.MILLISECONDS) < needDiff) 
                return false;
        }
        return true;
    }

    public Boolean checkNotCompletedQuestionnaires() {
        String[] questionnaires = {"CAPS", "IESR", "BHS", "TOP8"};
        String[] types = {"today", "missed"};
        for (String q : questionnaires) {
            for (String t : types) {
                if (checkNeedQuestionnaire(q, t)) return false;
            }
        }
        return true;
    }

    public Boolean checkNeedQuestionnaire(String questionnaireName, String type) {
        Questionnaire questionnaire = getLastQuestionnaire(questionnaireName);
        if (questionnaire != null) {
            int needDiff;
            if (questionnaireName.equals("CAPS")) needDiff = 30; else needDiff = 7;
            if (type.equals("today")) {
                if (TimeUnit.DAYS.convert(new Date().getTime() - questionnaire.getDate().getTime(), TimeUnit.MILLISECONDS) == needDiff) 
                    return true;
            } else if (type.equals("missed")) {
                if (TimeUnit.DAYS.convert(new Date().getTime() - questionnaire.getDate().getTime(), TimeUnit.MILLISECONDS) > needDiff) 
                    return true;
            }  
        } else return true;
        return false;
    }

    public Questionnaire getLastQuestionnaire(String questionnaireName) {
        Questionnaire questionnaire = null;
        if (questionnaireName.equals("CAPS")) {
            List<CAPSResults> questionnaires = capsRepository.findByPatientOrderByDateDesc(peopleService.getCurrentPatient());
            if (!questionnaires.isEmpty()) questionnaire = questionnaires.get(0);
        } else if (questionnaireName.equals("IESR")) {
            List<IESRResults> questionnaires = iesrRepository.findByPatientOrderByDateDesc(peopleService.getCurrentPatient());
            if (!questionnaires.isEmpty()) questionnaire = questionnaires.get(0);
        } else if (questionnaireName.equals("BHS")) {
            List<BHSResults> questionnaires = bhsRepository.findByPatientOrderByDateDesc(peopleService.getCurrentPatient());
            if (!questionnaires.isEmpty()) questionnaire = questionnaires.get(0);
        } else {
            List<TOP8Results> questionnaires = top8Repository.findByPatientOrderByDateDesc(peopleService.getCurrentPatient());
            if (!questionnaires.isEmpty()) questionnaire = questionnaires.get(0);
        }
        return questionnaire;
    }

    public String[] getMinMaxIntervalDate(String questionnaireName) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String[] dates = {"", ""}; // min, max
        if (questionnaireName.equals("CAPS")) { 
            List<CAPSResults> resultsCAPS = getAllCAPSForUser(); 
            dates[0] = dateFormatter.format(resultsCAPS.get(0).getDate());
            dates[1] = dateFormatter.format(resultsCAPS.get(resultsCAPS.size() - 1).getDate());
        } else if (questionnaireName.equals("IESR")) { 
            List<IESRResults> resultsIESR = getAllIESRForUser();
            dates[0] = dateFormatter.format(resultsIESR.get(0).getDate());
            dates[1] = dateFormatter.format(resultsIESR.get(resultsIESR.size() - 1).getDate());
        } else if (questionnaireName.equals("BHS")) {
            List<BHSResults> resultsBHS = getAllBHSForUser();
            dates[0] = dateFormatter.format(resultsBHS.get(0).getDate());
            dates[1] = dateFormatter.format(resultsBHS.get(resultsBHS.size() - 1).getDate());
        } else {
            List<TOP8Results> resultsTOP8 = getAllTOP8ForUser();
            dates[0] = dateFormatter.format(resultsTOP8.get(0).getDate());
            dates[1] = dateFormatter.format(resultsTOP8.get(resultsTOP8.size() - 1).getDate());
        }
        return dates;
    }

    public Boolean existsQuestionnaireResultsBetweenDates(String questionnaireName, Date start, Date end) {
        if (questionnaireName.equals("CAPS")) {
            if (capsRepository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end).size() != 0) return true;
        } else if (questionnaireName.equals("IESR")) {
            if (iesrRepository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end).size() != 0) return true;
        } else if (questionnaireName.equals("BHS")) {
            if (bhsRepository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end).size() != 0) return true;
        } else if (questionnaireName.equals("TOP8")) {
            if (top8Repository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end).size() != 0) return true;
        }
        return false;
    }

    public List<CAPSResults> getCAPSResultsBetweenDates(Date start, Date end) {
        return capsRepository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end);
    }

    public List<IESRResults> getIESRResultsBetweenDates(Date start, Date end) {
        return iesrRepository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end);
    }

    public List<BHSResults> getBHSResultsBetweenDates(Date start, Date end) {
        return bhsRepository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end);
    }

    public List<TOP8Results> getTOP8ResultsBetweenDates(Date start, Date end) {
        return top8Repository.findByPatientAndDateBetween(peopleService.getCurrentPatient(), start, end);
    }

}
