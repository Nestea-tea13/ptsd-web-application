package com.application.ptsdwebapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.Drug;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.PersonDrug;
import com.application.ptsdwebapplication.repositories.PersonDrugsRepository;

@Service
@Transactional(readOnly = true)
public class PersonDrugsService {

    private final PersonDrugsRepository personDrugsRepository;
    private final PeopleService peopleService;
    private final DrugsService drugsService;

    public PersonDrugsService(PersonDrugsRepository personDrugsRepository, PeopleService peopleService, DrugsService drugsService) {
        this.personDrugsRepository = personDrugsRepository;
        this.peopleService = peopleService;
        this.drugsService = drugsService;
    }

    public List<PersonDrug> findPersonDrugs() {
        return personDrugsRepository.findByPerson(peopleService.getCurrentPerson());
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
    }
    
}
