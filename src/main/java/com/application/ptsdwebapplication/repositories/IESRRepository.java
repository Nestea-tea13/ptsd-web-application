package com.application.ptsdwebapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.Person;

@Repository
public interface IESRRepository extends JpaRepository<IESRResults, Integer> {

    List<IESRResults> findByUser(Person currentPerson);
    
}
