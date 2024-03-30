package com.application.ptsdwebapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {

     private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Person findUserById(int id) {
        return peopleRepository.findById(id).get();
    }

    public Boolean existsUserById(int id) {
        return peopleRepository.existsById(id);
    }
 
    public Iterable<Person> findByRole(String role) {
        return peopleRepository.findByRole(role);
    }

    @Transactional
    public Person update(int id, Person updatedUser) {
        updatedUser.setId(id);
        peopleRepository.save(updatedUser);
        return updatedUser;
    }

    @Transactional
    public Person addUser(Person user) {
        user.generateRandomPassword(); // ДОБАВИТЬ ОТПРАВКУ ДАННЫХ НА ПОЧТУ ДЛЯ ВХОДА
        user.setRole("ROLE_USER");
        user.setStatus("В процессе");
        peopleRepository.save(user);
        return user;
    }

    @Transactional
    public void removePerson(int id) {
        Person person = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(person);
    }

}
