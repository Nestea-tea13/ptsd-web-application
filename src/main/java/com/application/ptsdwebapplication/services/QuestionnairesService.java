package com.application.ptsdwebapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.data.QuestionnaireDataBHS;
import com.application.ptsdwebapplication.data.QuestionnaireDataCAPS;
import com.application.ptsdwebapplication.data.QuestionnaireDataIESR;
import com.application.ptsdwebapplication.data.QuestionnaireDataTOP8;
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

    /*@Transactional
    public Questionnaire saveQuestionnaire(String[] answers, String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return saveCAPS(answers);
        else if (questionnaireName.equals("IESR")) return saveIESR(answers);
        else if (questionnaireName.equals("BHS")) return saveBHS(answers);
        else return saveTOP8(answers);
    }

    @Transactional
    public CAPSResults saveCAPS(String[] answers) {
        CAPSResults results = new CAPSResults(answers, peopleService.getCurrentPerson());
        //peopleService.getCurrentPerson().getResultsCAPS().add(results);
        capsRepository.save(results);
        return results;
    }

    @Transactional
    public IESRResults saveIESR(String[] answers) {
        IESRResults results = new IESRResults(answers, peopleService.getCurrentPerson());
        //peopleService.getCurrentPerson().getResultsIESR().add(results);
        iesrRepository.save(results);
        return results;
    }

    @Transactional
    public BHSResults saveBHS(String[] answers) {
        BHSResults results = new BHSResults(answers, peopleService.getCurrentPerson());
        //peopleService.getCurrentPerson().getResultsBHS().add(results);
        bhsRepository.save(results);
        return results;
    }

    @Transactional
    public TOP8Results saveTOP8(String[] answers) {
        TOP8Results results = new TOP8Results(answers, peopleService.getCurrentPerson());
        //peopleService.getCurrentPerson().getResultsTOP8().add(results);
        top8Repository.save(results);
        return results;
    }*/

    public String[] getQuestions(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return QuestionnaireDataCAPS.Questions;
        else if (questionnaireName.equals("IESR")) return QuestionnaireDataIESR.Questions;
        else if (questionnaireName.equals("BHS")) return QuestionnaireDataBHS.Questions;
        else return QuestionnaireDataTOP8.Questions;
    }

    public String[] getSingleAnswerOptions(String questionnaireName) {
        if (questionnaireName.equals("IESR")) return QuestionnaireDataIESR.AnswerOptions;
        else return QuestionnaireDataBHS.AnswerOptions;
    }

    public String[][] getDoubleAnswerOptions(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return QuestionnaireDataCAPS.AnswerOptions;
        else return QuestionnaireDataTOP8.AnswerOptions;
    }

    public String getQuestionnaireURL(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return "user/questionnaires/CAPS";
        else if (questionnaireName.equals("IESR")) return "user/questionnaires/IESR";
        else if (questionnaireName.equals("BHS")) return "user/questionnaires/BHS";
        else if (questionnaireName.equals("TOP8")) return "user/questionnaires/TOP8";
        else return "redirect:/questionnaires";
    }

}
