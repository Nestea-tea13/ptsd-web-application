package com.application.ptsdwebapplication.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.Questionnaire;
import com.application.ptsdwebapplication.models.TOP8Results;
import com.application.ptsdwebapplication.repositories.BHSRepository;
import com.application.ptsdwebapplication.repositories.CAPSRepository;
import com.application.ptsdwebapplication.repositories.IESRRepository;
import com.application.ptsdwebapplication.repositories.TOP8Repository;

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

    public Boolean existsQuestionnaireResults(int id, String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return capsRepository.existsById(id);
        else if (questionnaireName.equals("IESR")) return iesrRepository.existsById(id);
        else if (questionnaireName.equals("BHS")) return bhsRepository.existsById(id);
        else if (questionnaireName.equals("TOP8")) return top8Repository.existsById(id);
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

    public List<CAPSResults> getAllCAPSForUser() {
        return capsRepository.findByUser(peopleService.getCurrentPerson());
    }

    public List<IESRResults> getAllIESRForUser() {
        return iesrRepository.findByUser(peopleService.getCurrentPerson());
    }

    public List<BHSResults> getAllBHSForUser() {
        return bhsRepository.findByUser(peopleService.getCurrentPerson());
    }

    public List<TOP8Results> getAllTOP8ForUser() {
        return top8Repository.findByUser(peopleService.getCurrentPerson());
    }

    @Transactional
    public Questionnaire saveQuestionnaire(String[] answers, String questionnaireName) {
        Questionnaire saveQuestionnaire;
        if (questionnaireName.equals("CAPS")) {
            saveQuestionnaire = new CAPSResults(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsCAPS().add(results); // получение данных после ленивой загрузки?
            capsRepository.save(saveQuestionnaire);
        } else if (questionnaireName.equals("IESR")) {
            saveQuestionnaire = new IESRResults(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsIESR().add(results);
            iesrRepository.save(saveQuestionnaire);
        } else if (questionnaireName.equals("BHS")) {
            saveQuestionnaire = new BHSResults(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsBHS().add(results);
            bhsRepository.save(saveQuestionnaire);
        } else {
            saveQuestionnaire = new TOP8Results(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsTOP8().add(results);
            top8Repository.save(saveQuestionnaire);
        }
        return saveQuestionnaire;
    }

    public Boolean checkPeriodQuestionnaire(String questionnaireName) {
        Questionnaire questionnaire = null;
        if (questionnaireName.equals("CAPS")) {
            List<CAPSResults> questionnaires = capsRepository.findByUserOrderByDateDesc(peopleService.getCurrentPerson());
            if (questionnaires.size() != 0) questionnaire = questionnaires.get(0);
        } else if (questionnaireName.equals("IESR")) {
            List<IESRResults> questionnaires = iesrRepository.findByUserOrderByDateDesc(peopleService.getCurrentPerson());
            if (questionnaires.size() != 0) questionnaire = questionnaires.get(0);
        } else if (questionnaireName.equals("BHS")) {
            List<BHSResults> questionnaires = bhsRepository.findByUserOrderByDateDesc(peopleService.getCurrentPerson());
            if (questionnaires.size() != 0) questionnaire = questionnaires.get(0);
        } else {
            List<TOP8Results> questionnaires = top8Repository.findByUserOrderByDateDesc(peopleService.getCurrentPerson());
            if (questionnaires.size() != 0) questionnaire = questionnaires.get(0);
        }

        if (questionnaire != null) {
            int needDiff;
            if (questionnaireName.equals("CAPS")) needDiff = 30; else needDiff = 7;
            if (TimeUnit.DAYS.convert(new Date().getTime() - questionnaire.getDate().getTime(), TimeUnit.MILLISECONDS) < needDiff) 
                return false;
        }
        return true;
    }

    public String getMinMaxIntervalDate(String questionnaireName, String typeNeedValue) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        if (questionnaireName.equals("CAPS")) { 
            List<CAPSResults> resultsCAPS = getAllCAPSForUser(); 
            if (typeNeedValue.equals("min")) return dateFormatter.format(resultsCAPS.get(0).getDate());
            else return dateFormatter.format(resultsCAPS.get(resultsCAPS.size() - 1).getDate());
        } else if (questionnaireName.equals("IESR")) { 
            List<IESRResults> resultsIESR = getAllIESRForUser();
            if (typeNeedValue.equals("min")) return dateFormatter.format(resultsIESR.get(0).getDate());
            else return dateFormatter.format(resultsIESR.get(resultsIESR.size() - 1).getDate());
        } else if (questionnaireName.equals("BHS")) {
            List<BHSResults> resultsBHS = getAllBHSForUser();
            if (typeNeedValue.equals("min")) return dateFormatter.format(resultsBHS.get(0).getDate());
            else return dateFormatter.format(resultsBHS.get(resultsBHS.size() - 1).getDate());
        } else {
            List<TOP8Results> resultsTOP8 = getAllTOP8ForUser();
            if (typeNeedValue.equals("min")) return dateFormatter.format(resultsTOP8.get(0).getDate());
            else return dateFormatter.format(resultsTOP8.get(resultsTOP8.size() - 1).getDate());
        }
    }

    public Boolean existsQuestionnaireResultsBetweenDates(String questionnaireName, Date start, Date end) {
        if (questionnaireName.equals("CAPS")) {
            if (capsRepository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end).size() != 0) return true;
        } else if (questionnaireName.equals("IESR")) {
            if (iesrRepository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end).size() != 0) return true;
        } else if (questionnaireName.equals("BHS")) {
            if (bhsRepository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end).size() != 0) return true;
        } else if (questionnaireName.equals("TOP8")) {
            if (top8Repository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end).size() != 0) return true;
        }
        return false;
    }

    public List<CAPSResults> getCAPSResultsBetweenDates(Date start, Date end) {
        return capsRepository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end);
    }

    public List<IESRResults> getIESRResultsBetweenDates(Date start, Date end) {
        return iesrRepository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end);
    }

    public List<BHSResults> getBHSResultsBetweenDates(Date start, Date end) {
        return bhsRepository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end);
    }

    public List<TOP8Results> getTOP8ResultsBetweenDates(Date start, Date end) {
        return top8Repository.findByUserAndDateBetween(peopleService.getCurrentPerson(), start, end);
    }

}
