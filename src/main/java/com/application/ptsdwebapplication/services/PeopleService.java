package com.application.ptsdwebapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {

     private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

}
