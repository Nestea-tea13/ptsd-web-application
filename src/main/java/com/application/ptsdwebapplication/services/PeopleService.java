package com.application.ptsdwebapplication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.repositories.PeopleRepository;
import com.application.ptsdwebapplication.security.PersonDetails;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Person findPersonById(int id) {
        return peopleRepository.findById(id).get();
    }

    public Boolean existsPersonById(int id) {
        return peopleRepository.existsById(id);
    }
 
    public Iterable<Person> findByRole(String role) {
        return peopleRepository.findByRole(role);
    }

    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    @Transactional
    public Person update(int id, Person updatedUser) {
        updatedUser.setId(id);
        peopleRepository.save(updatedUser);
        return updatedUser;
    }
    
    @Transactional
    public Person updateCurrentPerson(Person updatedUser) {
        PersonDetails userDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        updatedUser.setId(userDetails.getPerson().getId());
        userDetails.setPerson(updatedUser);
        peopleRepository.save(updatedUser);
        return updatedUser;
    }

    @Transactional
    public Person addPerson(Person person) {
        String textEmail = "Здравствуйте, " + person.getName() + "!\nВы зарегистрированы в качестве "; 
        
        person.generateRandomPassword(); // ДОБАВИТЬ ШИФРОВАНИЕ ПАРОЛЯ
        if (person.getRole().equals("ROLE_USER")) {
            person.setStatus("В процессе");
            textEmail += "пациента ";
        } else textEmail += "администратора медицинского учреждения ";
        textEmail +="в web-приложении для оценки динамики посттравматического стрессового расстройства в процессе лечения.\n"
                + "\nСсылка на сайт: http://localhost:8080/\nЛогин: " + person.getEmail() 
                + "\nПароль: " + person.getPassword() + "\nДанный пароль можно изменить на свой в личном кабинете.";
        
        peopleRepository.save(person);

        mailSender.send(person.getEmail(), "Регистрация в приложении", textEmail);
        return person;
    }

    @Transactional
    public void removePerson(int id) {
        Person person = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(person);
    }

    public Person getCurrentPerson() {
        PersonDetails userDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getPerson();
    }

    @Transactional
    public void updatePassword(String password) {
        Person currentPerson = getCurrentPerson();
        currentPerson.setPassword(password);
        peopleRepository.save(currentPerson);
    }

}
