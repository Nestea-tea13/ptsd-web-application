package com.application.ptsdwebapplication.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.PersonDrug;

@Repository
public interface PersonDrugsRepository extends CrudRepository<PersonDrug, Integer> {

    List<PersonDrug> findByPerson(Person person);
    
}
