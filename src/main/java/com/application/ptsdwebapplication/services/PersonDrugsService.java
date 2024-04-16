package com.application.ptsdwebapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.Drug;
import com.application.ptsdwebapplication.models.DrugMark;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.PersonDrug;
import com.application.ptsdwebapplication.repositories.PersonDrugsRepository;

@Service
@Transactional(readOnly = true)
public class PersonDrugsService {

    private final PersonDrugsRepository personDrugsRepository;
    private final PeopleService peopleService;
    private final DrugsService drugsService;
    private final DrugMarksService drugMarksService;

    public PersonDrugsService(PersonDrugsRepository personDrugsRepository, PeopleService peopleService, 
            DrugsService drugsService, DrugMarksService drugMarksService) {
        this.personDrugsRepository = personDrugsRepository;
        this.peopleService = peopleService;
        this.drugsService = drugsService;
        this.drugMarksService = drugMarksService;
    }

    public List<PersonDrug> findPersonDrugs() {
        return personDrugsRepository.findByPerson(peopleService.getCurrentPerson());
    }

    public Boolean existsPersonDrugById(int id) {
        if (personDrugsRepository.existsById(id)) {
            if (findById(id).getPerson().getId() == peopleService.getCurrentPerson().getId()) {
                return true;
            }
        }
        return false;
    }

    public PersonDrug findById(int id) {
        return personDrugsRepository.findById(id).get();
    }

    @Transactional
    public void addDrug(PersonDrug personDrug, String name) {
        Person person = peopleService.getCurrentPerson();
        Drug drug = drugsService.findByName(name).get();

        personDrug.setPerson(person);
        personDrug.setDrug(drug);

        person.getDrugs().add(personDrug);
        drug.getPersons().add(personDrug);

        personDrugsRepository.save(personDrug);
        drugMarksService.createEmptyMarks(personDrug);
    }

    public List<DrugMark> findNeedMarks(String type) {
        List<PersonDrug> personDrugs = findPersonDrugs();
        List<DrugMark> marks = null, personDrugMarks = null;
        for (PersonDrug personDrug : personDrugs) {
            if (type.equals("missed")) personDrugMarks = drugMarksService.findByPersonDrugMissedMarks(personDrug);
            else if (type.equals("today")) personDrugMarks = drugMarksService.findByPersonDrugTodayNullMarks(personDrug);
            if (!personDrugMarks.isEmpty()) {
                if (marks == null) marks = personDrugMarks;
                else marks.addAll(personDrugMarks);
            }
        }
        return marks;
    }
    
}
