package com.application.ptsdwebapplication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.repositories.PeopleRepository;
import com.application.ptsdwebapplication.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {
    
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByEmail(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден!");

        return new PersonDetails(person.get());
    }
  
}
