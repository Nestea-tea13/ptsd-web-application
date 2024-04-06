package com.application.ptsdwebapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.IESRResults;
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

    public Boolean existsResultsById(int id, String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return capsRepository.existsById(id);
        else if (questionnaireName.equals("IESR")) return iesrRepository.existsById(id);
        else if (questionnaireName.equals("TOP8")) return top8Repository.existsById(id);
        else return bhsRepository.existsById(id);
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
    }

}
