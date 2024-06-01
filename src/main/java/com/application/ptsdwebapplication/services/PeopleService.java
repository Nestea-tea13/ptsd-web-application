package com.application.ptsdwebapplication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.Patient;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.repositories.PatientsRepository;
import com.application.ptsdwebapplication.repositories.PeopleRepository;
import com.application.ptsdwebapplication.security.PersonDetails;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final PatientsRepository patientsRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PatientsRepository patientsRepository) {
        this.peopleRepository = peopleRepository;
        this.patientsRepository = patientsRepository;
    }

    public Person findPersonById(int id) {
        Person person = peopleRepository.findById(id).get();
        if(person.getRole().equals("ROLE_USER"))
            person.setStatus((patientsRepository.findById(id)).getStatus());
        return person;
    }

    public Boolean existsPersonById(int id) {
        return peopleRepository.existsById(id);
    }
 
    public Iterable<Person> findByRole(String role) {
        Iterable<Person> patients = peopleRepository.findByRoleOrderBySername(role);
        if(role.equals("ROLE_USER"))
            for (Person patient : patients)
                patient.setStatus((patientsRepository.findById(patient.getId())).getStatus());
        return patients;
    }

    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    @Transactional
    public void update(int id, Person updatedUser) {
        updatedUser.setId(id);
        peopleRepository.save(updatedUser);
        if(updatedUser.getRole().equals("ROLE_USER")) {
            Patient patient = patientsRepository.findById(id);
            patient.setStatus(updatedUser.getStatus());
            patientsRepository.save(patient);
        }
    }
    
    @Transactional
    public void updateCurrentPerson(Person updatedUser) {
        PersonDetails userDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        updatedUser.setId(userDetails.getPerson().getId());
        userDetails.setPerson(updatedUser);
        peopleRepository.save(updatedUser);
    }

    @Transactional
    public Person addPerson(Person person) {
        String textEmail = "Здравствуйте, " + person.getName() + "!\nВы зарегистрированы в качестве "; 
        
        String randomPassword = person.generateRandomPassword();
        if (person.getRole().equals("ROLE_USER")) {
            person.setStatus("В процессе");
            textEmail += "пациента ";
        } else textEmail += "администратора медицинского учреждения ";
        textEmail +="в web-приложении для оценки динамики посттравматического стрессового расстройства в процессе лечения.\n"
                + "\nСсылка на сайт: http://localhost:8080/\nЛогин: " + person.getEmail() 
                + "\nПароль: " + randomPassword + "\nДанный пароль можно изменить в личном кабинете.";
        
        peopleRepository.save(person);
        patientsRepository.save(new Patient(person.getId(), "В процессе"));

        mailSender.send(person.getEmail(), "Регистрация в приложении", textEmail);
        return person;
    }

    @Transactional
    public void removePerson(int id) {
        Person person = peopleRepository.findById(id).orElseThrow();
        peopleRepository.delete(person);

        Patient patient = patientsRepository.findById(id);
        patientsRepository.delete(patient);
    }

    public Person getCurrentPerson() {
        PersonDetails userDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getPerson();
    }

    public Patient getCurrentPatient() {
        return patientsRepository.findById(getCurrentPerson().getId());
    }

    @Transactional
    public void updatePassword(String password) {
        Person currentPerson = getCurrentPerson();
        currentPerson.setPassword(password);
        peopleRepository.save(currentPerson);
    }

}
