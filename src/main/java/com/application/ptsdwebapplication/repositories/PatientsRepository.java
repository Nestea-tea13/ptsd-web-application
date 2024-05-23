package com.application.ptsdwebapplication.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.Patient;

@Repository
public interface PatientsRepository extends CrudRepository<Patient, Integer> {

    Patient findById(int id);

}