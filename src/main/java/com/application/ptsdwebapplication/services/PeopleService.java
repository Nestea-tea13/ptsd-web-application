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
    private MailSender mailSender;

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
    public Person addPerson(Person person, String role) {
        person.generateRandomPassword(); // ДОБАВИТЬ ШИФРОВАНИЕ ПАРОЛЯ
        person.setRole(role);
        if (role.equals("ROLE_USER")) person.setStatus("В процессе");
        peopleRepository.save(person);

        mailSender.send(person.getEmail(), "Регистрация в приложении", 
            "Здравствуйте, " + person.getName() + "!\n" + "Вы были зарегистрированы администратором больницы в web-приложении для оценки динамики посттравматического стрессового расстройства в процессе лечения.\n\n" 
            + "Ссылка на сайт: //http..\nВаш логин: " + person.getEmail() + "\nПароль: " + person.getPassword() + "\nДанный регистрационный пароль можно изменить на свой в личном кабинете.");
        return person;
    }

    @Transactional
    public void removePerson(int id) {
        Person person = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(person);
    }

}
